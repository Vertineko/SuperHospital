package com.vertineko.shospital.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constant.StatusConstant;
import com.vertineko.shospital.constrain.errorDef.error.PatientErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.PatientException;
import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dao.dto.req.InsertReservationDTO;
import com.vertineko.shospital.dao.dto.req.ReservationPageDTO;
import com.vertineko.shospital.dao.mapper.ReservationMapper;
import com.vertineko.shospital.service.ReservationService;
import com.vertineko.shospital.usr.UserDO;
import com.vertineko.shospital.usr.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, ReservationDO> implements ReservationService {

    private final ReservationMapper reservationMapper;

    @Override
    public int insertReservation(InsertReservationDTO requestParam) {
        //首先获取当前使用系统的患者的信息
        UserDO user = UserUtils.getUser();
        //检测当前是否有还没有处理的预约
        if (check(requestParam)){
            ReservationDO reservation = ReservationDO.builder()
                    .departmentId(requestParam.getDepartment_id())
                    .doctorId(requestParam.getDoctor_id())
                    .patientId(user.getId())
                    .createTime(new Date())
                    .status(StatusConstant.SUBMITTED)
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
        if (user.getRole().equals(Role.DOCTOR.getMsg())){
            reservationDO = ReservationDO.builder()
                    .id(id)
                    .status(StatusConstant.COMPLETED)
                    .build();
        }else {
            reservationDO = ReservationDO.builder()
                    .id(id)
                    .status(StatusConstant.CANCELED)
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
    public ReservationPageDTO getReservationPage(ReservationPageDTO requestParam) {
        UserDO user = UserUtils.getUser();
        LambdaQueryWrapper<ReservationDO> queryWrapper;
        if (user.getRole().equals(Role.DOCTOR.getMsg())){
            queryWrapper = Wrappers.lambdaQuery(ReservationDO.class)
                    .eq(ReservationDO::getDoctorId, user.getId());
        }else {
            queryWrapper = Wrappers.lambdaQuery(ReservationDO.class)
                    .eq(ReservationDO::getPatientId, user.getId());
        }
        return reservationMapper.selectPage(requestParam, queryWrapper);


    }

    //检查患者是否在对某个科室的医生重复预约
    private boolean check(InsertReservationDTO requestParam) {
        UserDO user = UserUtils.getUser();
        LambdaQueryWrapper<ReservationDO> queryWrapper = Wrappers.lambdaQuery(ReservationDO.class)
                .eq(ReservationDO::getPatientId, user.getId())
                .eq(ReservationDO::getDepartmentId, requestParam.getDepartment_id())
                .eq(ReservationDO::getDoctorId, requestParam.getDoctor_id())
                .eq(ReservationDO::getStatus, StatusConstant.SUBMITTED);
        return reservationMapper.selectOne(queryWrapper) == null;
    }

}
