package com.vertineko.shospital.constrain.exceptionDef.exception;

import com.vertineko.shospital.constrain.errorDef.error.AdminErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.AbstractException;

public class AdminException extends AbstractException {

    public AdminException(String message, Throwable cause, AdminErrorCode adminErrorCode) {
        super(message, cause, adminErrorCode.getErrorCode(), adminErrorCode.getMessage());
    }

    public AdminException(AdminErrorCode adminErrorCode) {
        super(adminErrorCode.getMessage(), null, adminErrorCode.getErrorCode(), adminErrorCode.getMessage());
    }
    @Override
    public String toString() {
        return "ADMIN_EXCEPTION[msg:" + this.getCode()
                + "\n message:" + this.getMsg()
                + "\n cause:" + this.getCause()
                + "]\n";
    }
}
