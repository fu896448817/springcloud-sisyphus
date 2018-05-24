package com.prometheus.sisyphus.uaa.repository;

import com.prometheus.sisyphus.uaa.entity.UaaTenant;
import com.prometheus.sisyphus.uaa.repository.support.WiselyRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by tommy on 2017/11/26.
 */
public interface UaaTenantRepository extends WiselyRepository<UaaTenant, Long> {
    UaaTenant findByClientIdAndClientSecret(String clientId, String clientSecret);
}
