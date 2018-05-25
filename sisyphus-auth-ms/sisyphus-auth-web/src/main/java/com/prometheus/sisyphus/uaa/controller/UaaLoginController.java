package com.prometheus.sisyphus.uaa.controller;

import com.prometheus.sisyphus.common.exception.BusinessException;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.common.web.controller.BaseController;
import com.prometheus.sisyphus.uaa.common.domain.OauthToken;
import com.prometheus.sisyphus.uaa.exception.UaaErrorCode;
import com.prometheus.sisyphus.uaa.service.TenantService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * 功能描述: <br>
 *
 * @since: 1.0.0
 * @Author: sunliang
 * @Date: 2018/5/25
 */
@RestController
@RequestMapping("/uaa/oauth2")
public class UaaLoginController extends BaseController {
    @Autowired
    private TenantService tenantService;
    @PostMapping("/token")
    public Object getAccessToken(HttpServletRequest request, Principal principal,
                                 @RequestParam Map<String, String> parameters) {
        String clientId = parameters.get("clientId");
        String clientSecret = parameters.get("clientSecret");
        String grantType = parameters.get("grant_type");
        OauthToken oauthToken = new OauthToken();

        if (!"password".equals(grantType)) {
            oauthToken.setCode(102);
            oauthToken.setMessage("authentication mode not supported");
        }
        if (StringUtils.isNotBlank(clientId) && StringUtils.isNotBlank(clientSecret)) {
            String username = parameters.get("username");
            String password = parameters.get("password");
            logger.info("-->>{username}:{},{password}:{}",username,password);
            tenantService.getUaaTenant(clientId,clientSecret);

            return RestResultBuilder.builder().success().build();
        }
        throw new BusinessException(UaaErrorCode.PARAM_ERROR);
    }
}
