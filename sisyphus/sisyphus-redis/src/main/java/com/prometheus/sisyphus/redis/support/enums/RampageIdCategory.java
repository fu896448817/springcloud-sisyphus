package com.prometheus.sisyphus.redis.support.enums;

/**
 * Created by zhuhuaiqi on 2017/3/23.
 */
public enum RampageIdCategory {
    RAMPAGE_ADMIN_ID("sisyphus:admin:id:_id:{id}"), RAMPAGE_MSGCENTER_ID("sisyphus:msgcenter:id:_id:{id}");
    private String value;

    RampageIdCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
