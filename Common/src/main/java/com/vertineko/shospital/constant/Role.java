package com.vertineko.shospital.constant;

public enum Role {
    ADMIN("管理员"),
    DOCTER("医生"),
    PATIENT("患者");



    //枚举值
    private final String msg;

    Role(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
