package com.prometheus.sisyphus.common.util;


import com.prometheus.sisyphus.common.exception.GlobalErrorCode;

/**
 * Created by wujun on 2017/04/27.
 *
 * @author wujun
 * @since 2017/04/27
 */
public class IdUtils {

    public static String getId(Long tenantId, String bizId) {
        AssertUtils.notNull(tenantId, GlobalErrorCode.TENANTID_ID_NOT_EMPTY);
        return String.format("%s:%s", tenantId, bizId);
    }
}
