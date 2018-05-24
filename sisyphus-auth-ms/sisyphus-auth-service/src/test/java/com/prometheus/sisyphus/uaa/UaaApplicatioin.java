package com.prometheus.sisyphus.uaa;

import com.prometheus.sisyphus.uaa.repository.support.WiselyRepositoryImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by wushaoyong on 2017/11/29.
 */
@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(repositoryBaseClass = WiselyRepositoryImpl.class)
public class UaaApplicatioin {
}
