package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.dto.req.AdminLoginDTO;
import com.vertineko.shospital.dao.dto.req.AdminPageDTO;
import com.vertineko.shospital.dao.dto.req.UpdateAdminByIdDTO;
import com.vertineko.shospital.dao.dto.req.UpdateAdminByUsernameDTO;
import com.vertineko.shospital.dto.doctor.req.DoctorPageDTO;
import com.vertineko.shospital.dto.doctor.req.InsertDoctorDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByIdDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByUsernameDTO;
import com.vertineko.shospital.remote.service.DoctorRemoteService;
import com.vertineko.shospital.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final DoctorRemoteService doctorRemoteService;

    @RequestMapping("/admin/api/login")
    public Result<String> login(@RequestBody AdminLoginDTO adminLoginDTO) {
        return Results.success(adminService.login(adminLoginDTO));
    }

    @RequestMapping("/admin/api/removeById/{id}")
    public Result<Integer> removeById(@PathVariable Long id) {
        return Results.success(adminService.removeById(id));
    }

    @RequestMapping("/admin/api/removeByUsername/{username}")
    public Result<Integer> removeByUsername(@PathVariable String username) {
        return Results.success(adminService.removeByUsername(username));
    }

    @RequestMapping("/admin/api/updateById")
    public Result<Integer> updateById(@RequestBody UpdateAdminByIdDTO requestParam) {
        return Results.success(adminService.updateById(requestParam));
    }

    @RequestMapping("/admin/api/updateByUsername")
    public Result<Integer> updateByUsername(@RequestBody UpdateAdminByUsernameDTO requestParam) {
        return Results.success(adminService.updateByUsername(requestParam));
    }

    @RequestMapping("/admin/api/getAdminPage")
    public Result<AdminPageDTO> getAdminPage(@RequestBody AdminPageDTO requestParam) {
        return Results.success(adminService.getAdminPage(requestParam));
    }



    //以下都是远程调用
    @RequestMapping("/admin/api/addDoc")
    public String addDoc(@RequestBody InsertDoctorDTO requestParam) {
        return doctorRemoteService.insertDoctor(requestParam);
    }

    @RequestMapping("/admin/api/removeDocById/{id}")
    public String removeDocById(@PathVariable Long id){
        return doctorRemoteService.removeById(id);
    }

    @RequestMapping("/admin/api/removeDocByUsername/{username}")
    public String removeDocByUsername(@PathVariable String username){
        return doctorRemoteService.removeByUsername(username);
    }

    @RequestMapping("/admin/api/updateDocById")
    public String updateDocById(@RequestBody UpdateDoctorByIdDTO requestParam){
        return doctorRemoteService.updateById(requestParam);
    }

    @RequestMapping("/admin/api/updateDocByUsername")
    public String updateDocByUsername(@RequestBody UpdateDoctorByUsernameDTO requestParam){
        return doctorRemoteService.updateByUsername(requestParam);
    }

    @RequestMapping("/admin/api/getDocPage")
    public String getDocPage(@RequestBody DoctorPageDTO requestParam){
        return doctorRemoteService.getDoctorPage(requestParam);
    }
}
