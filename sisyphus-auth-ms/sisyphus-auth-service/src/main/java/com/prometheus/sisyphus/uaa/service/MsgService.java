//package com.prometheus.sisyphus.uaa.service;
//
//
//import com.netflix.client.ClientException;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 短信服务
// *
// * @author wushaoyong
// */
//public interface MsgService {
//
//    /**
//     * 发送验证消息
//     *
//     * @param phoneNum
//     * @throws ClientException
//     */
//    SendSmsResponse sendCaptchaMsg(String phoneNum) throws ClientException;
//
//    /**
//     * 发送注册成功消息
//     *
//     * @param phoneNum
//     * @param domain
//     * @throws ClientException
//     */
//    void sendRegisterMsg(String phoneNum, String domain) throws ClientException;
//
//    /**
//     * 验证短信验证码是否正确
//     * @param phoneNum
//     * @param code
//     * @param response
//     * @return
//     */
//    boolean validate(String phoneNum, String code, HttpServletResponse response);
//
//    /**
//     * 发送短信验证码
//     * @param phoneNum
//     * @param validPhoneNumberRegistered
//     * @return
//     * @throws ClientException
//     */
//    SendSmsResponse sendCaptchaMsg(String phoneNum, boolean validPhoneNumberRegistered) throws ClientException;
//
//}
