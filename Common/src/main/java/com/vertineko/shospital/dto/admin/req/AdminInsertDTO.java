package com.vertineko.shospital.dto.admin.req;

import lombok.Data;

@Data
public class AdminInsertDTO {

    private String username;

    private String password;

    private String name;

    private String tele;
}
