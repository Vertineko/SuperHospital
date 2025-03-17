package com.vertineko.shospital.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constant.NewConstant;
import com.vertineko.shospital.constant.RedisKeyConstant;
import com.vertineko.shospital.constrain.errorDef.error.AdminErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.AdminException;
import com.vertineko.shospital.dao.AdminDO;
import com.vertineko.shospital.dao.dto.req.*;
import com.vertineko.shospital.dao.mapper.AdminMapper;
import com.vertineko.shospital.service.AdminService;
import com.vertineko.shospital.usr.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, AdminDO> implements AdminService {

    private final RedissonClient redisson;

    private final StringRedisTemplate stringRedisTemplate;

    private final AdminMapper adminMapper;

    private static final int KEY_ALIVE_TIME = 4;

    private static final int LOCK_ALIVE_TIME = 1000;

    @Override
    public String login(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUsername();
        String key = RedisKeyConstant.ADMIN_LOGIN_KEY_PREFIX.getKey() + username;
        //首先检测登录状态
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            throw new AdminException(AdminErrorCode.ADMIN_LOGIN_REPEAT);
        }
        LambdaQueryWrapper<AdminDO> queryWrapper = Wrappers.lambdaQuery(AdminDO.class)
                .eq(AdminDO::getUsername, adminLoginDTO.getUsername())
                .eq(AdminDO::getPassword, adminLoginDTO.getPassword());
        AdminDO temp = adminMapper.selectOne(queryWrapper);
        if (temp == null) {
            throw new AdminException(AdminErrorCode.ADMIN_IS_NOT_EXISTED);
        }
        //构造Jwt的内容
        Map<String, String> claims = new HashMap<>();
        claims.put("id", temp.getId() + "");
        claims.put("username", temp.getUsername());
        claims.put("role", temp.getRole().getMsg());
        //设置有过期时限的缓存key
        stringRedisTemplate.opsForValue().set(key, JwtUtil.createToken(claims));
        stringRedisTemplate.expire(key, KEY_ALIVE_TIME, TimeUnit.HOURS);
        return username;
    }

    @Override
    public int addAdmin(AdminInsertDTO requestParam) {
        //先查找该Username是否已经被占用
        AdminDO admin = getById(requestParam.getUsername());
        if (admin != null) {
            throw new AdminException(AdminErrorCode.ADMIN_USERNAME_REPEATED);
        }
        //没被占用，尝试获取锁
        RReadWriteLock rLock = redisson.getReadWriteLock(RedisKeyConstant.ADMIN_INFO_RWLOCK_KEY_PREFIX.getKey() + requestParam.getUsername());
        rLock.writeLock().lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            AdminDO adminDo = new AdminDO();
            BeanUtil.copyProperties(requestParam, adminDo);
            adminDo.setNewflag(NewConstant.NEW_USER);
            return adminMapper.insert(adminDo);
        }finally {
            rLock.writeLock().unlock();
        }
    }

    @Override
    public AdminDO getById(Long id) {
        LambdaQueryWrapper<AdminDO> queryWrapper = Wrappers.lambdaQuery(AdminDO.class)
                .eq(AdminDO::getId, id);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public AdminDO getByUsername(String username) {
        LambdaQueryWrapper<AdminDO> queryWrapper = Wrappers.lambdaQuery(AdminDO.class)
                .eq(AdminDO::getUsername, username);
        return adminMapper.selectOne(queryWrapper);
    }

    @Override
    public int removeById(Long id) {
        AdminDO admin = getById(id);
        if (admin == null) {
            throw new AdminException(AdminErrorCode.USER_IS_NOT_ADMIN);
        }
        RReadWriteLock rLock = redisson.getReadWriteLock(RedisKeyConstant.ADMIN_INFO_RWLOCK_KEY_PREFIX.getKey() + admin.getId());
        rLock.writeLock().lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            return adminMapper.deleteById(admin);
        }finally {
            rLock.writeLock().unlock();
        }
    }

    @Override
    public int removeByUsername(String username) {
        AdminDO admin = getByUsername(username);
        if (admin == null) {
            throw new AdminException(AdminErrorCode.USER_IS_NOT_ADMIN);
        }
        RReadWriteLock rLock = redisson.getReadWriteLock(RedisKeyConstant.ADMIN_INFO_RWLOCK_KEY_PREFIX.getKey() + admin.getId());
        rLock.writeLock().lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            return adminMapper.deleteById(admin);
        }finally {
            rLock.writeLock().unlock();
        }
    }

    @Override
    public int updateById(UpdateAdminByIdDTO requestParam) {
        AdminDO admin = getById(requestParam.getId());
        if (admin == null) {
            throw new AdminException(AdminErrorCode.USER_IS_NOT_ADMIN);
        }
        RReadWriteLock rLock = redisson.getReadWriteLock(RedisKeyConstant.ADMIN_INFO_RWLOCK_KEY_PREFIX.getKey() + admin.getId());
        rLock.writeLock().lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            BeanUtil.copyProperties(requestParam, admin);
            admin.setNewflag(NewConstant.OLD_USER);
            return adminMapper.updateById(admin);
        }finally {
            rLock.writeLock().unlock();
        }

    }

    @Override
    public int updateByUsername(UpdateAdminByUsernameDTO requestParam) {
        AdminDO admin = getById(requestParam.getUsername());
        if (admin == null) {
            throw new AdminException(AdminErrorCode.USER_IS_NOT_ADMIN);
        }
        RReadWriteLock rLock = redisson.getReadWriteLock(RedisKeyConstant.ADMIN_INFO_RWLOCK_KEY_PREFIX.getKey() + admin.getId());
        rLock.writeLock().lock(LOCK_ALIVE_TIME, TimeUnit.MILLISECONDS);
        try {
            BeanUtil.copyProperties(requestParam, admin);
            admin.setNewflag(NewConstant.OLD_USER);
            return adminMapper.updateById(admin);
        }finally {
            rLock.writeLock().unlock();
        }
    }

    @Override
    public AdminPageDTO getAdminPage(AdminPageDTO requestParam) {
        LambdaQueryWrapper<AdminDO> queryWrapper = Wrappers.lambdaQuery(AdminDO.class)
                .like(AdminDO::getUsername, requestParam.getUsername())
                .like(AdminDO::getName, requestParam.getName())
                .like(AdminDO::getTele, requestParam.getTele())
                .eq(AdminDO::getNewflag, requestParam.getNewFlag());
        return adminMapper.selectPage(requestParam, queryWrapper);
    }


}
