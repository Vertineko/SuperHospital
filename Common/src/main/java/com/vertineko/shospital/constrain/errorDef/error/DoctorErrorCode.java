package com.vertineko.shospital.constrain.errorDef.error;

import com.vertineko.shospital.constrain.errorDef.IErrorCode;

public enum DoctorErrorCode implements IErrorCode {
    //D1xxxx 登陆注册分区的错误
    DOCTOR_USERNAME_REPEATED("D10001", "该医生用户名或邮箱已存在!"),
    DOCTOR_HAS_ALREADY_LOGIN("D10002", "该医生用户已经处于登录状态，请勿重复登录"),
    //D2xxxx 业务分区错误
    DOCTOR_USER_NOT_EXISTED("D20001", "该医生用户不存在！"),

    RECORD_NOT_EXISTED("R00001", "该病历已被其他用户修改或不存在！");


    private String code;
    private String message;

    DoctorErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
