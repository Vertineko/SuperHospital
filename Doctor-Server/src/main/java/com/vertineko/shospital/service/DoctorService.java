package com.vertineko.shospital.service;

import com.vertineko.shospital.dao.DoctorDO;
import com.vertineko.shospital.dao.dto.req.DoctorPageDTO;
import com.vertineko.shospital.dto.doctor.req.InsertDoctorDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByIdDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByUsernameDTO;
import com.vertineko.shospital.dto.doctor.res.DocDetailVO;

public interface DoctorService {

    int insertDoctor(InsertDoctorDTO requestParam);

    int removeById(Long id);

    int removeByUsername(String username);

    int updateById(UpdateDoctorByIdDTO requestParam);

    int updateByUsername(UpdateDoctorByUsernameDTO requestParam);

    DoctorDO getById(Long id);

    DoctorDO getByUsername(String username);

    DoctorPageDTO getDoctorPage(DoctorPageDTO requestParam);

    DocDetailVO getDocDetail(String username);
}
