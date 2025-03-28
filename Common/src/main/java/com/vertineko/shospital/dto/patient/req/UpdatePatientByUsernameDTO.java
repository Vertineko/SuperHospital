package com.vertineko.shospital.dto.patient.req;

import com.vertineko.shospital.constant.Sex;
import lombok.Data;

@Data
public class UpdatePatientByUsernameDTO {

    private String username;

    private String name;

    private String password;

    private Sex sex;

    private Integer age;

    private String tele;
}
