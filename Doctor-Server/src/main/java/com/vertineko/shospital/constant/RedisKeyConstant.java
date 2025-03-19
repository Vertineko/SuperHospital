package com.vertineko.shospital.constant;

import lombok.Getter;

@Getter
public enum RedisKeyConstant {

    REDIS_LOCK_MAX_WAIT_TIME("3000"),
    DOCTOR_INFO_LOCK_PREFIX("doctor:info:lock:"),
    DOCTOR_INFO_RWLOCK_PREFIX("doctor:info:lock:rw:"),


    REDIS_SESSION_KEY_PREFIX("token"),

    ADMIN_LOGIN_KEY_PREFIX("ADMIN:LOGIN:"),

    ADMIN_INFO_LOCK_KEY_PREFIX("ADMIN:INFO:LOCK:"),

    ADMIN_INFO_RWLOCK_KEY_PREFIX("ADMIN:INFO:RWLOCK:");


    private final String val;

    RedisKeyConstant(String val) {
        this.val = val;
    }

}
