package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.DepartmentDO;
import com.vertineko.shospital.service.DepartmentService;
import feign.Param;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @RequestMapping("/admin/api/department/insert")
    public Result<Integer> insertDepartment(String name){
        return Results.success(departmentService.insertDepartment(name));
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/api/department/{id}")
    public Result<Integer> deleteDepartment(@PathVariable Long id){
        return Results.success(departmentService.deleteDepartment(id));
    }

    @RequestMapping(method = RequestMethod.GET, path = "/admin/api/department/{id}")
    public Result<DepartmentDO> getDepartment(@PathVariable Long id){
        return Results.success(departmentService.getDepartment(id));
    }

    @RequestMapping("/admin/api/department")
    public Result<Integer> updateDepartment(@Param("id") Long id, @Param("name") String name){
        return Results.success(departmentService.updateDepartment(id, name));
    }

    @RequestMapping("/admin/api/department/getAll")
    public Result<List<DepartmentDO>> getAllDepartment(){
        return Results.success(departmentService.getDepartments());
    }

}
