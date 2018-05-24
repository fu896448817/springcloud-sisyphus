package com.prometheus.sisyphus.uaa.utils;


import com.prometheus.sisyphus.uaa.entity.RegisterInfo;

/**
 * Created by wushaoyong on 2017/12/3.
 */
public class RegisterAuthUtil {
    private static ThreadLocal<RegisterInfo> registerInfoThreadLocal = new ThreadLocal<>();

    public static void setRegisterThreadInfo(RegisterInfo info) {
        registerInfoThreadLocal.set(info);
    }

    public static String getPhoneNumber() {
        return registerInfoThreadLocal.get().getPhoneNumber();
    }

}
