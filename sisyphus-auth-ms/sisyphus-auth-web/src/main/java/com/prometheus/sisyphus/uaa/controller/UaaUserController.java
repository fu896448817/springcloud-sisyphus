package com.prometheus.sisyphus.uaa.controller;

import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.uaa.entity.UaaTenant;
import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.service.TenantService;
import com.prometheus.sisyphus.uaa.service.UaaUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoxinguo on 2017/9/13.
 */
@RestController
@RequestMapping("/users")
public class UaaUserController {

    private Logger logger = LoggerFactory.getLogger(com.prometheus.sisyphus.uaa.endpoint.UserController.class);
    @Autowired
    private UaaUserService uaaUserService;
    @Autowired
    private TenantService tenantService;
    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/userList")
    public RestResult userList(){
        UaaUser users = uaaUserService.getUser("18310811659");
        return RestResultBuilder.builder().success(users).build();
    }

    /**
     * 注册用户 默认开启白名单
     * @param username
     */
    @PostMapping("/signup")
    public RestResult signup(@RequestParam("username")String username, @RequestParam("password")String password) {
        tenantService.register(username,password);
        return RestResultBuilder.builder().success().build();
    }

}
