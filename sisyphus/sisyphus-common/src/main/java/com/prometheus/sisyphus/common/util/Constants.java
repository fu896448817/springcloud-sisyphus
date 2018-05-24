package com.prometheus.sisyphus.common.util;

import java.util.UUID;

/**
 * Created by sunliangliang on 2017/2/17.
 * 数据字典
 */
public class Constants {
    // 系统过期时间:2099-12-30 00:00:00
    public static final Long PACKAGE_SYSTEM_EXPIRE_TIME = 4102243200000L;

    //内部调用Rpc接口
    public static final int RPC_INVOKE_SUCCESS = 1;//成功
    /**
     * Topic：IM聊天记录、精灵会话记录
     */
    public static final String KAFKA_TOPIC_MSG = "sisyphus_msg";
    /**
     * 会话
     */
    public static final String KAFKA_TOPIC_SESSION = "sisyphus_session";
    /**
     * 聊天记录
     */
    public static final String KAFKA_TOPIC_IM_MSG = "_msg";


    //redis的各类key模板
    //过期时间几个档位，单位：秒
    public static final int REDIS_EXPIRE_TIME_ST = 60 * 1;
    public static final int REDIS_EXPIRE_TIME_T = 60 * 5;
    public static final int REDIS_EXPIRE_TIME_S = 60 * 30;
    public static final int REDIS_EXPIRE_TIME_M = 60 * 60 * 6;
    public static final int REDIS_EXPIRE_TIME_L = 60 * 60 * 24;

    public static final int REDIS_LOCK_SECONDS = 10 * 60;//10分钟，分布式锁锁定时间

    public static final Long REDIS_LOCK_INTERVAL_MILLIS = 1000L;//轮询间隔时间1s

    public static final int REDIS_LOCK_MAX_TRYCOUNT = 5;//5，最大轮询次数

    public static final String LOCK_KEY = UUID.randomUUID().toString();
    public static final String REDIS_LOCK_KEY =
            "sisyphus:session:_lockKey:{lockKey}";

    public static final String REDIS_MSG_PRIVATE_KEY = "sisyphus:msg:private:key:%s";


    /**
     * 短信验证码缓存前缀
     */
    public static final String REDIS_MSG_CAPTCHA =
            "rampage:msg:captcha:%s";

}
