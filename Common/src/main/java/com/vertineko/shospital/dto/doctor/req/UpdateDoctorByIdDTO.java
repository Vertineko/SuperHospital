package com.vertineko.shospital.dto.doctor.req;

import com.vertineko.shospital.constant.Sex;
import lombok.Data;

@Data
public class UpdateDoctorByIdDTO {

    private Long id;

    private String password;

    private String name;

    private Integer age;

    private Sex sex;

    private String tele;

    private String mail;

    private String worktime;

    private Long department;

    private Double price;

}
