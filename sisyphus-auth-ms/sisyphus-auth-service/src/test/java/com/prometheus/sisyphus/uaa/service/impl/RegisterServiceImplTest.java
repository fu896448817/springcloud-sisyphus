package com.prometheus.sisyphus.uaa.service.impl;

import com.prometheus.sisyphus.common.util.DateUtils;
import com.prometheus.sisyphus.uaa.UaaApplicatioin;
import com.prometheus.sisyphus.uaa.common.utils.PasswordUtils;
import com.prometheus.sisyphus.uaa.entity.UaaTenant;
import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.repository.UaaTenantRepository;
import com.prometheus.sisyphus.uaa.repository.UaaUserRepository;
import com.prometheus.sisyphus.uaa.service.RegisterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertTrue;

/**
 * Created by sunliang on 2017/12/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApplicatioin.class)
public class RegisterServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(RegisterServiceImplTest.class);

    @Autowired
    RegisterService registerService;

    @Autowired
    UaaUserRepository uaaUserRepository;

    @Autowired
    UaaTenantRepository uaaTenantRepository;

    @Test
    public void register() throws Exception {
        String UserName = "18310811659";
        registerService.register(UserName, "12597758");

        UaaUser uaaUser = new UaaUser();
        uaaUser.setUsername(UserName);
        UaaUser resultUser = uaaUserRepository.findOne(Example.of(uaaUser));
        logger.debug("已经注册的用户: {}", resultUser);

        UaaTenant uaaTenant = new UaaTenant();
        uaaTenant.setTelephone(UserName);
        UaaTenant resultTenant = uaaTenantRepository.findOne(Example.of(uaaTenant));
        logger.debug("已经注册的租户: {}", resultTenant);
        assertTrue(resultTenant.getCreatedBy().equals(UserName));
    }

    @Test
    public void registerSpecial() throws Exception {

        String phoneNumber = "18860700510";
        String password = "123456";
        Date current = new Date();

        UaaUser uaaUser = new UaaUser();
        uaaUser.setPhoneNumber(phoneNumber);
        uaaUser.setUsername(phoneNumber);
        uaaUser.setPassword(PasswordUtils.passwordEncoder(password));
        uaaUser.setGmtModified(current);
        uaaUser.setGmtCreate(current);
        uaaUser.setCreatedBy(phoneNumber);
        uaaUser.setLastName(phoneNumber);
        uaaUser.setTenantId(1016L);
        uaaUserRepository.save(uaaUser);
    }


}