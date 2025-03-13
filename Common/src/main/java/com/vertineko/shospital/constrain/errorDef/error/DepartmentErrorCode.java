package com.vertineko.shospital.constrain.errorDef.error;

import com.vertineko.shospital.constrain.errorDef.IErrorCode;
import lombok.Getter;

@Getter
public enum DepartmentErrorCode implements IErrorCode {
    DEPARTMENT_NOT_EXISTED("D00001", "指定的部门不存在！");

    private final String code;

    private final String message;

    private DepartmentErrorCode(final String code, final String message) {
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
