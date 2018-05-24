package com.prometheus.sisyphus.uaa.service;


import com.prometheus.sisyphus.uaa.entity.UaaTenant;

import java.util.List;

/**
 * 租户Service
 * Created by tommy on 17/11/11.
 */
public interface TenantService {
    List<Long> getAllTenantIds();

    /**
     * 注册
     *
     * @param password
     * @param phoneNumber
     */
    void register(String password, String phoneNumber);

    /**
     * 获取租户信息
     *
     * @param id
     * @return
     */
    UaaTenant getUaaTenant(Long id);

    UaaTenant getUaaTenant(String clientId, String clientSecret);
}
