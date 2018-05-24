package com.prometheus.sisyphus.uaa.api.controller.v1;

import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.uaa.entity.UaaTenant;
import com.prometheus.sisyphus.uaa.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/api/uaa/tenant")
public class TenantAuthController {
    private Logger logger = LoggerFactory.getLogger(TenantController.class);
    @Autowired
    private TenantService tenantService;

    @GetMapping("/info")
    public RestResult listAllTenantIds(@RequestParam("id") Long id) {
        UaaTenant uaaTenant = tenantService.getUaaTenant(id);
        if (uaaTenant == null) {
            return RestResultBuilder.builder().failure().build();
        }
        return RestResultBuilder.builder().success(uaaTenant).build();
    }
}
