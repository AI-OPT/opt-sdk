package com.ai.opt.sdk.cache.client;

import java.util.Map;

public interface ICacheClient {

    String get(String key);

    String hget(String key, String field);

    void hset(String key, String field, String value);

    byte[] hget(byte[] key);

    Long del(byte[] key);

    Long del(String key);

    boolean exists(String key);

    void expire(byte[] key, int seconds);

    void expire(String key, int seconds);

    void set(byte[] key, byte[] value);

    void set(String key, String value);

    void setex(byte[] key, int seconds, byte[] value);

    Map<String, String> hgetAll(String key);

    boolean hexists(String key, String field);

    Long hdel(String key, String[] fields);

}
