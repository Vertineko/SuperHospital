package com.vertineko.shospital.service.impl;

import com.vertineko.shospital.dto.admin.req.AdminLoginDO;
import com.vertineko.shospital.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSetviceImpl implements AdminService {

    @Override
    public String login(AdminLoginDO adminLoginDO) {
        return "";
    }
}
