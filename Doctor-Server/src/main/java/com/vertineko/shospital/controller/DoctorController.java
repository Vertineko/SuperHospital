package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dto.doctor.req.InsertDoctorDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByIdDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByUsernameDTO;
import com.vertineko.shospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    /**
     * 录入医生信息
     * @param requestParam
     * @return
     */
    @RequestMapping("/doctor/api/insertDoctor")
    public Result<Integer> insertDoctor(@RequestBody InsertDoctorDTO requestParam){
        return Results.success(doctorService.insertDoctor(requestParam));
    }

    /**
     * 根据id删除医生信息
     * @param id
     * @return
     */
    @RequestMapping("/doctor/api/removeById/{id}")
    public Result<Integer> removeById(@PathVariable("id") Long id){
        return Results.success(doctorService.removeById(id));
    }

    /**
     * 根据username删除医生信息
     * @param username
     * @return
     */
    @RequestMapping("/doctor/api/removeByUsername/{username}")
    public Result<Integer> removeByUsername(@PathVariable("username") String username){
        return Results.success(doctorService.removeByUsername(username));
    }

    @RequestMapping("/doctor/api/updateById")
    public Result<Integer> updateById(@RequestBody UpdateDoctorByIdDTO requestParam){
        return Results.success(doctorService.updateById(requestParam));
    }

    @RequestMapping("/doctor/api/updateByUsername")
    public Result<Integer> updateByUsername(@RequestBody UpdateDoctorByUsernameDTO requestParam){
        return Results.success(doctorService.updateByUsername(requestParam));
    }
}
