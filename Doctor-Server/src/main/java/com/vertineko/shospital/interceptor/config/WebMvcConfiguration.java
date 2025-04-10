package com.vertineko.shospital.interceptor.config;

import com.vertineko.shospital.interceptor.DoctorContextInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final DoctorContextInterceptor doctorContextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(doctorContextInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/doctor/api/login")
                .excludePathPatterns("/doctor/api/getDocAbsPage")
                .excludePathPatterns("/doctor/api/department/getPage")
                .excludePathPatterns("/doctor/api/medicine/**")
                .excludePathPatterns("/doctor/api/order/getOrders/*")
                .excludePathPatterns("/doctor/api/record/getRecordDetail/*");

    }
}
