package com.prometheus.sisyphus.redis.utils;

import com.prometheus.sisyphus.common.util.Constants;
import com.prometheus.sisyphus.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConverters;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * created by sunliangliang
 */
@Component
public class RedisLock {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JedisCluster jedisCluster;

    private boolean tryLock(byte[] lockKey,int lockSeconds) throws Exception {
        long nowTime = System.currentTimeMillis();
        long expireTime = nowTime + lockSeconds * 1000 + 1000; // 容忍不同服务器时间有1秒内的误差
        boolean result = JedisConverters.toBoolean(jedisCluster.setnx(lockKey, longToBytes(expireTime)));
        if (result) {
            jedisCluster.expire(lockKey, lockSeconds);
            return true;
        } else {
            byte[] oldValue = jedisCluster.get(lockKey);
            if (oldValue != null && bytesToLong(oldValue) < nowTime) {
                // 这个锁已经过期了，可以获得它
                // PS: 如果setNX和expire之间客户端发生崩溃，可能会出现这样的情况
                byte[] oldValue2 = jedisCluster.getSet(lockKey, longToBytes(expireTime));
                if (Arrays.equals(oldValue, oldValue2)) {
                    // 获得了锁
                    jedisCluster.expire(lockKey, lockSeconds);
                    return true;
                } else {
                    // 被别人抢占了锁(此时已经修改了lockKey中的值，不过误差很小可以忽略)
                    return false;
                }
            }
        }
        return false;
    }
    /**
     * 轮询的方式去获得锁，成功返回true，超过轮询次数或异常返回false
     *
     * @param lockSeconds       加锁的时间(秒)，超过这个时间后锁会自动释放
     * @param tryIntervalMillis 轮询的时间间隔(毫秒)
     * @param maxTryCount       最大的轮询次数
     */
    public boolean tryLock(String key,final int lockSeconds, final long tryIntervalMillis, final int maxTryCount) {
        byte[] lockKey = key.getBytes();
        int tryCount = 0;
        while (true) {
            if (++tryCount >= maxTryCount) {
                // 获取锁超时
                return false;
            }
            try {
                if (tryLock(lockKey,lockSeconds)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("tryLock Error", e);
                return false;
            }
            try {
                Thread.sleep(tryIntervalMillis);
            } catch (InterruptedException e) {
                logger.error("tryLock interrupted", e);
                return false;
            }
        }
    }

    public boolean tryLock(String key){
        if (StringUtils.isEmpty(key)){
            return false;
        }
        return   tryLock(key,Constants.REDIS_LOCK_SECONDS,Constants.REDIS_LOCK_INTERVAL_MILLIS, Constants.REDIS_LOCK_MAX_TRYCOUNT);
    }

    /**
     * 如果加锁后的操作比较耗时，调用方其实可以在unlock前根据时间判断下锁是否已经过期
     * 如果已经过期可以不用调用，减少一次请求
     */
    public void unlock(String lockKey) {
        jedisCluster.del(lockKey);
    }
    public byte[] longToBytes(long value) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
        buffer.putLong(value);
        return buffer.array();
    }
    public long bytesToLong(byte[] bytes) {
        if (bytes.length != Long.SIZE / Byte.SIZE) {
            throw new IllegalArgumentException("wrong length of bytes!");
        }
        return ByteBuffer.wrap(bytes).getLong();
    }
}

