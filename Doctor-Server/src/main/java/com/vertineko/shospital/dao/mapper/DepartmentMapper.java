package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.DepartmentDO;
import com.vertineko.shospital.dto.doctor.req.DepartmentPageDTO;
import com.vertineko.shospital.dto.doctor.res.DepartmentPageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DepartmentMapper extends BaseMapper<DepartmentDO> {

    IPage<DepartmentPageVO> selectDepartmentPage(DepartmentPageDTO requestParam);
}
