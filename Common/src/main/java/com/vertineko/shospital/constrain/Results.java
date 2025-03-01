package com.vertineko.shospital.constrain;

/**
 * 构建返回响应体的工具类
 */
public class Results {

    /**
     * 无返回值-请求成功
     * @return Result<Void>
     */
    public static Result<Void> success(){
        return Result.<Void>builder()
                .code("200")
                .message("success")
                .build();
    }

    /**
     * 有返回值-请求成功
     * @param data 返回数据
     * @return Result<T>
     */
    public static <T> Result<T> success(T data){
        return Result.<T>builder()
                .code("200")
                .message("success")
                .data(data)
                .build();
    }

    /**
     * 无返回值-请求失败
     * @return Result<Void>
     */
    public static Result<Void> fail(){
        return Result.<Void>builder()
                .code("500")
                .message("failed")
                .build();
    }

    /**
     * 无返回值-请求失败
     * @param message 返回消息
     * @return Result<Void>
     */
    public static Result<Void> fail(String message){
        return Result.<Void>builder()
                .code("500")
                .message(message)
                .build();
    }


}
