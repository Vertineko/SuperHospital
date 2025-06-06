package com.vertineko.shospital.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constant.RedisKeyConstant;
import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constrain.errorDef.error.PatientErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.PatientException;
import com.vertineko.shospital.dao.PatientDO;
import com.vertineko.shospital.dao.dto.req.UpdatePatientByIdDTO;
import com.vertineko.shospital.dao.mapper.PatientMapper;
import com.vertineko.shospital.dto.LoginDTO;
import com.vertineko.shospital.dto.modifyPasswordDTO;
import com.vertineko.shospital.dto.patient.req.InsertPatientDTO;
import com.vertineko.shospital.dto.patient.req.PatientPageDTO;
import com.vertineko.shospital.dto.patient.req.UpdatePatientByUsernameDTO;
import com.vertineko.shospital.dto.patient.res.PatientPageVO;
import com.vertineko.shospital.service.PatientService;
import com.vertineko.shospital.usr.UserDO;
import com.vertineko.shospital.utils.JwtUtil;
import com.vertineko.shospital.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientServiceImpl extends ServiceImpl<PatientMapper, PatientDO> implements PatientService {

    private final PatientMapper patientMapper;

    private final RedissonClient redisson;

    private final StringRedisTemplate redisTemplate;

    private static final int KEY_ALIVE_TIME = 4;

    private static final int LOCK_ALIVE_TIME = 1000;

    @Override
    public int insertPatient(InsertPatientDTO requestParam) {
        //首先查找是否有用户名重复的
        PatientDO patientDO = getByUsername(requestParam.getUsername());
        if (patientDO != null) {
            throw new PatientException(PatientErrorCode.PATIENT_USERNAME_ALREADY_EXISTED);
        }
        RLock lock = redisson.getLock(RedisKeyConstant.PATIENT_INFO_LOCK_KEY_PREFIX + requestParam.getUsername());
        lock.lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            patientDO = new PatientDO();
            patientDO.setRole(Role.PATIENT);
            BeanUtil.copyProperties(requestParam, patientDO);
            return patientMapper.insert(patientDO);
        }finally {
            lock.unlock();
        }

    }

    @Override
    public int removeById(Long id) {
        PatientDO patientDO = getById(id);
        if (patientDO == null) {
            throw new PatientException(PatientErrorCode.PATIENT_NOT_EXISTED);
        }
        RReadWriteLock lock = redisson.getReadWriteLock(RedisKeyConstant.PATIENT_INFO_RWLOCK_KEY_PREFIX.getKey() + id);
        lock.writeLock().lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            return patientMapper.deleteById(patientDO);
        }finally {
            lock.writeLock().unlock();
        }

    }

    @Override
    public int removeByUsername(String username) {
        PatientDO patientDO = getByUsername(username);
        if (patientDO == null) {
            throw new PatientException(PatientErrorCode.PATIENT_NOT_EXISTED);
        }
        RReadWriteLock lock = redisson.getReadWriteLock(RedisKeyConstant.PATIENT_INFO_RWLOCK_KEY_PREFIX.getKey() + patientDO.getUsername());
        lock.writeLock().lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            return patientMapper.deleteById(patientDO);
        }finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public String login(LoginDTO requestParam) {

        LambdaQueryWrapper<PatientDO> queryWrapper = Wrappers.lambdaQuery(PatientDO.class)
                .eq(PatientDO::getUsername, requestParam.getUsername())
                .eq(PatientDO::getPassword, requestParam.getPassword());
        PatientDO patient = patientMapper.selectOne(queryWrapper);
        if (patient == null) {
            throw new PatientException(PatientErrorCode.PATIENT_USER_NOT_EXISTED);
        }
        //构造JWT
        Map<String, String> map = new HashMap<>();
        map.put("id", patient.getId() + "");
        map.put("username", patient.getUsername());
        map.put("role", patient.getRole().getMsg());
        String token = JwtUtil.createToken(map);
        String key = RedisKeyConstant.PATIENT_LOGIN_KEY_PREFIX.getKey() + patient.getUsername();
        redisTemplate.opsForValue().set(key, token);
        redisTemplate.expire(key, KEY_ALIVE_TIME, TimeUnit.HOURS);
        return requestParam.getUsername();
    }

    @Override
    public int updateById(UpdatePatientByIdDTO requestParam) {
        PatientDO patientDO = getById(requestParam.getId());
        if (patientDO == null) {
            throw new PatientException(PatientErrorCode.PATIENT_NOT_EXISTED);
        }
        RLock lock = redisson.getLock(RedisKeyConstant.PATIENT_INFO_LOCK_KEY_PREFIX + patientDO.getUsername());
        lock.lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            BeanUtil.copyProperties(requestParam, patientDO);
            return patientMapper.updateById(patientDO);

        }finally {
            lock.unlock();
        }


    }

    @Override
    public int updateByUsername(UpdatePatientByUsernameDTO requestParam) {
        PatientDO patientDO = getByUsername(requestParam.getUsername());
        if (patientDO == null) {
            throw new PatientException(PatientErrorCode.PATIENT_NOT_EXISTED);
        }
        RLock lock = redisson.getLock(RedisKeyConstant.PATIENT_INFO_LOCK_KEY_PREFIX + patientDO.getUsername());
        lock.lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            BeanUtil.copyProperties(requestParam, patientDO);
            return patientMapper.updateById(patientDO);

        }finally {
            lock.unlock();
        }
    }

    @Override
    public IPage<PatientPageVO> pagePatient(PatientPageDTO requestParam) {
        return patientMapper.getPage(requestParam);
    }

    @Override
    public PatientDO getById(Long id) {
        LambdaQueryWrapper<PatientDO> queryWrapper = Wrappers.lambdaQuery(PatientDO.class)
                .eq(PatientDO::getId, id);
        return patientMapper.selectOne(queryWrapper);
    }

    @Override
    public PatientDO getByUsername(String username) {
        LambdaQueryWrapper<PatientDO> queryWrapper = Wrappers.lambdaQuery(PatientDO.class)
                .eq(PatientDO::getUsername, username);
        return patientMapper.selectOne(queryWrapper);
    }

    @Override
    public Integer modifyPassword(modifyPasswordDTO requestParam) {
        //获取当前用户
        UserDO user = UserUtils.getUser();
        Long id = user.getId();
        PatientDO patient = patientMapper.selectById(id);
        if (patient == null) {
            throw new PatientException(PatientErrorCode.PATIENT_IS_NOT_EXISTED);
        }
        if (patient.getPassword().equals(requestParam.getOriginPassword())){
            if (patient.getPassword().equals(requestParam.getNewPassword())){
                throw new PatientException(PatientErrorCode.OLD_PASSWORD_SAME_WITH_NEW_PASSWORD);
            }
            patient.setPassword(requestParam.getNewPassword());
            return patientMapper.updateById(patient);
        }
        throw new PatientException(PatientErrorCode.OLD_PASSWORD_NOT_MATCH);
    }
}
