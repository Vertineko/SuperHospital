package com.vertineko.shospital.service;

import com.vertineko.shospital.dao.DepartmentDO;

import java.util.List;

public interface DepartmentService {


    int insertDepartment(String name);

    int deleteDepartment(Long id);

    DepartmentDO getDepartment(Long id);

    int updateDepartment(Long id, String name);

    List<DepartmentDO> getDepartments();
}
