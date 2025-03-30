package com.vertineko.shospital.constrain.errorDef.error;

import com.vertineko.shospital.constrain.errorDef.IErrorCode;

public enum PatientErrorCode implements IErrorCode {

    DONT_DO_RESERVATION_AGAIN("P00001","请不要重复预约！"),

    RESERVATION_NOT_EXISTED("R00001", "该预约记录已不存在！"),

    PATIENT_USERNAME_ALREADY_EXISTED("P00003", "该用户名已注册，请修改后重试！"),

    PATIENT_NOT_EXISTED("P00004","该用户不存在或者已被其他用户修改，请重试！"),

    PATIENT_USER_HAS_ALREADY_LOGIN("P00005","您已经处于登录状态！"),

    PATIENT_USER_NOT_EXISTED("P00006","不存在该用户！"),

    RESERVATION_STATUS_ALREADY_CANCELED("ROOOO2", "预约状态已经为取消！");

    private final String code;

    private final String message;

    PatientErrorCode(String code,  String message) {
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
