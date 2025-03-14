package com.vertineko.shospital.constrain.exceptionDef.exception;

import com.vertineko.shospital.constrain.errorDef.error.PatientErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.AbstractException;

public class PatientException extends AbstractException {

    public PatientException(String message, Throwable cause, String code, String msg) {
        super(message, cause, code, msg);
    }
    public PatientException(PatientErrorCode errorCode) {
        super(errorCode.getMessage(), null, errorCode.getErrorCode(), errorCode.getMessage());
    }
    @Override
    public String toString() {
        return "ADMIN_EXCEPTION[msg:" + this.getCode()
                + "\n message:" + this.getMsg()
                + "\n cause:" + this.getCause()
                + "]\n";
    }

}
