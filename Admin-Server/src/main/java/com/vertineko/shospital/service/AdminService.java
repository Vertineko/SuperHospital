package com.vertineko.shospital.service;

import com.vertineko.shospital.dao.AdminDO;
import com.vertineko.shospital.dao.dto.req.*;

public interface AdminService {

    String login(AdminLoginDTO requestParam);

    int addAdmin(AdminInsertDTO requestParam);

    AdminDO getById(Long id);

    AdminDO getByUsername(String username);

    int removeById(Long id);

    int removeByUsername(String username);

    int updateById(UpdateAdminByIdDTO requestParam);

    int updateByUsername(UpdateAdminByUsernameDTO requestParam);

    AdminPageDTO getAdminPage(AdminPageDTO requestParam);

}
