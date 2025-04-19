package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.DoctorDO;
import com.vertineko.shospital.dto.LoginDTO;
import com.vertineko.shospital.dto.doctor.req.*;
import com.vertineko.shospital.dto.doctor.res.DocAbsPageVO;
import com.vertineko.shospital.dto.doctor.res.DocDetailVO;
import com.vertineko.shospital.dto.doctor.res.DoctorPageVO;
import com.vertineko.shospital.dto.doctor.res.DoctorVO;
import com.vertineko.shospital.dto.modifyPasswordDTO;

import java.util.List;

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

    @Deprecated
    DocDepartPageDTO getDocDepPageByDepId(DocDepartPageDTO requestParam);

    /**
     * 用于患者端按照当天上班表和所选择的科室，筛选出的医生列表
     * @param requestParam 请求参数
     * @return 返回参数
     */
    IPage<DocAbsPageVO> getDocAbsPage(DocAbsPageDTO requestParam);

    Integer modifyPassword(modifyPasswordDTO requestParam);

    /**
     * 根据传入的医生ID查询同部门的其他医生
     * @param id
     * @return
     */
    List<DoctorVO> getDoctorList(Long id);
}
