package com.vertineko.shospital.constant;

import lombok.Getter;

@Getter
public enum RedisKeyConstant {
    //前端传入时 token的标识
    REDIS_SESSION_KEY_PREFIX("token"),

    PATIENT_LOGIN_KEY_PREFIX("PATIENT:LOGIN:"),

    PATIENT_INFO_LOCK_KEY_PREFIX("PATIENT:INFO:LOCK:"),

    PATIENT_INFO_RWLOCK_KEY_PREFIX("PATIENT:INFO:RWLOCK:"),

    ;

    private final String key;

    RedisKeyConstant(String key) {
        this.key = key;
    }

}
