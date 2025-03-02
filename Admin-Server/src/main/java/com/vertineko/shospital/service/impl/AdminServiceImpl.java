package com.vertineko.shospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.vertineko.shospital.dao.Admin;
import com.vertineko.shospital.dao.mapper.AdminMapper;
import com.vertineko.shospital.dto.admin.req.AdminLoginDO;
import com.vertineko.shospital.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public String login(AdminLoginDO adminLoginDO) {
        LambdaQueryWrapper<Admin> queryWrapper = Wrappers.lambdaQuery(Admin.class)
                .eq(Admin::getUsername, adminLoginDO.getUsername())
                .eq(Admin::getPassword, adminLoginDO.getPassword());
        Admin temp = adminMapper.selectOne(queryWrapper);
        if (temp == null) {
            return null;
        }
        return "success!";
    }
}
