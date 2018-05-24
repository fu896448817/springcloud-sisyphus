package com.prometheus.sisyphus.uaa.api.controller.v1;

import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.uaa.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 租户Controller
 * Created by tommy on 17/11/11.
 */
@RestController
@RequestMapping("/v1/rpc/uaa/tenant")
public class TenantController {
    private Logger logger = LoggerFactory.getLogger(TenantController.class);
    @Autowired
    private TenantService tenantService;

    @GetMapping("/list")
    public RestResult listAllTenantIds() {

        List<Long> ids = tenantService.getAllTenantIds();
        if (ids == null) {
            return RestResultBuilder.builder().failure().build();
        }
        return RestResultBuilder.builder().success(ids).build();
    }
}
