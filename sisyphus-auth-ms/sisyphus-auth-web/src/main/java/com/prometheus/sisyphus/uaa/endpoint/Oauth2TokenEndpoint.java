package com.prometheus.sisyphus.uaa.endpoint;

import com.alibaba.fastjson.JSON;
import com.prometheus.sisyphus.common.util.ExceptionLog;
import com.prometheus.sisyphus.uaa.common.domain.OauthToken;
import com.prometheus.sisyphus.uaa.common.utils.PasswordUtils;
import com.prometheus.sisyphus.uaa.entity.UaaTenant;
import com.prometheus.sisyphus.uaa.service.RegisterService;
import com.prometheus.sisyphus.uaa.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class Oauth2TokenEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(Oauth2TokenEndpoint.class);
    @Autowired
    private TokenEndpoint tokenEndpoint;
    @Autowired
    private TenantService tenantService;
    @Autowired
    private RegisterService registerService;
    ///open-api/v1/uaa/oauth2/token
    @RequestMapping(method = RequestMethod.POST, value = "/open-api/v1/uaa/oauth2/token")
    public Object getAccessToken(Principal principal,
                                 @RequestParam Map<String, String> parameters) {
        logger.info(":>>> start getAccessToken");
        OauthToken oauthToken = new OauthToken();
        try {
            String clientId = parameters.get("client_id");
            String clientSecret = parameters.get("client_secret");
            String grantType = parameters.get("grant_type");
            if (!"client_credentials".equals(grantType)) {
                oauthToken.setCode(102);
                oauthToken.setMessage("authentication mode not supported");
            }
            if (StringUtils.isNotBlank(clientId) && StringUtils.isNotBlank(clientSecret)) {
                UaaTenant uaaTenant = tenantService.getUaaTenant(clientId, clientSecret);
                logger.info(":>>> uaaTenant is:{}", JSON.toJSON(uaaTenant));
                if (uaaTenant != null) {
                    oauthToken.setCode(1);
                    Long currentTime = System.currentTimeMillis();
                    oauthToken.setTime_stamp(System.currentTimeMillis());
                    // 30天秒数
                    oauthToken.setExpires_in(2592000L);
                    oauthToken.setMessage("ok");
                    oauthToken.setAccess_token(PasswordUtils.passwordEncoder(clientId + clientSecret + uaaTenant.getId() + currentTime));
                    oauthToken.setRefresh_token(PasswordUtils.passwordEncoder(clientId + clientSecret + uaaTenant.getId() + (currentTime + 2592000L)));
                    return oauthToken;
                }
                oauthToken.setCode(101);
                oauthToken.setMessage("tenant is not registered");
                return oauthToken;

            }
            oauthToken.setCode(102);
            oauthToken.setMessage("params of authentication error,miss client_id or client_secret");
            return oauthToken;
        } catch (Exception e) {
            logger.error(":>>> getAccessToken error,[errorMsg] is:{}", ExceptionLog.getErrorStack(e));
            oauthToken.setCode(0);
            oauthToken.setMessage("oauth failed");
            return oauthToken;
        }
    }
}
