package com.vertineko.shospital.dto.doctor.req;

import lombok.Data;

@Data
public class InsertDoctorDTO {

    private String username;

    private String password;

    private String name;

    private String worktime;

    private Long department;
}
