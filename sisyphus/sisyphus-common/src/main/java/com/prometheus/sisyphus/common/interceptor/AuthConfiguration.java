package com.prometheus.sisyphus.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhuhuaiqi on 2017/7/26.
 */
@Configuration
public class AuthConfiguration extends WebMvcConfigurerAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AuthInterceptor authInterceptor;

    /**
     * 添加拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("start addInterceptors for license,[开始添加拦截器]");
        registry.addInterceptor(authInterceptor)
                //添加需要验证登录用户操作权限的请求
                .addPathPatterns("/**");
        super.addInterceptors(registry);
        //排除不需要验证登录用户操作权限的请求
//                .excludePathPatterns("/userCtrl/*");


    }

    @Bean("authInterceptor")
    public AuthInterceptor getLicenseInterceptor() {
        logger.info("start getLicenseInterceptor");
        AuthInterceptor licenseInterceptor = new AuthInterceptor();
        return licenseInterceptor;
    }
}
