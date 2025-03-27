package com.vertineko.shospital.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constant.NewConstant;
import com.vertineko.shospital.constant.RedisKeyConstant;
import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constant.Sex;
import com.vertineko.shospital.constrain.errorDef.error.DoctorErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DocterException;
import com.vertineko.shospital.dao.DoctorDO;
import com.vertineko.shospital.dao.mapper.DoctorMapper;
import com.vertineko.shospital.dto.doctor.req.*;
import com.vertineko.shospital.dto.doctor.res.DocDetailVO;
import com.vertineko.shospital.dto.doctor.res.DoctorPageVO;
import com.vertineko.shospital.service.DoctorService;
import com.vertineko.shospital.utils.WorkTimeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl extends ServiceImpl<DoctorMapper, DoctorDO> implements DoctorService {

    private final DoctorMapper doctorMapper;

    private final RedissonClient redisson;

    @Override
    public int insertDoctor(InsertDoctorDTO requestParam) {
        //先查找是否有重复
        LambdaQueryWrapper<DoctorDO> queryWrapper = Wrappers.lambdaQuery(DoctorDO.class)
                .eq(DoctorDO::getUsername, requestParam.getUsername());
        DoctorDO doctor = doctorMapper.selectOne(queryWrapper);
        //如果重复了，就抛异常
        if (doctor != null) {
            throw new DocterException(DoctorErrorCode.DOCTOR_USERNAME_REPEATED);
        }
        //没有重复就加分布式锁 新增该角色
        RLock rlock = redisson.getLock(RedisKeyConstant.DOCTOR_INFO_LOCK_KEY_PREFIX.getVal() + requestParam.getUsername());
        //防止于此同时另一个管理员同样在录入该用户 最后导致数据库中冗余数据的产生
        if (rlock.tryLock()){
            try{
                DoctorDO doctorDO = new DoctorDO();
                BeanUtil.copyProperties(requestParam, doctorDO);
                doctorDO.setRole(Role.DOCTOR);
                doctorDO.setNewflag(NewConstant.NEW_USER);
                return doctorMapper.insert(doctorDO);
            }finally {
                rlock.unlock();
            }
        }else {
            throw new DocterException(DoctorErrorCode.DOCTOR_USER_NOT_EXISTED);
        }

    }

    @Override
    public int removeById(Long id) {
        //首先查找该ID指向的用户是否真实存在
        DoctorDO doctorDO = getById(id);
        if (doctorDO != null) {
            //先加锁, 此处的所获取模式为有限等待获取写锁(3000ms)，超时抛异常， 获取成功就删除
            RReadWriteLock rwLock = redisson.getReadWriteLock(RedisKeyConstant.DOCTOR_INFO_RWLOCK_KEY_PREFIX.getVal() + doctorDO.getId());
            rwLock.writeLock().lock(Long.parseLong(RedisKeyConstant.REDIS_LOCK_MAX_WAIT_TIME.getVal()), TimeUnit.MILLISECONDS);
            try{
                return doctorMapper.deleteById(doctorDO);
            }finally {
                rwLock.writeLock().unlock();
            }
        }else {
            throw new DocterException(DoctorErrorCode.DOCTOR_USER_NOT_EXISTED);
        }
    }

    @Override
    public int removeByUsername(String username) {
        //首先查找该ID指向的用户是否真实存在
        DoctorDO doctorDO = getByUsername(username);
        if (doctorDO != null) {
            //先加锁, 此处的所获取模式为有限等待获取写锁(3000ms)，超时抛异常， 获取成功就删除
            RReadWriteLock rwLock = redisson.getReadWriteLock(RedisKeyConstant.DOCTOR_INFO_RWLOCK_KEY_PREFIX.getVal() + doctorDO.getId());
            rwLock.writeLock().lock(Long.parseLong(RedisKeyConstant.REDIS_LOCK_MAX_WAIT_TIME.getVal()), TimeUnit.MILLISECONDS);
            try{
                return doctorMapper.deleteById(doctorDO);
            }finally {
                rwLock.writeLock().unlock();
            }
        }else {
            throw new DocterException(DoctorErrorCode.DOCTOR_USER_NOT_EXISTED);
        }
    }

    @Override
    public int updateById(UpdateDoctorByIdDTO requestParam) {
        //首先查找是否有该医生角色
        DoctorDO doctorDO = getById(requestParam.getId());
        if (doctorDO == null) {
            throw new DocterException(DoctorErrorCode.DOCTOR_USER_NOT_EXISTED);
        }
        RReadWriteLock rwlock = redisson.getReadWriteLock(RedisKeyConstant.DOCTOR_INFO_RWLOCK_KEY_PREFIX.getVal() + doctorDO.getId());
        rwlock.writeLock().lock(Long.parseLong(RedisKeyConstant.REDIS_LOCK_MAX_WAIT_TIME.getVal()), TimeUnit.MILLISECONDS);
        try {
            BeanUtil.copyProperties(requestParam, doctorDO);
            doctorDO.setNewflag(NewConstant.OLD_USER);
            return doctorMapper.updateById(doctorDO);
        }finally {
            rwlock.writeLock().unlock();
        }
    }

    @Override
    public int updateByUsername(UpdateDoctorByUsernameDTO requestParam) {
        //首先查找是否有该医生角色
        DoctorDO doctorDO = getByUsername(requestParam.getUsername());
        if (doctorDO == null) {
            throw new DocterException(DoctorErrorCode.DOCTOR_USER_NOT_EXISTED);
        }
        RReadWriteLock rwlock = redisson.getReadWriteLock(RedisKeyConstant.DOCTOR_INFO_RWLOCK_KEY_PREFIX.getVal() + doctorDO.getId());
        rwlock.writeLock().lock(Long.parseLong(RedisKeyConstant.REDIS_LOCK_MAX_WAIT_TIME.getVal()), TimeUnit.MILLISECONDS);
        try {
            BeanUtil.copyProperties(requestParam, doctorDO);
            doctorDO.setNewflag(NewConstant.OLD_USER);
            return doctorMapper.updateById(doctorDO);
        }finally {
            rwlock.writeLock().unlock();
        }
    }


    @Override
    public IPage<DoctorPageVO> getDoctorPage(DoctorPageDTO requestParam) {
        if (requestParam.getWorktime() != null && !requestParam.getWorktime().isEmpty()){
            requestParam.setWorkTimeList(WorkTimeUtils.parseWorkTimeStr(requestParam.getWorktime()));
        }
        return doctorMapper.getPage(requestParam);
    }

    @Override
    public DocDetailVO getDocDetail(String username) {
        DoctorDO doctorDO = getByUsername(username);
        if (doctorDO == null) {
            throw new DocterException(DoctorErrorCode.DOCTOR_USER_NOT_EXISTED);
        }
        DocDetailVO docDetailVO = new DocDetailVO();
        BeanUtil.copyProperties(doctorDO, docDetailVO);
        return docDetailVO;
    }

    @Override
    public DocDepartPageDTO getDocDepPageByDepId(DocDepartPageDTO requestParam) {
        DoctorPageDTO doctorPageDTO = new DoctorPageDTO();
        doctorPageDTO.setSex(Sex.NULL);
        BeanUtil.copyProperties(requestParam, doctorPageDTO);
        BeanUtil.copyProperties(getDoctorPage(doctorPageDTO), requestParam);
        return requestParam;
    }

    public DoctorDO getById(Long id) {
        LambdaQueryWrapper<DoctorDO> queryWrapper = Wrappers.lambdaQuery(DoctorDO.class)
                .eq(DoctorDO::getId, id);
        return doctorMapper.selectOne(queryWrapper);
    }



    public DoctorDO getByUsername(String username) {
        LambdaQueryWrapper<DoctorDO> queryWrapper = Wrappers.lambdaQuery(DoctorDO.class)
                .eq(DoctorDO::getUsername, username);
        log.info("参数:{}", username);
        return doctorMapper.selectOne(queryWrapper);
    }
}
