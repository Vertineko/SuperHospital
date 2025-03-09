package com.vertineko.shospital.constant;

import lombok.Getter;

@Getter
public enum RedisKeyConstant {

    REDIS_LOCK_MAX_WAIT_TIME("3000"),
    DOCTOR_INFO_LOCK_PREFIX("doctor:info:lock:"),
    DOCTOR_INFO_RWLOCK_PREFIX("doctor:info:lock:rw:");

    private final String val;

    RedisKeyConstant(String val) {
        this.val = val;
    }

}
