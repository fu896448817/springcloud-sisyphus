package com.prometheus.sisyphus.uaa.service;

/**
 * Created by wushaoyong on 2018/1/23.
 */
public interface BootstrapService {
    /**
     * 引导用户
     *
     * @param userId
     * @param version
     * @return true需要引导 false不需要引导
     */
    boolean bootstrap(Long userId, Float version, Long tenantId);
}
