package com.prometheus.sisyphus.uaa.service.impl;

import com.prometheus.sisyphus.uaa.UaaApplicatioin;
import com.prometheus.sisyphus.uaa.service.UaaUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wushaoyong on 2017/12/4.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UaaApplicatioin.class)
public class UaaUserServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(UaaUserServiceImpl.class);

    @Autowired
    UaaUserService uaaUserService;

    @Test
    public void register() throws Exception {

    }

    @Test
    public void phoneNumberIsUsed() throws Exception {
        boolean result = uaaUserService.phoneNumberIsUsed("110");
        logger.debug("获取电话的结果是: {}", result);
    }

}