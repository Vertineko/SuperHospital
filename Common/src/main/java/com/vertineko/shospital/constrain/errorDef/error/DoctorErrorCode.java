package com.vertineko.shospital.constrain.errorDef.error;

import com.vertineko.shospital.constrain.errorDef.IErrorCode;

public enum DoctorErrorCode implements IErrorCode {
    //D1xxxx 登陆注册分区的错误
    DOCTOR_USERNAME_REPEATED("D10001", "该医生用户名或邮箱已存在!"),
    DOCTOR_HAS_ALREADY_LOGIN("D10002", "您已经处于登录状态！"),
    DOCTOR_IS_NOT_EXISTED("D10003", "该医生用户不存在！"),
    OLD_PASSWORD_NOT_MATCH("D10004", "输入的原密码与账号原密码不一致！"),
    OLD_PASSWORD_SAME_WITH_NEW_PASSWORD("D10005", "输入的新密码与账号原密码一致！"),
    DOCTOR_CAN_NOT_DELETE("D10006", "该医生下仍有预约或病历待处理，无法删除！"),

    DOCTOR_TOKEN_MISSING("A10003", "用户未登录！"),
    DOCTOR_TOKEN_WRONG("A10004", "该TOKEN已过期或失效，请重新登录！"),
    //D2xxxx 业务分区错误
    DOCTOR_USER_NOT_EXISTED("D20001", "该医生用户不存在！"),

    RECORD_NOT_EXISTED("R00001", "该病历已被其他用户修改或不存在！"),


    RECORD_INSERT_FAILED("R00002", "病历插入失败！"),

    RECORD_UPDATE_FAILED("R00002", "病历更新失败！"),

    ORDER_NOT_EXISTED("O00001", "该处方不存在或者已经被删除！"),

    MEDICINE_NOT_EXISTED("M00001", "该药品不存在或者已经被删除！"),
    MEDICINE_NOT_ENOUGH("M00002", "该药品库存不足！");

    private final String code;
    private final String message;

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
