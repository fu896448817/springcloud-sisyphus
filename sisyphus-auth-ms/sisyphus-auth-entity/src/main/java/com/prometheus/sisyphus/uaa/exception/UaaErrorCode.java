package com.prometheus.sisyphus.uaa.exception;


import com.prometheus.sisyphus.common.exception.ErrorCode;

/**
 * created by zhuhuaiqi
 *
 * @author
 */
public enum UaaErrorCode implements ErrorCode {

    DATA_NOT_EXISTS(3001, "实体不存在！"),
    USER_NOT_EXISTS(3002, "用户不存在！"),
    PUBLIC_KEY_NOT_EXISTS(3003, "公钥不存在！"),
    DECRYPT_ERROR(3004, "解密错误！"),
    SEND_MSG_FAILED(3005, "发送短信失败！"),
    VALID_CODE_ERROR(3006, "校验验证码失败！"),
    BIZ_MSG_LIMIT(3007, "业务限流，您的操作过于频繁！");

    private final int code;

    private final String message;

    UaaErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
