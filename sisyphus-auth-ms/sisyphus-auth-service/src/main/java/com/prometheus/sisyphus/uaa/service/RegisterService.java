package com.prometheus.sisyphus.uaa.service;

/**
 * Created by wushaoyong on 2017/12/4.
 */
public interface RegisterService {
    /**
     * 注册租户
     *
     * @param userName
     * @param password
     * @return
     */
    boolean register(String userName, String password);

    /**
     * 重置密码
     *
     * @param phoneNumber
     * @param password
     * @return
     */
    boolean retrievePassword(String phoneNumber, String password);
}
