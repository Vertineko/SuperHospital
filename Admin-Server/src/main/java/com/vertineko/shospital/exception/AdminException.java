package com.vertineko.shospital.exception;

import com.vertineko.shospital.constrain.exception.AbstractException;
import com.vertineko.shospital.exception.error.AdminErrorCode;

public class AdminException extends AbstractException{

    public AdminException(String message, Throwable cause, AdminErrorCode adminErrorCode) {
        super(message, cause, adminErrorCode.getErrorCode(), adminErrorCode.getMessage());
    }

    public AdminException(AdminErrorCode adminErrorCode) {
        super(null, null, adminErrorCode.getErrorCode(), adminErrorCode.getMessage());
    }
    @Override
    public String toString() {
        return "ADMIN_EXCEPTION[msg:" + this.getCode()
                + "\n message:" + this.getMsg()
                + "\n cause:" + this.getCause()
                + "]\n";
    }
}
