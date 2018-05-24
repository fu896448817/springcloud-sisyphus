package com.prometheus.sisyphus.uaa.service.impl;

import com.alibaba.fastjson.JSON;
import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.repository.UaaUserRepository;
import com.prometheus.sisyphus.uaa.service.UaaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wushaoyong on 2017/12/4.
 */
@Service
public class UaaUserServiceImpl implements UaaUserService {

    private Logger logger = LoggerFactory.getLogger(UaaUserServiceImpl.class);

    @Autowired
    private UaaUserRepository uaaUserRepository;

    @Override
    public UaaUser getUser(String phoneNumber) {
        UaaUser uaaUser = new UaaUser();
        uaaUser.setPhoneNumber(phoneNumber);
        UaaUser user = uaaUserRepository.findOne(Example.of(uaaUser));
        logger.debug("使用手机号: {}的用户是: {}", phoneNumber, JSON.toJSON(user));
        return user;
    }

    @Override
    public boolean phoneNumberIsUsed(String phoneNumber) {
        UaaUser uaaUser = new UaaUser();
        uaaUser.setPhoneNumber(phoneNumber);
        long count = uaaUserRepository.count(Example.of(uaaUser));
        logger.debug("数据库中存在: {}个使用了相同电话号码的账号", count);
        return count > 0;
    }

    @Override
    public void updateUser(UaaUser user) {
        logger.debug("更新用户: {}", user.getId());
        user.setGmtModified(new Date());
        uaaUserRepository.save(user);
    }
}
