package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.MedicineDO;
import com.vertineko.shospital.dto.doctor.req.MedicinePageDTO;
import com.vertineko.shospital.dto.doctor.res.MedicinePageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedicineMapper extends BaseMapper<MedicineDO> {

    IPage<MedicinePageVO> getMedicinePage(MedicinePageDTO requestParam);
}
