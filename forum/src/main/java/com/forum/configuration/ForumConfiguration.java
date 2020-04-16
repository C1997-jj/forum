package com.forum.configuration;

import com.forum.interceptors.LoginRequiredInterceptor;
import com.forum.interceptors.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
public class ForumConfiguration implements WebMvcConfigurer{

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    private PassportInterceptor passportInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor).addPathPatterns("/user/*/");
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/user/*/");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
