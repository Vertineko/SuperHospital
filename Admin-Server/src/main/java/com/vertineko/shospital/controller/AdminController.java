package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dto.admin.req.AdminLoginDO;
import com.vertineko.shospital.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @RequestMapping("/superhospital/admin/login")
    public Result<String> login(@RequestBody AdminLoginDO adminLoginDO) {
        String res = adminService.login(adminLoginDO);
        return res == null ? Results.fail("") : Results.success(res);
    }
}
