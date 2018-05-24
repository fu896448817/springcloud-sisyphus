package com.prometheus.sisyphus.uaa.api.controller.v1;

import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.uaa.common.utils.UaaAuthUtils;
import com.prometheus.sisyphus.uaa.service.BootstrapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wushaoyong on 2018/1/23.
 */
@Api(description = "页面引导接口")
@RequestMapping("/v1")
@RestController
public class BootstrapController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BootstrapService bootstrapService;

    @ApiOperation(value = "是否需要引导", notes = "0 不需要引导, 1 需要引导")
    @GetMapping("/api/uaa/bootstrap")
    public RestResult bootstrap(@RequestParam("version") Float version) {
        Long userId = UaaAuthUtils.getUserId();
        Long tenantId = UaaAuthUtils.getTenantId();
        if (bootstrapService.bootstrap(userId, version, tenantId)) {
            return RestResultBuilder.builder().success().build();
        } else {
            return RestResultBuilder.builder().failure().build();
        }

    }

}
