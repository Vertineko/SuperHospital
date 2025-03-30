package com.vertineko.shospital.interceptor.config;

import com.vertineko.shospital.interceptor.PatientContextInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final PatientContextInterceptor patientContextInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(patientContextInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/patient/api/login")
                .excludePathPatterns("/patient/api/register")
                .excludePathPatterns("/patient/api/pagePatient");

    }
}
