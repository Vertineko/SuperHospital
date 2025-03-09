package com.vertineko.shospital.usr;

/**
 * 用户上下文
 */
public final class UserUtils {

    private static final ThreadLocal<UserDO> threadLocal = new ThreadLocal<>();

    /**
     * 设置用户上下文
     */
    public static void setUser(UserDO user) {
        threadLocal.set(user);
    }

    /**
     * 获取用户上下文
     * @return UserDO
     */
    public static UserDO getUser() {
        return threadLocal.get();
    }

    /**
     * 清除用户上下文
     */
    public static void clear() {
        threadLocal.remove();
    }
}
