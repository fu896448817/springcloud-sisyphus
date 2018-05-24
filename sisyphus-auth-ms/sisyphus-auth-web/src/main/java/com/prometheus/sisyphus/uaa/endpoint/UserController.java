package com.prometheus.sisyphus.uaa.endpoint;

import com.prometheus.sisyphus.common.exception.BusinessException;
import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.uaa.common.utils.UaaAuthUtils;
import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.exception.UaaErrorCode;
import com.prometheus.sisyphus.uaa.service.UaaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/uaa")
@RestController
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UaaUserService uaaUserService;
    @Autowired
    @Qualifier("consumerTokenServices")
    ConsumerTokenServices consumerTokenServices;

    @RequestMapping(method = RequestMethod.GET, value = "/user")
    public RestResult hasUsed(@RequestParam("username") String username) {
        logger.info(">>${username}:{}",username);
        Long userId = UaaAuthUtils.getUserId();
        logger.debug(":>>> userId is:{}", userId);
        Map<String, Long> map = new HashMap<>();
        UaaUser uaaUser = uaaUserService.getUser(username);
        if (uaaUser == null) {
            logger.error(":>>> user not exist with userName:{}", username);
            throw new BusinessException(UaaErrorCode.USER_NOT_EXISTS);
        }
        map.put("userId", uaaUser.getId());
        map.put("tenantId", uaaUser.getTenantId());
        return RestResultBuilder.builder().success(map).build();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/user/token")
    public String revokeToken(String access_token) {
        if (consumerTokenServices.revokeToken(access_token)) {
            return "注销成功";
        } else {
            return "注销失败";
        }
    }
}
