package com.prometheus.sisyphus.uaa.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OauthToken implements Serializable {
    private Integer code;
    private String message;
    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private Long time_stamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public Long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Long time_stamp) {
        this.time_stamp = time_stamp;
    }
}
