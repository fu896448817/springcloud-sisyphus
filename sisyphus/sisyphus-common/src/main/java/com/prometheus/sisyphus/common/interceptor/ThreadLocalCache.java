package com.prometheus.sisyphus.common.interceptor;

import java.util.HashMap;

/**
 * ThreadLocalCache
 */
public class ThreadLocalCache {

    public static ThreadLocal<HashMap<String, Long>> baseSignatureRequestThreadLocal = new ThreadLocal<HashMap<String, Long>>();
}
