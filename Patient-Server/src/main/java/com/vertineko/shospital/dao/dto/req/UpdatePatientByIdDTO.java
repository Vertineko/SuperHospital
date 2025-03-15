package com.vertineko.shospital.dao.dto.req;

import com.vertineko.shospital.constant.Sex;
import lombok.Data;

@Data
public class UpdatePatientByIdDTO {

    private Long id;

    private String password;

    private Sex sex;

    private Integer age;

    private String tele;

}
