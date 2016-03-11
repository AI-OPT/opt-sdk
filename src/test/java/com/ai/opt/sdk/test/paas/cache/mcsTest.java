package com.ai.opt.sdk.test.paas.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ai.opt.sdk.cache.client.ICacheClient;
import com.ai.opt.sdk.cache.factory.CacheClientBuilderFactory;

public class mcsTest {
    private ICacheClient cacheClient;

    private String namespace = "com.ai.opt.test.mcs";

    @Before
    public void initData() {
        this.cacheClient = CacheClientBuilderFactory.getCacheClientBuilder().getCacheClient(
                namespace);
    }

    @Test
    public void addCache() {
        cacheClient.set("testKey", "testValue");
        assertEquals("testValue", cacheClient.get("testKey"));
    }
}
