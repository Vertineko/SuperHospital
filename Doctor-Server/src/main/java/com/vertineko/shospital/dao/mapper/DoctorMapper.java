package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.DoctorDO;
import com.vertineko.shospital.dto.doctor.req.DocAbsPageDTO;
import com.vertineko.shospital.dto.doctor.req.DoctorPageDTO;
import com.vertineko.shospital.dto.doctor.res.DocAbsPageVO;
import com.vertineko.shospital.dto.doctor.res.DoctorPageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DoctorMapper extends BaseMapper<DoctorDO> {

    IPage<DoctorPageVO> getPage(DoctorPageDTO requestParam);


    IPage<DocAbsPageVO> getDocAbsPage(DocAbsPageDTO requestParam);
}
