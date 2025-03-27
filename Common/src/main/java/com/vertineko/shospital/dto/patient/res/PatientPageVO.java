package com.vertineko.shospital.dto.patient.res;

import com.vertineko.shospital.constant.Sex;
import lombok.Data;

@Data
public class PatientPageVO {

    private String username;

    private String name;

    private Sex sex;

    private Integer age;

    private String tele;

}
