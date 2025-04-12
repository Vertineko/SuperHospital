package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.dto.req.AdminInsertDTO;
import com.vertineko.shospital.dao.dto.req.AdminPageDTO;
import com.vertineko.shospital.dao.dto.req.UpdateAdminByIdDTO;
import com.vertineko.shospital.dao.dto.req.UpdateAdminByUsernameDTO;
import com.vertineko.shospital.dao.dto.res.AdminPageVO;
import com.vertineko.shospital.dao.dto.res.AdminVO;
import com.vertineko.shospital.dto.LoginDTO;

public interface AdminService {

    String login(LoginDTO requestParam);

    int addAdmin(AdminInsertDTO requestParam);


    AdminVO getAdmin(String username);

    int removeById(Long id);

    int removeByUsername(String username);

    int updateById(UpdateAdminByIdDTO requestParam);

    int updateByUsername(UpdateAdminByUsernameDTO requestParam);

    IPage<AdminPageVO> getAdminPage(AdminPageDTO requestParam);

}
