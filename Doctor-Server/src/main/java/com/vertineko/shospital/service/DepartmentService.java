package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.DepartmentDO;
import com.vertineko.shospital.dto.doctor.req.DepartmentPageDTO;
import com.vertineko.shospital.dto.doctor.res.DepartmentPageVO;
import com.vertineko.shospital.dto.doctor.res.DepartmentVO;

import java.util.List;

public interface DepartmentService {


    int insertDepartment(String name);

    int deleteDepartment(Long id);

    DepartmentDO getDepartment(Long id);

    int updateDepartment(Long id, String name);

    List<DepartmentVO> getDepartments();

    IPage<DepartmentPageVO> selectDepartmentPage(DepartmentPageDTO requestParam);
}
