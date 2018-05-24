package com.prometheus.sisyphus.basic.controller.v1;

import com.prometheus.sisyphus.common.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by sunliangliang
 */
@RestController
public class DemoController extends BaseController {
    @Value("${spring.cloud.config.profile:}")
    private String profile;
    @Value("${spring.cloud.config.label:}")
    private String label;

    @GetMapping("/v1/config/info")
    public String getProperties(){
        return "profile:"+profile+"\n label:"+label+"";
    }
}
