package com.prometheus.sisyphus.uaa.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.prometheus.sisyphus.common.interceptor.ThreadLocalCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import java.util.HashMap;

/**
 * UAA 鉴权中心工具类
 */
public class UaaAuthUtils {
    private final static Logger logger = LoggerFactory.getLogger(UaaAuthUtils.class);

    public static Long getTenantId() {
//        JSONObject uaaClaim = getUaaClaims();
//        if (uaaClaim == null) {
//            logger.error(":>>> uaaClaim is null");
//            return null;
//        }
//        Integer tenantId = (Integer) uaaClaim.get("tenantId");
//        logger.debug(":>>> tenantId is:{}", tenantId);
//        return tenantId.longValue();
        HashMap<String, Long> userMap = getThreadLocalCache();
        logger.info("----------------->$<userMap>:{}",userMap.toString());
        return userMap.get("tenantId");
//        return 1001L;
    }

    public static Long getUserId() {
//        JSONObject uaaClaim = getUaaClaims();
//        if (uaaClaim == null) {
//            logger.error(":>>> uaaClaim is null");
//            return null;
//        }
//        Integer userId = (Integer) uaaClaim.get("userId");
//        logger.debug(":>>> userId is:{}", userId);
//        return userId.longValue();
//        return 1001L;
        HashMap<String, Long> userMap = getThreadLocalCache();
        return userMap.get("userId");
    }

    //
    private static JSONObject getUaaClaims() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication instanceof OAuth2Authentication) {
            OAuth2Authentication auth2Authentication = (OAuth2Authentication) authentication;
            logger.debug(":>>> detail is:{}", JSON.toJSON(auth2Authentication.getDetails()));
            OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) auth2Authentication.getDetails();
            String token = details.getTokenValue();
            Jwt jwt = JwtHelper.decode(token);
            System.out.println(":>>> token is:" + jwt.toString());
            ;
            JSONObject uaaClaim = (JSONObject) JSON.parse(jwt.getClaims());
            System.out.println(":>>> Claims is:" + uaaClaim.toJSONString());
            return uaaClaim;
        }
        logger.error(":>>> getUaaClaims is null");
        return null;
    }

    private static HashMap getThreadLocalCache() {
        return ThreadLocalCache.baseSignatureRequestThreadLocal.get();
    }

}
