package com.prometheus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author sunliangliang
 *
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServerApplication {
    private final static Logger logger = LoggerFactory.getLogger(EurekaServerApplication.class);

    public static void main( String[] args ) {
        logger.info(":>>> EurekaServer start success,^_^");
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
