package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("patient")
public class PatientDO {

    private Long id;

    private String username;

    private String password;

    private Sex sex;

    private Integer age;

    private String tele;

    private Role role;
}
