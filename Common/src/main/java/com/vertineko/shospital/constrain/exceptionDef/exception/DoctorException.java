package com.vertineko.shospital.constrain.exceptionDef.exception;


import com.vertineko.shospital.constrain.errorDef.error.DoctorErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.AbstractException;

public class DoctorException extends AbstractException {

    public DoctorException(String message, Throwable cause, DoctorErrorCode docterErrorCode) {
        super(message, cause, docterErrorCode.getErrorCode(), docterErrorCode.getMessage());
    }

    public DoctorException(DoctorErrorCode docterErrorCode) {
        super(docterErrorCode.getMessage(), null, docterErrorCode.getErrorCode(), docterErrorCode.getMessage());
    }
    @Override
    public String toString() {
        return "ADMIN_EXCEPTION[msg:" + this.getCode()
                + "\n message:" + this.getMsg()
                + "\n cause:" + this.getCause()
                + "]\n";
    }
}
