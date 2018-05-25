package com.prometheus.sisyphus.uaa.controller;

import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.common.web.controller.BaseController;
import com.prometheus.sisyphus.uaa.entity.UaaUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author: sunliang
 * @Date: 2018/5/25
 */
@RestController
@RequestMapping("/open-api/v1")
public class OpenApiController extends BaseController {

    @GetMapping("/hello")
    public RestResult userList(){
        String text = "welcome to sisyphus!";

        return RestResultBuilder.builder().success(text).build();
    }
}
