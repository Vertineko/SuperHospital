package com.vertineko.shospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.DepartmentDO;
import com.vertineko.shospital.dao.mapper.DepartmentMapper;
import com.vertineko.shospital.dto.doctor.req.DepartmentPageDTO;
import com.vertineko.shospital.dto.doctor.res.DepartmentPageVO;
import com.vertineko.shospital.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    @RequestMapping("/doctor/api/department/insert")
    public Result<Integer> insertDepartment(String name){
        return Results.success(departmentService.insertDepartment(name));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/doctor/api/department/{id}")
    public Result<Integer> deleteDepartment(@PathVariable Long id){
        return Results.success(departmentService.deleteDepartment(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/doctor/api/department/{id}")
    public Result<DepartmentDO> getDepartment(@PathVariable Long id){
        return Results.success(departmentService.getDepartment(id));
    }

    @RequestMapping("/doctor/api/department")
    public Result<Integer> updateDepartment(@RequestParam("id") Long id, @RequestParam("name") String name){
        return Results.success(departmentService.updateDepartment(id, name));
    }

    @RequestMapping("/doctor/api/department/getAll")
    public Result<List<DepartmentDO>> getAllDepartment(){
        return Results.success(departmentService.getDepartments());
    }

    @RequestMapping("/doctor/api/department/getPage")
    public Result<IPage<DepartmentPageVO>> getDepartmentPage(@RequestBody DepartmentPageDTO requestParam){
        return Results.success(departmentMapper.selectDepartmentPage(requestParam));
    }
}
