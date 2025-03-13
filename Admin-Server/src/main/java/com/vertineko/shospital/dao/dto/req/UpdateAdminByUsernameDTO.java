package com.vertineko.shospital.dao.dto.req;

import lombok.Data;

@Data
public class UpdateAdminByUsernameDTO {

    private String username;

    private String password;

    private String tele;

    private String name;

}
