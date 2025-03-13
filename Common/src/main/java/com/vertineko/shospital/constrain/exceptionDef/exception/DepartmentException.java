package com.vertineko.shospital.constrain.exceptionDef.exception;

import com.vertineko.shospital.constrain.errorDef.error.DepartmentErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.AbstractException;

public class DepartmentException extends AbstractException {
    public DepartmentException(String message, Throwable cause, String code, String msg) {
        super(message, cause, code, msg);
    }

    public DepartmentException(DepartmentErrorCode departmentErrorCode) {
        super(departmentErrorCode.getMessage(), null, departmentErrorCode.getErrorCode(), departmentErrorCode.getMessage());
    }
    @Override
    public String toString() {
        return "ADMIN_EXCEPTION[msg:" + this.getCode()
                + "\n message:" + this.getMsg()
                + "\n cause:" + this.getCause()
                + "]\n";
    }
}
