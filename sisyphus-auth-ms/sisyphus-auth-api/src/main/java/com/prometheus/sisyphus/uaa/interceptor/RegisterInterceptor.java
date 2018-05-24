package com.prometheus.sisyphus.uaa.interceptor;

import com.alibaba.fastjson.JSON;
import com.prometheus.sisyphus.common.exception.BusinessException;
import com.prometheus.sisyphus.common.exception.GlobalErrorCode;
import com.prometheus.sisyphus.uaa.common.UaaConstants;
import com.prometheus.sisyphus.uaa.common.utils.JwtDecryptUtil;
import com.prometheus.sisyphus.uaa.entity.RegisterInfo;
import com.prometheus.sisyphus.uaa.utils.RegisterAuthUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by wushaoyong on 2017/12/4.
 */
public class RegisterInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(RegisterInterceptor.class);

    @Value("${register.jwt.private.key:}")
    private String privateKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String subject = JwtDecryptUtil.decrypt(UaaConstants.REGISTER_JWT_COOKIE, privateKey, request);
        if (StringUtils.isEmpty(subject)) {
            logger.error("subject为空 subject: {}", subject);
            throw new BusinessException(GlobalErrorCode.BAD_REQUEST);
        }

        RegisterInfo info = JSON.parseObject(subject, RegisterInfo.class);
        if (info == null) {
            logger.error(" subject json 转换为 OfficialUserInfo 失败, subject: {}", subject);
            throw new BusinessException(GlobalErrorCode.BAD_REQUEST);
        }
        logger.debug("经过权限验证得到的 OfficialUserInfo: {}", JSON.toJSONString(info));
        RegisterAuthUtil.setRegisterThreadInfo(info);
        return true;
    }
}
