package com.vertineko.shospital.dto.doctor.res;

import lombok.Data;

@Data
public class DoctorPageVO {

    /**
     * 分页时显示
     */

    private String username;

    private String name;

    private Integer age;

    private String sex;

    private String tele;

    private String mail;

    private String worktime;

    private String departmentName;

}
