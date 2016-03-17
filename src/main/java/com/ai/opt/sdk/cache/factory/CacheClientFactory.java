package com.ai.opt.sdk.cache.factory;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.helper.OptConfHelper;
import com.ai.paas.ipaas.mcs.CacheFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;

public final class CacheClientFactory {

    private CacheClientFactory() {

    }

    private static Map<String, ICacheClient> baseMap = new ConcurrentHashMap<String, ICacheClient>();

    public static ICacheClient getCacheClient(String namespace) {

        if (!baseMap.containsKey(namespace)) {
        	Properties mcsProperties = OptConfHelper.getInstance().assembleMcsProperties(namespace);
            ICacheClient client = CacheFactory.getClient(mcsProperties);
            baseMap.put(namespace, client);
        }

        return baseMap.get(namespace);
    }

}
