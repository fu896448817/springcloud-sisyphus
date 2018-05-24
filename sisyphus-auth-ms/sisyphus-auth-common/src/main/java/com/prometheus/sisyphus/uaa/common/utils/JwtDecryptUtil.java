package com.prometheus.sisyphus.uaa.common.utils;

import com.alibaba.fastjson.JSON;
import com.prometheus.sisyphus.common.exception.BusinessException;
import com.prometheus.sisyphus.common.exception.GlobalErrorCode;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by wushaoyong on 2017/7/26.
 */
public class JwtDecryptUtil {
    private static Logger logger = LoggerFactory.getLogger(JwtDecryptUtil.class);

    /**
     * 反解jwt_cookie
     *
     * @param cookieName cookie名称
     * @param privateKey 私钥
     * @param request
     * @return
     */
    public static String decrypt(String cookieName, String privateKey, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            logger.error("未携带cookies 无法获取到 jwt, ");
            throw new BusinessException(GlobalErrorCode.UNAUTHORIZED);
        }
        String jwt = null;

        for (Cookie cookie : cookies) {
            if (cookieName.equals(cookie.getName())) {
                jwt = cookie.getValue();
            }
        }

        if (jwt == null) {
            logger.error("无法从cookies中获取到 jwt, cookies: {}", JSON.toJSONString(cookies));
            throw new BusinessException(GlobalErrorCode.UNAUTHORIZED);
        }
        String subject = null;
        try {
            subject = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(jwt).getBody().getSubject();
        } catch (Exception e) {
            logger.error("jwt转换失败, jwt: {}", jwt);
            throw new BusinessException(GlobalErrorCode.UNAUTHORIZED);
        }
        return subject;
    }
}
