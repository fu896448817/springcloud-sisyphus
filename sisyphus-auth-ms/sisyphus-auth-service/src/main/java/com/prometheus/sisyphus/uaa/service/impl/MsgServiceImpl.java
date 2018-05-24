//package com.changyou.thoth.uaa.service.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.aliyuncs.DefaultAcsClient;
//import com.aliyuncs.IAcsClient;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
//import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
//import com.aliyuncs.exceptions.ClientException;
//import com.aliyuncs.profile.DefaultProfile;
//import com.aliyuncs.profile.IClientProfile;
//import com.changyou.thoth.common.exception.BusinessException;
//import com.changyou.thoth.common.exception.GlobalErrorCode;
//import com.changyou.thoth.common.util.Constants;
//import com.changyou.thoth.uaa.entity.UaaUser;
//import com.changyou.thoth.uaa.service.MsgService;
//import com.changyou.thoth.uaa.service.UaaUserService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.env.Environment;
//import org.springframework.stereotype.Service;
//import redis.clients.jedis.JedisCluster;
//
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by wushaoyong on 2017/12/4.
// */
//@Service
//public class MsgServiceImpl implements MsgService {
//
//    private Logger logger = LoggerFactory.getLogger(MsgServiceImpl.class);
//
//    //产品名称:云通信短信API产品,开发者无需替换
//    static final String product = "Dysmsapi";
//    //产品域名,开发者无需替换
//    static final String domain = "dysmsapi.aliyuncs.com";
//
//    @Value("${aliyun.msg.accessKeyId:}")
//    String accessKeyId;
//
//    @Value("${aliyun.msg.accessKeySecret:}")
//    String accessKeySecret;
//
//    @Value("${aliyun.msg.msgCaptcha.templateCode:}")
//    // 手机验证码
//    String msgCaptchaTemplateCode;
//    // 注册成功短信模板
//    @Value("${aliyun.msg.register.templateCode:}")
//    String registerTemplateCode;
//
//    @Value("${aliyun.msg.signName:知U}")
//    String signName;
//
//    @Autowired
//    JedisCluster jc;
//
//    @Autowired
//    UaaUserService uaaUserService;
//
//    @Autowired
//    private Environment env;
//    // 即将过期短信模板
//    final static String packageExpiringTemplateCode = "SMS_113460446";
//    // 已经过期短信模板
//    final static String packageExpiredTemplateCode = "SMS_113455467";
//    // 已经失效,过期8天
//    final static String packageExpiredOverDuedTemplateCode = "SMS_113445483";
//
//    @Override
//    public SendSmsResponse sendCaptchaMsg(String phoneNum) throws ClientException {
//        return sendCaptchaMsg(phoneNum, true);
//    }
//
//    @Override
//    public SendSmsResponse sendCaptchaMsg(String phoneNum, boolean validPhoneNumberRegistered) throws ClientException {
//        if (validPhoneNumberRegistered) {
//            UaaUser user = uaaUserService.getUser(phoneNum);
//            if (user == null) {
//                logger.error("电话号码不存在: {}", phoneNum);
//                throw new BusinessException(GlobalErrorCode.RESOURCE_NOT_FOUND);
//            }
//        }
//
//        String templateCode = msgCaptchaTemplateCode;
//        String templateParam = "{\"code\":\"" + generateCode(phoneNum) + "\"}";
//        return this.sendMsg(phoneNum, templateCode, templateParam);
//    }
//
//    private String generateCode(String userIdentification) {
//        String code = (int) (Math.random() * 9999) + "";
//        while (code.length() < 4) {
//            code = 0 + code;
//        }
//        String key = String.format(Constants.REDIS_MSG_CAPTCHA, userIdentification);
//        jc.set(key, code);
//        logger.debug("设置: {} 的短信验证码: {} 过期时间为10分钟", userIdentification, code);
//        jc.expire(key, 60 * 10);
//        return code;
//    }
//
//    /**
//     * @param phoneNum
//     * @param templateCode
//     * @param templateParam
//     * @return 发送成功返回true 发送失败返回false
//     * @throws ClientException
//     */
//    private SendSmsResponse sendMsg(String phoneNum, String templateCode, String templateParam) throws ClientException {
//        //可自助调整超时时间
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//        //初始化acsClient,暂不支持region化
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
//        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
//        IAcsClient acsClient = new DefaultAcsClient(profile);
//        //组装请求对象-具体描述见控制台-文档部分内容
//        SendSmsRequest request = new SendSmsRequest();
//        //必填:待发送手机号
//        request.setPhoneNumbers(phoneNum);
//        //必填:短信签名-可在短信控制台中找到
//        request.setSignName(signName);
//        //必填:短信模板-可在短信控制台中找到
//        request.setTemplateCode(templateCode);
//        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
//        request.setTemplateParam(templateParam);
//
//        String envProfile = "default";
//        if (env.getActiveProfiles() != null && env.getActiveProfiles().length > 0) {
//            envProfile = env.getActiveProfiles()[0];
//        }
//        if ("prod".equals(envProfile.toLowerCase()) || "test".equals(envProfile.toLowerCase()) || true) {
//            logger.debug("短信发送请求体:{}", JSON.toJSONString(request));
//            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
//            logger.debug("短信发送响应:{}", JSON.toJSONString(sendSmsResponse));
//            return sendSmsResponse;
//        } else {
//            logger.debug("测试环境不需要真正发送短信");
//            SendSmsResponse sendSmsResponse = new SendSmsResponse();
//            sendSmsResponse.setBizId("470807012528444608^0");
//            sendSmsResponse.setCode("OK");
//            sendSmsResponse.setMessage("OK");
//            sendSmsResponse.setRequestId("DF7DDD37-910A-4D7F-8339-982E26B3D34A");
//            return sendSmsResponse;
//        }
//    }
//
//    @Override
//    public void sendRegisterMsg(String phoneNum, String domain) throws ClientException {
//        String templateCode = registerTemplateCode;
//        String templateParam = "{\"name\":\"" + domain + "\"}";
//        this.sendMsg(phoneNum, templateCode, templateParam);
//    }
//
//    @Override
//    public boolean validate(String phoneNum, String code, HttpServletResponse response) {
//        if (code == null || phoneNum == null) {
//            return false;
//        }
//        String key = String.format(Constants.REDIS_MSG_CAPTCHA, phoneNum);
//        String cachedCode = jc.get(key);
//        if (code.equals(cachedCode)) {
//            return true;
//        }
//        logger.debug("校验不通过 key的过期时间缩短 20秒 实现次数限制的效果");
//        int expireTime = Integer.parseInt(jc.ttl(key).longValue() + "");
//        expireTime = expireTime - 20;
//        if (expireTime > 0) {
//            jc.expire(key, expireTime);
//        } else {
//            jc.del(key);
//        }
//        return false;
//    }
//}
