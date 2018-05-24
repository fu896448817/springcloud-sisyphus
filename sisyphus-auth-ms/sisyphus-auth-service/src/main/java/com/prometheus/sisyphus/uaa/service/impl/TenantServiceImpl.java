package com.prometheus.sisyphus.uaa.service.impl;

import com.alibaba.fastjson.JSON;
import com.prometheus.sisyphus.common.exception.BusinessException;
import com.prometheus.sisyphus.common.service.LoggerService;
import com.prometheus.sisyphus.common.util.StringUtils;
import com.prometheus.sisyphus.uaa.common.utils.PasswordUtils;
import com.prometheus.sisyphus.uaa.entity.UaaTenant;
import com.prometheus.sisyphus.uaa.exception.UaaErrorCode;
import com.prometheus.sisyphus.uaa.repository.UaaTenantRepository;
import com.prometheus.sisyphus.uaa.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tommy on 2017/11/26.
 */
@Service
public class TenantServiceImpl extends LoggerService implements TenantService {
    @Autowired
    private UaaTenantRepository uaaTenantRepository;

    @Override
    public List<Long> getAllTenantIds() {
        logger.info(":>>> start getAllTenantIds");
        List<UaaTenant> tenants = uaaTenantRepository.findAll();
        List<Long> ids = new ArrayList<>();
        if (tenants == null || tenants.isEmpty()) {
            logger.warn(":>>> tenants is null or is empty");
            return ids;
        }
        for (UaaTenant uaaTenant : tenants) {
            ids.add(uaaTenant.getId());
        }
        logger.info(":>>> tenant ids:{}", JSON.toJSON(ids));
        return ids;
    }

    @Override
    public void register(String password, String phoneNumber) {

    }

    @Override
    public UaaTenant getUaaTenant(Long id) {
        logger.info(":>>> start getUaaTenant with {$id}:{}", id);
        if (id == null) {
            logger.error(":>>> id is null,return null");
            return null;
        }
        UaaTenant uaaTenant = uaaTenantRepository.findOne(id);
        if (uaaTenant == null) {
            logger.error(":>>> uaaTenant not exist");
            throw new BusinessException(UaaErrorCode.DATA_NOT_EXISTS);
        }
        boolean isSave = false;
        if (StringUtils.isBlank(uaaTenant.getClientId())) {
            uaaTenant.setClientId("1" + uaaTenant.getId());
            isSave = true;
        }
        if (StringUtils.isBlank(uaaTenant.getClientSecret())) {

            uaaTenant.setClientSecret(PasswordUtils.passwordEncoder(uaaTenant.getClientSecret() + uaaTenant.getId().toString()));
            isSave = true;
        }
        if (isSave) {
            uaaTenantRepository.save(uaaTenant);
        }
        return uaaTenant;
    }

    @Override
    public UaaTenant getUaaTenant(String clientId, String clientSecret) {
        return uaaTenantRepository.findByClientIdAndClientSecret(clientId, clientSecret);
    }
}
