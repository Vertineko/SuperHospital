package com.vertineko.shospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dto.LoginDTO;
import com.vertineko.shospital.dto.doctor.req.*;
import com.vertineko.shospital.dto.doctor.res.DocAbsPageVO;
import com.vertineko.shospital.dto.doctor.res.DocDetailVO;
import com.vertineko.shospital.dto.doctor.res.DoctorPageVO;
import com.vertineko.shospital.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    /**
     * 医生登录
     * @param requestParam
     * @return
     */
    @RequestMapping("/doctor/api/login")
    public Result<String> login(@RequestBody LoginDTO requestParam){
        return Results.success(doctorService.login(requestParam));
    }

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

    /**
     * 分页查询医生
     * @param requestParam 分页查询参数
     * @return 分页记录
     */
    @RequestMapping("/doctor/api/getDocPage")
    public Result<IPage<DoctorPageVO>> getDoctorPage(@RequestBody DoctorPageDTO requestParam){
        return Results.success(doctorService.getDoctorPage(requestParam));
    }

    @RequestMapping("/doctor/api/updateById")
    public Result<Integer> updateById(@RequestBody UpdateDoctorByIdDTO requestParam){
        return Results.success(doctorService.updateById(requestParam));
    }

    @RequestMapping("/doctor/api/updateByUsername")
    public Result<Integer> updateByUsername(@RequestBody UpdateDoctorByUsernameDTO requestParam){
        return Results.success(doctorService.updateByUsername(requestParam));
    }

    @RequestMapping("/doctor/api/getDocDetailByUsername")
    public Result<DocDetailVO> getDocDetailByUsername(@RequestParam String username){
        return Results.success(doctorService.getDocDetail(username));
    }

    /**
     * 根据部门ID获取医生列表
     * @param requestParam 请求参数只包含department_id
     * @return 查询的结果
     */
    @Deprecated
    @RequestMapping("/doctor/api/getDocDepPageByDepId")
    public Result<DocDepartPageDTO> getDocDepPageByDepId(@RequestBody DocDepartPageDTO requestParam){
        return Results.success(doctorService.getDocDepPageByDepId(requestParam));
    }


    @RequestMapping("/doctor/api/getDocAbsPage")
    public Result<IPage<DocAbsPageVO>> getDocAbsPage(@RequestBody DocAbsPageDTO requestParam){
        return Results.success(doctorService.getDocAbsPage(requestParam));
    }
}
