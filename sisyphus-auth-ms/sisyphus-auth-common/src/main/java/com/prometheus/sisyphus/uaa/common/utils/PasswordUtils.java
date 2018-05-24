package com.prometheus.sisyphus.uaa.common.utils;

import com.prometheus.sisyphus.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码工具类
 */
public class PasswordUtils {
    private final static Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    public static String passwordEncoder(String password) {
        if (StringUtils.isBlank(password)) {
            logger.error(":>>> password is null");
            return null;
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(-1);
        String hashedPassword = passwordEncoder.encode(password);
        logger.info(":>>> hashedPassword is:{}", hashedPassword);
        return hashedPassword;
    }
}
