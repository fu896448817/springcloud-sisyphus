package com.prometheus.sisyphus.uaa.repository;

import com.alibaba.fastjson.JSON;
import com.prometheus.sisyphus.common.util.DateUtils;
import com.prometheus.sisyphus.uaa.entity.UaaBootstrap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by wushaoyong on 2018/1/23.
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = UaaApplicatioin.class)
public class UaaBootstrapRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UaaBootstrapRepository uaaBootstrapRepository;

    @Test
    public void insert() throws Exception {
        String current = DateUtils.getCurrentSecond();
        Long userId = Long.parseLong(current);
        Long tenantId = Long.parseLong(current);
        Float version = 0.1F;

        UaaBootstrap uaaBootstrap = new UaaBootstrap(userId, version, tenantId);
        uaaBootstrap.setCreatedBy(userId + "");
        uaaBootstrap.setLastModifiedBy(userId + "");

        uaaBootstrapRepository.save(uaaBootstrap);
        logger.debug("持久化之后的实例: {}", JSON.toJSONString(uaaBootstrap));
        UaaBootstrap instance = uaaBootstrapRepository.findOne(Example.of(new UaaBootstrap(userId, tenantId)));
        assertNotNull(instance);
    }
}