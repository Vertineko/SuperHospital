package com.vertineko.shospital.dto;

import lombok.Data;

@Data
public class modifyPasswordDTO {

    private String originPassword;

    private String newPassword;
}
