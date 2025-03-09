package com.vertineko.shospital.service;

import com.vertineko.shospital.dto.doctor.req.InsertDoctorDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByIdDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByUsernameDTO;

public interface DoctorService {

    int insertDoctor(InsertDoctorDTO requestParam);

    int removeById(Long id);

    int removeByUsername(String username);

    int updateById(UpdateDoctorByIdDTO requestParam);

    int updateByUsername(UpdateDoctorByUsernameDTO requestParam);
}
