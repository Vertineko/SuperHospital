package com.vertineko.shospital.dao;

import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constant.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocterDO {

    private Long id;

    private String username;

    private String password;

    private Integer age;

    private Sex sex;

    private String tele;

    private String mail;

    private String worktime;

    private Long department;

    private Role role;
}
