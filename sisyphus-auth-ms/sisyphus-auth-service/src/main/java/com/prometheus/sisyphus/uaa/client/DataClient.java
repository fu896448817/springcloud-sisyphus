package com.prometheus.sisyphus.uaa.client;

import com.prometheus.sisyphus.common.model.RestResult;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by wushaoyong on 2017/12/7.
 */
@FeignClient(value = "THOTH-DATA-MS")
public interface DataClient {
    /**
     * 初始化注册的ES索引
     * @param tenantId
     * @return
     */
    @PostMapping("/v1/rpc/data-ms/chatRecord/{tenantId}/create")
    RestResult initRegisterIndexes(@PathVariable("tenantId") Long tenantId);
}
