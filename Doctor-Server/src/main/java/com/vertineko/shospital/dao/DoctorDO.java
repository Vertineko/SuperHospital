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
@TableName("doctor")
public class DoctorDO extends BaseDO{

    private Long id;

    private String username;

    private String password;

    private String name;

    private Integer age;

    private Sex sex;

    private Double price;

    private String tele;

    private String mail;

    private String worktime;

    private Long department;

    private Role role;

}
