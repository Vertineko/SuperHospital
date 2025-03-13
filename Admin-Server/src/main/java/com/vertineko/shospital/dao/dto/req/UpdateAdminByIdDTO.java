package com.vertineko.shospital.dao.dto.req;

import lombok.Data;

@Data
public class UpdateAdminByIdDTO {

    private Long id;

    private String password;

    private String tele;

    private String name;
}
