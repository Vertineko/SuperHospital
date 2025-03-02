package com.vertineko.shospital.exception.error;

import com.vertineko.shospital.constrain.error.IErrorCode;

public enum AdminErrorCode implements IErrorCode {
    ADMIN_LOGIN_REPEAT("A00001", "MESSAGE");


    private String code;
    private String message;

    AdminErrorCode(String code, String message) {
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
