package com.prometheus.sisyphus.uaa.service;


import com.prometheus.sisyphus.uaa.entity.UaaUser;

/**
 * 用户Service
 * Created by tommy on 17/11/11.
 */
public interface UaaUserService {

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(UaaUser user);

    /**
     * 通过手机号获取用户列表
     *
     * @param phoneNumber
     * @return
     */
    UaaUser getUser(String phoneNumber);

    /**
     * 判断
     *
     * @param phoneNumber
     * @return
     */
    boolean phoneNumberIsUsed(String phoneNumber);

}
