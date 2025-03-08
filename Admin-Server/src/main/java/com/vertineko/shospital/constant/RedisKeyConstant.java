package com.vertineko.shospital.constant;

public enum RedisKeyConstant {
    REDIS_SESSION_KEY_PREFIX("user_context"),

    ADMIN_LOGIN_KEY_PREFIX("admin:login:");

    private String key;

    RedisKeyConstant(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
