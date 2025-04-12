package com.vertineko.shospital.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constrain.errorDef.error.DepartmentErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DepartmentException;
import com.vertineko.shospital.dao.DepartmentDO;
import com.vertineko.shospital.dao.DoctorDO;
import com.vertineko.shospital.dao.mapper.DepartmentMapper;
import com.vertineko.shospital.dao.mapper.DoctorMapper;
import com.vertineko.shospital.dto.doctor.req.DepartmentPageDTO;
import com.vertineko.shospital.dto.doctor.res.DepartmentPageVO;
import com.vertineko.shospital.dto.doctor.res.DepartmentVO;
import com.vertineko.shospital.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, DepartmentDO> implements DepartmentService {

    private final DepartmentMapper departmentMapper;

    private final DoctorMapper doctorMapper;

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
        //然后看该部门中是否还有医生
        LambdaQueryWrapper<DoctorDO> queryWrapper = new LambdaQueryWrapper<DoctorDO>()
                .eq(DoctorDO::getDepartment, id);
        List<DoctorDO> doctors = doctorMapper.selectList(queryWrapper);
        if (doctors != null && !doctors.isEmpty()) {
            throw new DepartmentException(DepartmentErrorCode.DOCTORS_IS_NOT_EMPTY);
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

    @Override
    public List<DepartmentVO> getDepartments() {
        LambdaQueryWrapper<DepartmentDO> queryWrapper = Wrappers.lambdaQuery(DepartmentDO.class);
        List<DepartmentDO> list = departmentMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(list, DepartmentVO.class);

    }

    @Override
    public IPage<DepartmentPageVO> selectDepartmentPage(DepartmentPageDTO requestParam) {
        return departmentMapper.selectDepartmentPage(requestParam);
    }
}
