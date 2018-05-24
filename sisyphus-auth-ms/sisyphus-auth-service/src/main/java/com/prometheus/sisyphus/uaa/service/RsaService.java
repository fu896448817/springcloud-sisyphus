package com.prometheus.sisyphus.uaa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

import java.security.Key;
import java.util.Map;

/**
 * Created by wushaoyong on 2017/12/3.
 */
public interface RsaService {

    String publicKey(String phoneNumber);

    String decrypt(String text, String phoneNumber);
}
