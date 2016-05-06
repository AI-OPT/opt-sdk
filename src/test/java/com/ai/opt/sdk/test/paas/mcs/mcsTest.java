package com.ai.opt.sdk.test.paas.mcs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.components.mcs.MCSFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;

public class mcsTest {
    private ICacheClient cacheClient;

    private String namespace = "com.ai.opt.test.mcs";

    @Before
    public void initData() {
        this.cacheClient = MCSFactory.getCacheClient(
                namespace);
    }
    //@Ignore
    @Test
    public void addCache() {
        cacheClient.set("testKey", "testValue");
        assertEquals("testValue", cacheClient.get("testKey"));
    }
}
