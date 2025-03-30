package com.vertineko.shospital.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constant.RedisKeyConstant;
import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constant.StatusConstant;
import com.vertineko.shospital.constrain.errorDef.error.PatientErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.PatientException;
import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dao.dto.req.InsertReservationDTO;
import com.vertineko.shospital.dao.mapper.ReservationMapper;
import com.vertineko.shospital.dto.patient.req.ReservationPageDTO;
import com.vertineko.shospital.dto.patient.res.ReservationPageVO;
import com.vertineko.shospital.service.ReservationService;
import com.vertineko.shospital.usr.UserDO;
import com.vertineko.shospital.usr.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, ReservationDO> implements ReservationService {

    private final ReservationMapper reservationMapper;

    private final RedissonClient redisson;

    private static final int LOCK_ALIVE_TIME = 100000;

    @Override
    public int insertReservation(InsertReservationDTO requestParam) {
        //首先获取当前使用系统的患者的信息
        UserDO user = UserUtils.getUser();
        //检测当前是否有还没有处理的预约
        if (check(requestParam)){
            ReservationDO reservation = ReservationDO.builder()
                    .department(requestParam.getDepartment())
                    .doctorId(requestParam.getDoctorId())
                    .patientId(user.getId())
                    .createTime(new Date())
                    .status(StatusConstant.NORMAL)
                    .build();
            return reservationMapper.insert(reservation);
        }
        throw new PatientException(PatientErrorCode.DONT_DO_RESERVATION_AGAIN);
    }

    //此处其实只为更改状态，患者只可能取消，医生只可能接受
    @Override
    public int updateReservation(Long id) {
        if (getReservation(id) == null) {
            throw new PatientException(PatientErrorCode.RESERVATION_NOT_EXISTED);
        }
        UserDO user = UserUtils.getUser();
        ReservationDO reservationDO;
        if (user != null && user.getRole().equals(Role.PATIENT.getMsg())){
            reservationDO = ReservationDO.builder()
                    .id(id)
                    .status(StatusConstant.CANCELED)
                    .build();

        }else {
            reservationDO = ReservationDO.builder()
                    .id(id)
                    .status(StatusConstant.COMPLETED)
                    .build();
        }
        return reservationMapper.updateById(reservationDO);

    }

    @Override
    public int deleteBatchReservation() {
        return 0;
    }

    @Override
    public ReservationDO getReservation(Long id) {
        LambdaQueryWrapper<ReservationDO> queryWrapper = Wrappers.lambdaQuery(ReservationDO.class)
                .eq(ReservationDO::getId, id);
        return reservationMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<ReservationPageVO> getReservationPage(ReservationPageDTO requestParam) {
        requestParam.setPatientId(UserUtils.getUser().getId());
        return reservationMapper.getReservationPage(requestParam);
    }

    @Override
    public Integer cancelReservation(Long id) {
        ReservationDO reservation = getReservation(id);
        if (reservation == null) {
            throw new PatientException(PatientErrorCode.RESERVATION_NOT_EXISTED);
        }
        //如果存在的话，就要加锁来改变状态
        RLock rLock = redisson.getLock(RedisKeyConstant.RESERVATION_INFO_RWLOCK_KEY_PREFIX.getKey() + id);
        rLock.lock();
        try {
            //获取锁之后，再检查一遍当前预约的状态，因为可能已经被取消了
            log.info("取消预约加锁:{}", RedisKeyConstant.RESERVATION_INFO_RWLOCK_KEY_PREFIX.getKey() + id);
            if (reservation.getStatus() == StatusConstant.CANCELED) {
                throw new PatientException(PatientErrorCode.RESERVATION_STATUS_ALREADY_CANCELED);
            }
            //真正开始修改状态
            reservation.setStatus(StatusConstant.CANCELED);
            return reservationMapper.updateById(reservation);
        }finally {
            rLock.unlock();
        }
    }

    //检查患者是否在对某个科室的医生重复预约
    private boolean check(InsertReservationDTO requestParam) {
        UserDO user = UserUtils.getUser();
        LambdaQueryWrapper<ReservationDO> queryWrapper = Wrappers.lambdaQuery(ReservationDO.class)
                .eq(ReservationDO::getPatientId, user.getId())
                .eq(ReservationDO::getDoctorId, requestParam.getDoctorId())
                .in(ReservationDO::getStatus, StatusConstant.NORMAL, StatusConstant.SUBMITTED);
        return reservationMapper.selectOne(queryWrapper) == null;
    }

}
