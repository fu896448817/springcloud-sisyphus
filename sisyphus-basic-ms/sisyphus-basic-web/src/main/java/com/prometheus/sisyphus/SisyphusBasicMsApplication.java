package com.prometheus.sisyphus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by sunliangliang
 */

@EnableEurekaClient
@SpringBootApplication
//@EnableFeignClients
//@EnableResourceServer
public class SisyphusBasicMsApplication {
    private final static Logger logger = LoggerFactory.getLogger(SisyphusBasicMsApplication.class);

    public static void main(String[] args) {
        logger.info(":>>> sisyphus-basic-ms start success,^_^");
        SpringApplication.run(SisyphusBasicMsApplication.class, args);
    }
    @Configuration
    protected static class RestSecurity extends WebSecurityConfigurerAdapter {
        //不需要权限控制的URL
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/v1/**","/swagger-ui.html", "/error","/swagger-resources/**","/v2/api-docs");
        }
    }
}