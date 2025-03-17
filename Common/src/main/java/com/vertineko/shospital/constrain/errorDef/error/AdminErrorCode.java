package com.vertineko.shospital.constrain.errorDef.error;

import com.vertineko.shospital.constrain.errorDef.IErrorCode;
import lombok.Getter;

@Getter
public enum AdminErrorCode implements IErrorCode {
    //A1xxxx 权限区块错误
    USER_IS_NOT_ADMIN("A00000", "该用户没有足够的权限访问该页面！"),
    ADMIN_LOGIN_REPEAT("A10001", "您已经处于登录状态！"),
    ADMIN_USERNAME_REPEATED("A10002", "该用户名已被占用！"),
    ADMIN_TOKEN_MISSING("A10003", "用户未登录！"),
    ADMIN_TOKEN_WRONG("A10004", "该TOKEN已过期或失效，请重新登录！"),
    ADMIN_IS_NOT_EXISTED("A10005", "该管理员用户不存在！"),;




    private final String code;
    private final String message;

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
