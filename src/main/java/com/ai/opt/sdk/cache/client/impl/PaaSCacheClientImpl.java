package com.ai.opt.sdk.cache.client.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.opt.sdk.cache.client.ICacheClient;

public class PaaSCacheClientImpl implements ICacheClient {

    private static final Logger LOG = LogManager.getLogger(PaaSCacheClientImpl.class);

    private com.ai.paas.ipaas.mcs.interfaces.ICacheClient client;

    public PaaSCacheClientImpl(com.ai.paas.ipaas.mcs.interfaces.ICacheClient client) {
        this.client = client;

    }

    @Override
    public String hget(String key, String field) {
        long startTime = System.currentTimeMillis();
        String value = client.hget(key, field);
        if (LOG.isDebugEnabled()) {
            LOG.debug("获取MCS值花费时间:{}", (System.currentTimeMillis() - startTime));
        }
        return value;
    }

    @Override
    public void hset(String key, String field, String value) {
        client.hset(key, field, value);
    }

    @Override
    public byte[] hget(byte[] key) {
        return client.get(key);
    }

    @Override
    public Long del(byte[] key) {
        return client.del(key);
    }

    @Override
    public void expire(byte[] key, int seconds) {
        client.expire(key, seconds);
    }

    @Override
    public void set(byte[] key, byte[] value) {
        client.set(key, value);
    }

    @Override
    public void setex(byte[] key, int seconds, byte[] value) {
        client.setex(key, seconds, value);
    }

    @Override
    public Long del(String key) {
        return client.del(key);
    }

    @Override
    public boolean exists(String key) {
        return client.exists(key);
    }

    @Override
    public Map<String, String> hgetAll(String key) {
        return client.hgetAll(key);
    }

    @Override
    public boolean hexists(String key, String field) {
        return client.hexists(key, field);
    }

    @Override
    public void expire(String key, int seconds) {
        client.expire(key, seconds);
    }

    @Override
    public void set(String key, String value) {
        client.set(key, value);
    }

    @Override
    public String get(String key) {
        return client.get(key);
    }

    @Override
    public Long hdel(String key, String[] fields) {
        return client.hdel(key, fields);
    }

}
