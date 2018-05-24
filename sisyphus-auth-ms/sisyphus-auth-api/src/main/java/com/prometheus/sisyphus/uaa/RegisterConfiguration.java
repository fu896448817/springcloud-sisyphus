package com.prometheus.sisyphus.uaa;

import com.prometheus.sisyphus.uaa.interceptor.RegisterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by wushaoyong on 2017/12/4.
 */
@Configuration
public class RegisterConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册拦截器
        registry.addInterceptor(registerInterceptor()).addPathPatterns("/v1/api/uaa/register", "/v1/api/uaa/register/public-key", "/v1/api/uaa/register/retrieve-password");


    }

    @Bean
    public RegisterInterceptor registerInterceptor() {
        return new RegisterInterceptor();
    }
}

