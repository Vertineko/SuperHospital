package com.vertineko.shospital.interceptor;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@Slf4j
@RestControllerAdvice
@Order(2)
public class GlobalExceptionHandler {

    @SneakyThrows
    @ExceptionHandler(Throwable.class)
    public Result<String> handleOthersException(Throwable e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURL().toString(), e);
        return Results.fail("发生了未知错误");
    }
}
