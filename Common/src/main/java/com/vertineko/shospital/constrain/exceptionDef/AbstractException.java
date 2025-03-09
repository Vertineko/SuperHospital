package com.vertineko.shospital.constrain.exceptionDef;

import lombok.Data;

@Data
public class AbstractException extends RuntimeException{

    private String code;
    private String msg;


    public AbstractException(String message, Throwable cause, String code, String msg){
        super(message, cause);
        this.code = code;
        this.msg = msg;
    }


}
