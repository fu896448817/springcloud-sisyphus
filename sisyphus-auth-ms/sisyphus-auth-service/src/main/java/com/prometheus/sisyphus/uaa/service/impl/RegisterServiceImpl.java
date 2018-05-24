package com.prometheus.sisyphus.uaa.service.impl;

import com.prometheus.sisyphus.common.exception.BusinessException;
import com.prometheus.sisyphus.common.exception.GlobalErrorCode;
import com.prometheus.sisyphus.uaa.common.utils.PasswordUtils;
import com.prometheus.sisyphus.uaa.entity.UaaTenant;
import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.repository.UaaTenantRepository;
import com.prometheus.sisyphus.uaa.repository.UaaUserRepository;
import com.prometheus.sisyphus.uaa.service.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.management.RuntimeMBeanException;
import java.util.Date;

/**
 * Created by wushaoyong on 2017/12/4.
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private UaaUserRepository uaaUserRepository;

    @Autowired
    private UaaTenantRepository uaaTenantRepository;
//
//    @Autowired
//    private DataClient dataClient;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean register(String phoneNumber, String password) {
        if (phoneNumber == null || password == null) {
            return false;
        }
        Date current = new Date();
        UaaTenant uaaTenant = new UaaTenant();
        uaaTenant.setTelephone(phoneNumber);
        uaaTenant.setGmtModified(current);
        uaaTenant.setGmtCreate(current);
        uaaTenant.setLastModifiedBy(phoneNumber);
        uaaTenant.setCreatedBy(phoneNumber);
        uaaTenant.setStatus(true);
        logger.info("-------->:"+uaaTenant.getCreatedBy());
        uaaTenantRepository.save(uaaTenant);
        Long tenantId = uaaTenant.getId();
        logger.debug("注册租户: {}", tenantId);
        uaaTenant.setClientSecret(PasswordUtils.passwordEncoder(phoneNumber + tenantId.toString()));
        uaaTenant.setClientId("1"+tenantId);
        uaaTenantRepository.save(uaaTenant);
        UaaUser uaaUser = new UaaUser();
        uaaUser.setPhoneNumber(phoneNumber);
        uaaUser.setUsername(phoneNumber);
        uaaUser.setPassword(PasswordUtils.passwordEncoder(password));
        uaaUser.setGmtModified(current);
        uaaUser.setGmtCreate(current);
        uaaUser.setCreatedBy(phoneNumber);
        uaaUser.setLastName(phoneNumber);
        uaaUser.setTenantId(uaaTenant.getId());
        uaaUserRepository.save(uaaUser);
        logger.debug("注册用户: {}", uaaUser.getId());


        // 初始化ES索引
        logger.debug("开始初始化ES索引 {}", uaaTenant.getId());
        //dataClient.initRegisterIndexes(uaaTenant.getId());
        logger.debug("完成初始化ES索引: {}", uaaTenant.getId());
        return true;
    }

    @Override
    public boolean retrievePassword(String phoneNumber, String password) {
        if (phoneNumber == null || password == null) {
            return false;
        }
        UaaUser uaaUser = new UaaUser();
        uaaUser.setPhoneNumber(phoneNumber);
        UaaUser persistedUser = uaaUserRepository.findOne(Example.of(uaaUser));
        if (persistedUser == null) {
            logger.warn("无法获取到用户: {} 修改密码失败", phoneNumber);
            throw new BusinessException(GlobalErrorCode.RESOURCE_NOT_FOUND);
        }
        persistedUser.setPassword(PasswordUtils.passwordEncoder(password));
        persistedUser.setGmtModified(new Date());
        persistedUser.setLastModifiedBy(persistedUser.getUsername());
        uaaUserRepository.save(persistedUser);
        return true;
    }
}
