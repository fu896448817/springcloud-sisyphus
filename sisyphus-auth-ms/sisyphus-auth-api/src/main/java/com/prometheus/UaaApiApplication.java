package com.prometheus;

import com.prometheus.sisyphus.common.util.ExceptionLog;
import com.prometheus.sisyphus.uaa.repository.support.WiselyRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author tommy
 */
@SpringBootApplication
//@EnableResourceServer
//@EnableDiscoveryClient
//@EnableFeignClients
@EnableJpaRepositories(repositoryBaseClass = WiselyRepositoryImpl.class)
public class UaaApiApplication {
    private static final Logger logger = LoggerFactory.getLogger(UaaApiApplication.class);

    public static void main(String[] args) {
        try {
            SpringApplication.run(UaaApiApplication.class, args);
        } catch (Exception e) {
            logger.error(":>>> UaaApiApplication boot error:{}", ExceptionLog.getErrorStack(e));
        }

    }

    @Configuration
    protected static class RestSecurity extends WebSecurityConfigurerAdapter {
        //不需要权限控制的URL
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/swagger-ui.html", "/error", "/swagger-resources/**", "/v2/api-docs", "/v1/api/uaa/register/**","/v1/api/uaa/bootstrap");
        }
    }
}
