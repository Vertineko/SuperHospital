package com.vertineko.shospital.dto.patient.req;

import com.vertineko.shospital.constant.Sex;
import lombok.Data;

@Data
public class InsertPatientDTO {

    private String name;

    private String username;

    private String password;

    private Sex sex;

    private Integer age;

    private String tele;

}
