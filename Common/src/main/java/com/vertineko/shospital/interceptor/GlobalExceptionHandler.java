package com.vertineko.shospital.interceptor;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.constrain.exceptionDef.exception.AdminException;
import com.vertineko.shospital.constrain.exceptionDef.exception.DoctorException;
import com.vertineko.shospital.constrain.exceptionDef.exception.PatientException;
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

    @SneakyThrows
    @ExceptionHandler(AdminException.class)
    public Result<String> handleOthersException(AdminException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURL().toString(), e);
        return Results.fail(e.getMsg());
    }

    @SneakyThrows
    @ExceptionHandler(DoctorException.class)
    public Result<String> handleOthersException(DoctorException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURL().toString(), e);
        return Results.fail(e.getMsg());
    }

    @SneakyThrows
    @ExceptionHandler(PatientException.class)
    public Result<String> handleOthersException(PatientException e, HttpServletRequest request) {
        log.error("[{}] {}", request.getMethod(), request.getRequestURL().toString(), e);
        return Results.fail(e.getMsg());
    }
}
