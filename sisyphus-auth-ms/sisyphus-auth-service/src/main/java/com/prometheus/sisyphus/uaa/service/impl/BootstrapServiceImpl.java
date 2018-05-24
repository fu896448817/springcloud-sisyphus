package com.prometheus.sisyphus.uaa.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.prometheus.sisyphus.uaa.entity.UaaBootstrap;
import com.prometheus.sisyphus.uaa.repository.UaaBootstrapRepository;
import com.prometheus.sisyphus.uaa.service.BootstrapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Optional;

/**
 * Created by wushaoyong on 2018/1/23.
 */
@Service
public class BootstrapServiceImpl implements BootstrapService {

    private Logger logger = LoggerFactory.getLogger(BootstrapServiceImpl.class);

    @Autowired
    private UaaBootstrapRepository uaaBootstrapRepository;

    @Override
    public boolean bootstrap(@NotNull Long userId, @NotNull Float version, @NotNull Long tenantId) {
        UaaBootstrap param = new UaaBootstrap(userId, tenantId);
        UaaBootstrap instance = uaaBootstrapRepository.findOne(Example.of(param));
        if (instance == null) {
            instance = new UaaBootstrap(userId, version, tenantId);
            instance.setCreatedBy(userId.toString());
            instance.setGmtCreate(new Date());
            instance.setLastModifiedBy(userId.toString());
            instance.setGmtModified(new Date());
            logger.info("未查询到引导 需要创建一个", JSON.toJSONString(instance, SerializerFeature.PrettyFormat));
            uaaBootstrapRepository.save(instance);
            return true;
        }
        float currentVersion = Optional.ofNullable(instance.getVersion()).orElse(0F);
        if (currentVersion < version) {
            logger.debug("当前版本: {} 小于参数版本: {} 需要进行引导", currentVersion, param);
            instance.setVersion(version);
            uaaBootstrapRepository.save(instance);
            return true;
        }
        logger.debug("当前版本: {} 大于等于参数版本: {} 不用进行引导", currentVersion, param);
        return false;
    }
}
