package com.vertineko.shospital.constrain.errorDef.error;

import com.vertineko.shospital.constrain.errorDef.IErrorCode;

public enum PatientErrorCode implements IErrorCode {

    PATIENT_TOKEN_MISSING("A10003", "用户未登录！"),
    PATIENT_TOKEN_WRONG("A10004", "该TOKEN已过期或失效，请重新登录！"),

    DONT_DO_RESERVATION_AGAIN("P00001","请不要重复预约！"),

    PATIENT_IS_NOT_EXISTED("D10003", "该患者用户不存在！"),
    OLD_PASSWORD_NOT_MATCH("D10004", "输入的原密码与账号原密码不一致！"),
    OLD_PASSWORD_SAME_WITH_NEW_PASSWORD("D10005", "输入的新密码与账号原密码一致！"),

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
