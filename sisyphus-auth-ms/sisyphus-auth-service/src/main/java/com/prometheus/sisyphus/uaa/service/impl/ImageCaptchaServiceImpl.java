package com.prometheus.sisyphus.uaa.service.impl;

import com.prometheus.sisyphus.common.util.ImageCaptcha;
import com.prometheus.sisyphus.common.util.StringUtils;
import com.prometheus.sisyphus.uaa.service.ImageCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wushaoyong on 2017/11/29.
 */
@Service
//@ConditionalOnBean(JedisCluster.class)
public class ImageCaptchaServiceImpl implements ImageCaptchaService {

    private Logger logger = LoggerFactory.getLogger(ImageCaptchaServiceImpl.class);

    private static final String REDIS_IMAGE_CAPTCHA = "rampage:image:captcha:%s";

    @Autowired
    private JedisCluster jc;

    @Override
    public void generate(String userIdentification, HttpServletResponse response) throws IOException {
        // 在response中塞入图片
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setHeader("Accept-Ranges", "bytes");
        ImageCaptcha vCode = new ImageCaptcha(120, 40, 5, 100);
        // 缓存图片到redis
        cacheCode(userIdentification, vCode.getCode());
        vCode.write(response.getOutputStream());
        logger.debug("验证码: {}", vCode.getCode());
    }

    @Override
    public void generate(String userIdentification, OutputStream outputStream) throws IOException {
        ImageCaptcha vCode = new ImageCaptcha(120, 40, 5, 100);
        // 缓存图片到redis
        cacheCode(userIdentification, vCode.getCode());
        vCode.write(outputStream);
        logger.debug("验证码: {}", vCode.getCode());
    }

    private void cacheCode(String userIdentification, String code) {
        String key = String.format(REDIS_IMAGE_CAPTCHA, userIdentification);
        jc.set(key, code);
        jc.expire(key, 2 * 60);
    }


    @Override
    public Boolean validate(String userIdentification, String code) {
        logger.debug("校验 {} 的验证码{}", userIdentification, code);
        if (userIdentification == null || code == null) {
            return false;
        }
        String key = String.format(REDIS_IMAGE_CAPTCHA, userIdentification);
        String cachedCode = jc.get(key);
        boolean flag = StringUtils.equalsIgnoreCase(code, cachedCode);
        logger.debug("校验验证码结果: {}", flag);
        if (flag) {
            logger.debug("校验通过, 删除缓存");
            jc.del(key);
        } else {
            logger.debug("校验失败, 减少缓存时间");
            long ttl = jc.ttl(key);
            int expire = (int) (ttl - 30);
            if (expire < 0) {
                logger.debug("校验时间不足 删除验证码");
                jc.del(key);
            } else {
                logger.debug("校验时间剩余: {}", expire);
                jc.expire(key, expire);
            }
        }
        return flag;
    }
}
