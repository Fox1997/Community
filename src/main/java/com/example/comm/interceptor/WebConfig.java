package com.example.comm.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    @Autowired(required = false)
    private  SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry register){
        register.addInterceptor(sessionInterceptor).addPathPatterns("/**");

    }


}
