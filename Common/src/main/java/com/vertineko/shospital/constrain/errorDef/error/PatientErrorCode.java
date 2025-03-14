package com.vertineko.shospital.constrain.errorDef.error;

import com.vertineko.shospital.constrain.errorDef.IErrorCode;

public enum PatientErrorCode implements IErrorCode {

    DONT_DO_RESERVATION_AGAIN("P00001","请不要重复预约！"),

    RESERVATION_NOT_EXISTED("P00002", "该预约记录已不存在！"),;

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
