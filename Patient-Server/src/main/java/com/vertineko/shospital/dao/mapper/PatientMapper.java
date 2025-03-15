package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vertineko.shospital.dao.PatientDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper extends BaseMapper<PatientDO> {
}
