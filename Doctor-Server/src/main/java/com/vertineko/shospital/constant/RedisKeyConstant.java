package com.vertineko.shospital.constant;

import lombok.Getter;

@Getter
public enum RedisKeyConstant {

    REDIS_LOCK_MAX_WAIT_TIME("3000"),

    REDIS_SESSION_KEY_PREFIX("token"),

    DOCTOR_LOGIN_KEY_PREFIX("DOCTOR:LOGIN:"),

    DOCTOR_INFO_LOCK_KEY_PREFIX("DOCTOR:INFO:LOCK:"),

    DOCTOR_INFO_RWLOCK_KEY_PREFIX("DOCTOR:INFO:RWLOCK:");


    private final String val;

    RedisKeyConstant(String val) {
        this.val = val;
    }

}
