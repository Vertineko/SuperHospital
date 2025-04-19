package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.DoctorDO;
import com.vertineko.shospital.dto.doctor.req.DocAbsPageDTO;
import com.vertineko.shospital.dto.doctor.req.DoctorPageDTO;
import com.vertineko.shospital.dto.doctor.res.DocAbsPageVO;
import com.vertineko.shospital.dto.doctor.res.DoctorPageVO;
import com.vertineko.shospital.dto.doctor.res.DoctorVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DoctorMapper extends BaseMapper<DoctorDO> {

    IPage<DoctorPageVO> getPage(DoctorPageDTO requestParam);


    IPage<DocAbsPageVO> getDocAbsPage(DocAbsPageDTO requestParam);

    List<DoctorVO> getDoctorList(Long id);

    /**
     * 根据预约ID获取医生ID
     * @param id
     * @return
     */
    Long getDoctorId(Long id);
}
