package com.ai.opt.sdk.test.paas.cache;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ai.opt.sdk.cache.factory.CacheClientFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;

public class mcsTest {
    private ICacheClient cacheClient;

    private String namespace = "com.ai.opt.test.mcs";

   /* @Before
    public void initData() {
        this.cacheClient = CacheClientFactory.getCacheClient(
                namespace);
    }*/

    @Test
    public void addCache() {
    	this.cacheClient = CacheClientFactory.getCacheClient(
                namespace);
        cacheClient.set("testKey", "testValue");
        assertEquals("testValue", cacheClient.get("testKey"));
    }
}
