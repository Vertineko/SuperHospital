package com.vertineko.shospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.vertineko.shospital.constant.RedisKeyConstant;
import com.vertineko.shospital.dao.Admin;
import com.vertineko.shospital.dao.mapper.AdminMapper;
import com.vertineko.shospital.dto.admin.req.AdminLoginDO;
import com.vertineko.shospital.exception.AdminException;
import com.vertineko.shospital.exception.error.AdminErrorCode;
import com.vertineko.shospital.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final StringRedisTemplate stringRedisTemplate;

    private final AdminMapper adminMapper;

    @Override
    public String login(AdminLoginDO adminLoginDO) {
        String key = RedisKeyConstant.ADMIN_LOGIN_KEY_PREFIX.getKey() + adminLoginDO.getUsername();
        //首先检测登录状态
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            throw new AdminException(AdminErrorCode.ADMIN_LOGIN_REPEAT);
        }
        LambdaQueryWrapper<Admin> queryWrapper = Wrappers.lambdaQuery(Admin.class)
                .eq(Admin::getUsername, adminLoginDO.getUsername())
                .eq(Admin::getPassword, adminLoginDO.getPassword());
        Admin temp = adminMapper.selectOne(queryWrapper);
        if (temp == null) {
            return null;
        }
        //设置有过期时限的缓存key
        stringRedisTemplate.opsForValue().set(key, "213");
        stringRedisTemplate.expire(key, 1, TimeUnit.HOURS);
        return key;
    }
}
