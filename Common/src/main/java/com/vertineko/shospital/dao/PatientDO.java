package com.vertineko.shospital.dao;

import com.vertineko.shospital.constant.Role;
import com.vertineko.shospital.constant.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDO {

    private Long id;

    private String username;

    private String password;

    private Sex sex;

    private Integer age;

    private String tele;

    private Role role;
}
