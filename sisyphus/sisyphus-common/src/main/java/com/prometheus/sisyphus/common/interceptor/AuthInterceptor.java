package com.prometheus.sisyphus.common.interceptor;

import com.prometheus.sisyphus.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * AuthInterceptor Interceptor
 * Created by zhuhuaiqi on 2017/7/26.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        logger.debug(":>>> start AuthInterceptor preHandle");
        logger.debug(":>>> 拦截GET请求");
        String userId = request.getParameter("userId");
        HashMap<String, Long> userCache = new HashMap<>();
        if (StringUtils.isNotBlank(userId) && !"null".equals(userId) && !"undefined".equals(userId)) {
            Long uId = Long.valueOf(userId);
            userCache.put("userId", uId);
        }

        logger.debug(":>>> userId is:{}", userId);
        String tenantId = request.getParameter("tenantId");
        if (StringUtils.isNotBlank(tenantId) && !"null".equals(tenantId) && !"undefined".equals(tenantId)) {
            Long tId = Long.valueOf(tenantId);
            userCache.put("tenantId", tId);
        }
        ThreadLocalCache.baseSignatureRequestThreadLocal.set(userCache);
        logger.debug(":>>> tenantId is:{}", tenantId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        ThreadLocalCache.baseSignatureRequestThreadLocal.remove();
    }

}
