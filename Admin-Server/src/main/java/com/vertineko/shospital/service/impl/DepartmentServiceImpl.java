package com.vertineko.shospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constrain.errorDef.error.DepartmentErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DepartmentException;
import com.vertineko.shospital.dao.DepartmentDO;
import com.vertineko.shospital.dao.mapper.DepartmentMapper;
import com.vertineko.shospital.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentDO> implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    @Override
    public int insertDepartment(String name) {
        DepartmentDO departmentDO = DepartmentDO.builder()
                .name(name)
                .build();
        return departmentMapper.insert(departmentDO);
    }

    @Override
    public int deleteDepartment(Long id) {
        //首先找存在
        DepartmentDO department = getDepartment(id);
        if (department == null) {
            throw new DepartmentException(DepartmentErrorCode.DEPARTMENT_NOT_EXISTED);
        }
        return departmentMapper.deleteById(id);
    }

    @Override
    public DepartmentDO getDepartment(Long id) {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = new LambdaQueryWrapper<DepartmentDO>()
                .eq(DepartmentDO::getId, id);
        return departmentMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateDepartment(Long id, String name) {
        DepartmentDO department = getDepartment(id);
        if (department == null) {
            throw new DepartmentException(DepartmentErrorCode.DEPARTMENT_NOT_EXISTED);
        }
        department.setName(name);
        return departmentMapper.updateById(department);
    }
}
