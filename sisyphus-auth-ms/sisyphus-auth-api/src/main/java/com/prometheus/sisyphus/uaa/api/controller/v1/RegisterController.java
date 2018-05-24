package com.prometheus.sisyphus.uaa.api.controller.v1;

import com.alibaba.fastjson.JSON;

import com.prometheus.sisyphus.common.exception.BusinessException;
import com.prometheus.sisyphus.common.exception.GlobalErrorCode;
import com.prometheus.sisyphus.common.model.RestResult;
import com.prometheus.sisyphus.common.model.RestResultBuilder;
import com.prometheus.sisyphus.uaa.common.UaaConstants;
import com.prometheus.sisyphus.uaa.common.utils.PasswordUtils;
import com.prometheus.sisyphus.uaa.entity.RegisterInfo;
import com.prometheus.sisyphus.uaa.entity.UaaUser;
import com.prometheus.sisyphus.uaa.exception.UaaErrorCode;
import com.prometheus.sisyphus.uaa.service.ImageCaptchaService;
import com.prometheus.sisyphus.uaa.service.RegisterService;
import com.prometheus.sisyphus.uaa.service.RsaService;
import com.prometheus.sisyphus.uaa.service.UaaUserService;
import com.prometheus.sisyphus.uaa.utils.RegisterAuthUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 注册接口
 *
 * @author wushaoyong
 */
@Api(description = "注册接口", value = "我是value")
@RequestMapping("/v1")
@RestController
public class RegisterController {
    private Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Value("${register.jwt.private.key:}")
    private String privateKey;

    @Autowired
    private RsaService rsaService;

    @Autowired
    private RegisterService registerService;

//    @Autowired
//    private MsgService msgService;

    @Autowired
    private UaaUserService uaaUserService;

    @Autowired
    private ImageCaptchaService imageCaptchaService;

