package com.vertineko.shospital.service;

import com.vertineko.shospital.dao.PatientDO;
import com.vertineko.shospital.dao.dto.req.PatientLoginDTO;
import com.vertineko.shospital.dao.dto.req.UpdatePatientByIdDTO;
import com.vertineko.shospital.dao.dto.req.UpdatePatientByUsernameDTO;
import com.vertineko.shospital.dto.patient.req.InsertPatientDTO;
import com.vertineko.shospital.dto.patient.req.PatientPageDTO;

public interface PatientService {

    int insertPatient(InsertPatientDTO requestParam);

    int removeById(Long id);

    int removeByUsername(String username);

    String login(PatientLoginDTO requestParam);

    int updateById(UpdatePatientByIdDTO requestParam);

    int updateByUsername(UpdatePatientByUsernameDTO requestParam);

    PatientPageDTO pagePatient(PatientPageDTO requestParam);

    PatientDO getById(Long id);

    PatientDO getByUsername(String username);
}
