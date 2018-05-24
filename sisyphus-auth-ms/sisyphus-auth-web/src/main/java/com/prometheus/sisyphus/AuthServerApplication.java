package com.prometheus.sisyphus;

import com.prometheus.sisyphus.common.util.ExceptionLog;
import com.prometheus.sisyphus.uaa.repository.support.WiselyRepositoryImpl;
import com.prometheus.sisyphus.uaa.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryBaseClass = WiselyRepositoryImpl.class)
//@EnableEurekaClient
//@EnableFeignClients
// 后端服务不添加跨域支持
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(
        excludeFilters={@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,value=CorsFilter.class)})
public class AuthServerApplication {
    private final static Logger logger = LoggerFactory.getLogger(AuthServerApplication.class);

    @Bean(name = "auditorAware")
    public AuditorAware<String> auditorAware() {
        return () -> SecurityUtils.getCurrentUserUsername();
    }

    public static void main(String[] args) {
        try {
            SpringApplication.run(AuthServerApplication.class, args);
            logger.info(":>>> AuthServer start success,^_^");
        } catch (Exception e) {
            logger.error(":>>> AuthServer start failed,[$errorMsg] is:{}", ExceptionLog.getErrorStack(e));
            System.exit(0);
        }

    }

//    @Configuration
//    @Order(1)
//    protected static class RestSecurity extends WebSecurityConfigurerAdapter {
//        //不需要权限控制的URL
//        @Override
//        public void configure(WebSecurity web) throws Exception {
//            web.ignoring().antMatchers("/v1/rpc/**", "/swagger-ui.html", "/error", "/swagger-resources/**", "/v2/api-docs", "/v1/api/uaa/register/**","/v1/api/uaa/bootstrap");
//        }
//    }
}
