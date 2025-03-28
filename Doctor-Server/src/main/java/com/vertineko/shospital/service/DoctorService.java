package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.DoctorDO;
import com.vertineko.shospital.dto.LoginDTO;
import com.vertineko.shospital.dto.doctor.req.*;
import com.vertineko.shospital.dto.doctor.res.DocDetailVO;
import com.vertineko.shospital.dto.doctor.res.DoctorPageVO;

public interface DoctorService {

    String login(LoginDTO requestParam);

    int insertDoctor(InsertDoctorDTO requestParam);

    int removeById(Long id);

    int removeByUsername(String username);

    int updateById(UpdateDoctorByIdDTO requestParam);

    int updateByUsername(UpdateDoctorByUsernameDTO requestParam);

    DoctorDO getById(Long id);

    DoctorDO getByUsername(String username);

    IPage<DoctorPageVO> getDoctorPage(DoctorPageDTO requestParam);

    DocDetailVO getDocDetail(String username);

    DocDepartPageDTO getDocDepPageByDepId(DocDepartPageDTO requestParam);
}
