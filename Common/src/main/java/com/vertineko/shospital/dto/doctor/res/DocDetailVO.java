package com.vertineko.shospital.dto.doctor.res;

import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constant.Sex;
import lombok.Data;

@Data
public class DocDetailVO {

    private String username;

    private String password;

    private String name;

    private Integer age;

    private Sex sex;

    private String tele;

    private String mail;

    private String worktime;

    private String department;

    private String price;

    private Role role;
}
