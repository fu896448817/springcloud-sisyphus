package com.prometheus.sisyphus.basic.exception;

import com.prometheus.sisyphus.common.exception.ErrorCode;

/**
 * created by sunliangliang
 */
public enum  BasicErrorCode implements ErrorCode {
    FILE_EMPTY(2100, "UPLOAD FILE IS EMPTY!"),
    INVALID_IMAGE_FORMAT(2101, "IMAGE FORMAT IS INVALID!");

    private final int code;

    private final String message;

    BasicErrorCode(int code, String message) {
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
