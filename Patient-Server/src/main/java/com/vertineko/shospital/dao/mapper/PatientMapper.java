package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.PatientDO;
import com.vertineko.shospital.dto.patient.res.PatientPageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientMapper extends BaseMapper<PatientDO> {

    IPage<PatientPageVO> getPage(IPage<?> requestParam);
}
