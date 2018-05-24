package com.prometheus.sisyphus.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.filter.CorsFilter;

/**
 * tommy
 */
@SpringBootApplication
//@EnableZuulProxy
//@EnableDiscoveryClient
@EnableOAuth2Sso
// 后端服务不添加跨域支持
@ComponentScan(
		excludeFilters={@ComponentScan.Filter(type= FilterType.ASSIGNABLE_TYPE,value=CorsFilter.class)})
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}
}
