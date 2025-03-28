package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.PatientDO;
import com.vertineko.shospital.dao.dto.req.UpdatePatientByIdDTO;
import com.vertineko.shospital.dto.LoginDTO;
import com.vertineko.shospital.dto.patient.req.UpdatePatientByUsernameDTO;
import com.vertineko.shospital.dto.patient.req.InsertPatientDTO;
import com.vertineko.shospital.dto.patient.req.PatientPageDTO;
import com.vertineko.shospital.dto.patient.res.PatientPageVO;

public interface PatientService {

    int insertPatient(InsertPatientDTO requestParam);

    int removeById(Long id);

    int removeByUsername(String username);

    String login(LoginDTO requestParam);

    int updateById(UpdatePatientByIdDTO requestParam);

    int updateByUsername(UpdatePatientByUsernameDTO requestParam);

    IPage<PatientPageVO> pagePatient(PatientPageDTO requestParam);

    PatientDO getById(Long id);

    PatientDO getByUsername(String username);
}
