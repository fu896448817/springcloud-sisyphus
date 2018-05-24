package com.prometheus.sisyphus.uaa.service.impl;

import com.prometheus.sisyphus.common.util.Constants;
import com.prometheus.sisyphus.common.util.ExceptionUtils;
import com.prometheus.sisyphus.common.util.StringUtils;
import com.prometheus.sisyphus.uaa.common.utils.RSACoderUtil;
import com.prometheus.sisyphus.uaa.exception.UaaErrorCode;
import com.prometheus.sisyphus.uaa.service.RsaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.security.Key;
import java.util.Map;

/**
 * Created by wushaoyong on 2017/12/3.
 */
@Service
public class RsaServiceImpl implements RsaService {
    private Logger logger = LoggerFactory.getLogger(RsaServiceImpl.class);

    @Autowired
    private JedisCluster jc;

    @Override
    public String publicKey(String key) {
        logger.debug("RsaServiceImpl.publicKey param key:{}", key);
        String publicKey = null;
        try {
            Map<String, Key> keyMap = RSACoderUtil.initKey();
            publicKey = RSACoderUtil.getPublicKey(keyMap);
            String privateKey = RSACoderUtil.getPrivateKey(keyMap);

            String redisKey = String.format(Constants.REDIS_MSG_PRIVATE_KEY, key);
            jc.set(redisKey, privateKey);
            logger.debug("设置: {} 的私钥: {} 过期时间为2分钟", key, privateKey);
            jc.expire(redisKey, 120);
        } catch (Exception ex) {
            logger.error("获取公钥的时候发生异常", ex);
            ExceptionUtils.business(UaaErrorCode.PUBLIC_KEY_NOT_EXISTS);
        }
        return publicKey;
    }

    @Override
    public String decrypt(String text, String key) {
        logger.debug("RsaServiceImpl.publicKey param key:{}, text:{}", key, text);
        try {
            String redisKey = String.format(Constants.REDIS_MSG_PRIVATE_KEY, key);
            String privateKey = jc.get(redisKey);
            if (StringUtils.isEmpty(privateKey)) {
                logger.warn("RsaServiceImpl.decrypt redisKey:{} privateKey is empty", redisKey);
                ExceptionUtils.business(UaaErrorCode.DECRYPT_ERROR);
            }
            logger.debug("RsaServiceImpl.decrypt redisKey:{}, privateKey:{}", redisKey, privateKey);
            byte[] bytes = RSACoderUtil.decryptByPrivateKey(text, privateKey);
            return new String(bytes);
        } catch (Exception ex) {
            logger.warn("解密的时候发生异常", ex);
            ExceptionUtils.business(UaaErrorCode.DECRYPT_ERROR);
        }
        return null;
    }
}
