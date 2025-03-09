package com.vertineko.shospital.dto.admin.req;

import lombok.Data;

@Data
public class UpdateAdminByIdDTO {

    private Long id;

    private String password;

    private String tele;

    private String name;
}
