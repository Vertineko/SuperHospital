package com.vertineko.shospital.constant;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("管理员"),
    DOCTOR("医生"),
    PATIENT("患者");



    //枚举值
    private final String msg;

    Role(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return msg;
    }

}
