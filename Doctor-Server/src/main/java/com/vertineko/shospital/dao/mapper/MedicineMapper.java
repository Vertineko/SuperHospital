package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.MedicineDO;
import com.vertineko.shospital.dto.doctor.req.MedicinePageDTO;
import com.vertineko.shospital.dto.doctor.res.MedicinePageVO;
import com.vertineko.shospital.dto.doctor.res.MedicinesVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MedicineMapper extends BaseMapper<MedicineDO> {

    IPage<MedicinePageVO> getMedicinePage(MedicinePageDTO requestParam);

    List<MedicinesVO> getMedicineList();
}