    @ApiOperation(value = "判断手机号码是否已经被注册过了", notes = "返回true表示已经被使用了， 返回false表示没有被使用。找回密码也可以用同一个接口。")
    @GetMapping("/api/uaa/register/has-used")
    public RestResult hasUsed(@RequestParam("phoneNumber") String phoneNumber) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("hasUsed", uaaUserService.phoneNumberIsUsed(phoneNumber));
        return RestResultBuilder.builder().success(map).build();
    }

    @ApiOperation(value = "获取图片验证码", notes = "")
    @GetMapping("/api/uaa/register/image-captcha")
    public void getImageCatch(@ApiParam("只要保证每个用户当时是唯一的标识即可 如 UUID") @RequestParam("uniqueCode") String uniqueCode, HttpServletResponse response) {
        try {
            imageCaptchaService.generate(uniqueCode, response);
        } catch (IOException e) {
            logger.error("生成图形校验码时发生IO异常", e);
            throw new BusinessException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "验证图片验证码", notes = "请在10分钟内完成图片验证 并且请在30分钟内完成注册 否则本次验证无效")
    @ApiResponses(value = {
            @ApiResponse(code = 3005, message = "发送短信失败")
    })
    @PostMapping("/api/uaa/register/valid-image-code")
    public RestResult validateImageCatch(@ApiParam(value = "用户唯一标识", required = true) @RequestParam("uniqueCode") String uniqueCode,
                                         @ApiParam(value = "图片验证码", required = true) @RequestParam("code") String code,
                                         HttpServletResponse response) {

        boolean isPassed = imageCaptchaService.validate(uniqueCode, code);
        if (isPassed) {
            addJwtCookie(uniqueCode, response);
            return RestResultBuilder.builder().success().build();
        } else {
            return RestResultBuilder.builder().failure().build();
        }
    }

    @ApiOperation(value = "验证图片验证码同时获取短信验证码", notes = "请在10分钟内完成图片验证 并且请在10分钟内完成短信验证")
    @ApiResponses(value = {
            @ApiResponse(code = 3005, message = "发送短信失败!"),
            @ApiResponse(code = 3006, message = "校验验证码失败!"),
            @ApiResponse(code = 3007, message = "业务限流，您的操作过于频繁！")
    })
    @PostMapping("/api/uaa/register/valid-image-code-and-send-msg-captcha")
    public RestResult validateImageCatchAndGetMsgCaptcha(@ApiParam(value = "用户唯一标识", required = true) @RequestParam("uniqueCode") String uniqueCode,
                                                         @ApiParam(value = "用户手机号", required = true) @RequestParam("phoneNumber") String phoneNumber,
                                                         @ApiParam(value = "图片验证码", required = true) @RequestParam("code") String code) {

        boolean isPassed = imageCaptchaService.validate(uniqueCode, code);
        if (isPassed) {
//            try {
//                SendSmsResponse response = msgService.sendCaptchaMsg(phoneNumber, true);
//                if (response.getCode().equals(UaaConstants.BIZ_MSG_LIMIT)) {
//                    throw new BusinessException(UaaErrorCode.BIZ_MSG_LIMIT);
//                }
//                return RestResultBuilder.builder().success(response).build();
//            } catch (ClientException e) {
//                logger.error("发送短信验证码的时候发生异常", e);
//                throw new BusinessException(UaaErrorCode.SEND_MSG_FAILED);
//            }
        } else {
            throw new BusinessException(UaaErrorCode.VALID_CODE_ERROR);
        }
        return null;

    }

    /**
     * 验证图形验证码并获取短信验证码
     * 不需要验证手机号是否注册
     *
     * @param phoneNumber
     * @param
     */
    @ApiOperation(value = "获取短信验证码", notes = "请在10分钟内完成图片验证 并且请在10分钟内完成短信验证")
    @ApiResponses(value = {
            @ApiResponse(code = 3005, message = "发送短信失败!"),
            @ApiResponse(code = 3006, message = "校验验证码失败!"),
            @ApiResponse(code = 3007, message = "业务限流，您的操作过于频繁！")
    })
    @PostMapping("/api/uaa/register/send-msg-captcha")
    public RestResult getMsgCaptcha(@ApiParam(value = "用户唯一标识", required = true) @RequestParam("uniqueCode") String uniqueCode,
                                    @ApiParam(value = "用户手机号", required = true) @RequestParam("phoneNumber") String phoneNumber,
                                    @ApiParam(value = "图片验证码", required = true) @RequestParam("code") String code) {
        boolean isPassed = imageCaptchaService.validate(uniqueCode, code);

        if (isPassed) {
//            try {
//                SendSmsResponse response = msgService.sendCaptchaMsg(phoneNumber, false);
//                if (response.getCode().equals(UaaConstants.BIZ_MSG_LIMIT)) {
//                    throw new BusinessException(UaaErrorCode.BIZ_MSG_LIMIT);
//                }
//                return RestResultBuilder.builder().success(response).build();
//            } catch (ClientException e) {
//                logger.error("发送短信验证码的时候发生异常", e);
//                throw new BusinessException(UaaErrorCode.SEND_MSG_FAILED);
//            }
        } else {
            throw new BusinessException(UaaErrorCode.VALID_CODE_ERROR);
        }
        return null;
    }

    private void addJwtCookie(String phoneNumber, HttpServletResponse response) {
        RegisterInfo registerInfo = new RegisterInfo();
        registerInfo.setPhoneNumber(phoneNumber);

        String compactJws = Jwts.builder()
                .setSubject(JSON.toJSONString(registerInfo))
                .signWith(SignatureAlgorithm.HS512, privateKey)
                .compact();

        Cookie jwtCookie = new Cookie(UaaConstants.REGISTER_JWT_COOKIE, compactJws);
        jwtCookie.setMaxAge(30 * 60);
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);
    }

    @ApiOperation(value = "注册", notes = "密码应该由: 除空格、逗号、单双引号、反斜杠外的6-18位字符组成；字符必须包含字母、数字、符号中至少2种；")
    @PostMapping("/api/uaa/register")
    public RestResult register(@RequestBody Map<String, String> map, HttpServletRequest request) {
        String password = map.get("password");
        String phoneNumber = map.get("phoneNumber");
        if (StringUtils.isEmpty(password) || StringUtils.isEmpty(phoneNumber)) {
            logger.warn("注册信息不完整:{} {}", password, phoneNumber);
            throw new BusinessException(GlobalErrorCode.BAD_REQUEST);
        }
        String pwd = "";
        try {
            pwd = rsaService.decrypt(password, phoneNumber);
            logger.debug("用户: {}的密码是: {}", phoneNumber, pwd);

            if (!validatePwd(pwd)) {
                logger.warn("密码格式校验的失败 密码是: {}", pwd);
                return RestResultBuilder.builder().failure().build();
            }
        } catch (Exception e) {
            logger.error("解密的时候发生异常", e);
            throw new BusinessException(UaaErrorCode.DECRYPT_ERROR);
        }
        // 注册用户
        registerService.register(phoneNumber, pwd);
//        //成功后发送信息
//        try {
//            msgService.sendRegisterMsg(phoneNumber, "");
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
        // 设置cookie失效
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (UaaConstants.REGISTER_JWT_COOKIE.equals(cookie.getName())) {
                cookie.setMaxAge(0);
                logger.debug("设置了 cookie 失效");
                break;
            }
        }
        return RestResultBuilder.builder().success().build();
    }

    @ApiOperation(value = "获取公钥", notes = "")
    @GetMapping("/api/uaa/register/public-key")
    public RestResult register() {
        String phoneNumber = RegisterAuthUtil.getPhoneNumber();
        logger.debug("手机号: {} 获取公钥", phoneNumber);
        try {
            String publicKey = rsaService.publicKey(phoneNumber);
            logger.debug("获取到公钥: {}", publicKey);
            return RestResultBuilder.builder().success(publicKey).build();
        } catch (Exception e) {
            logger.error("获取公钥的时候发生异常", e);
            throw new BusinessException(UaaErrorCode.PUBLIC_KEY_NOT_EXISTS);
        }
    }


    @ApiOperation(value = "短信验证码校验", notes = "请在10分钟内填写验证码 填写验证码之后请在30分钟内注册")
    @PostMapping("/api/uaa/register/msg-captcha-valid")
    public RestResult msgCaptcha(@ApiParam(value = "电话号码", required = true) @RequestParam("phoneNum") String phoneNum,
                                 @ApiParam(value = "短信验证码", required = true) @RequestParam("code") String code, HttpServletResponse response) {
//        boolean isPassed = msgService.validate(phoneNum, code, response);
        boolean isPassed = true;
        if (isPassed) {
            RegisterInfo registerInfo = new RegisterInfo();
            registerInfo.setPhoneNumber(phoneNum);

            String compactJws = Jwts.builder()
                    .setSubject(JSON.toJSONString(registerInfo))
                    .signWith(SignatureAlgorithm.HS512, privateKey)
                    .compact();

            Cookie jwtCookie = new Cookie(UaaConstants.REGISTER_JWT_COOKIE, compactJws);
            // 验证码有效时间
            jwtCookie.setMaxAge(30 * 60);
            jwtCookie.setHttpOnly(true);
            logger.debug(":>>> 短信验证码 cookie is:{}", jwtCookie);
            response.addCookie(jwtCookie);
            return RestResultBuilder.builder().success().build();
        } else {
            return RestResultBuilder.builder().failure().build();
        }
    }

    @ApiOperation("重置密码")
    @ApiResponses(value = {
            @ApiResponse(code = 3004, message = "解密失败"),
            @ApiResponse(code = 113, message = "用户不存在")
    })
    @PostMapping("/api/uaa/register/retrieve-password")
    public RestResult retrievePassword(@RequestBody Map<String, String> request) {
        String password = request.get("password");
        if (password == null) {
            throw new BusinessException(GlobalErrorCode.BAD_REQUEST);
        }
        String phoneNumber = RegisterAuthUtil.getPhoneNumber();
        String pwd = rsaService.decrypt(password, phoneNumber);
        logger.debug("用户: {}的密码是: {}", phoneNumber, pwd);

        if (!validatePwd(pwd)) {
            logger.warn("密码格式校验的失败 密码是: {}", pwd);
            return RestResultBuilder.builder().failure().build();
        }
        logger.debug("密码格式校验的结果是: {}", pwd);
        UaaUser user = uaaUserService.getUser(phoneNumber);
        if (user == null) {
            logger.error("修改密码的时候无法获取到用户: {}", phoneNumber);
            throw new BusinessException(GlobalErrorCode.RESOURCE_NOT_FOUND);
        } else {
            user.setPassword(PasswordUtils.passwordEncoder(pwd));
            uaaUserService.updateUser(user);
        }
        return RestResultBuilder.builder().success().build();
    }

    private boolean validatePwd(String pwd) {
        /**
         * 由除空格、逗号、单双引号、反斜杠外的6-18位字符组成；字符必须包含字母、数字、符号中至少2种；
         */
        String REGEX_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![`~!@#$%^&*()_+\\-=\\[\\];,./{}|:<>?]+$)[0-9A-Za-z`~!@#$%^&*()_+\\-=\\[\\];,./{}|:<>?]{6,18}$";
        return pwd.matches(REGEX_PASSWORD);
    }


}
