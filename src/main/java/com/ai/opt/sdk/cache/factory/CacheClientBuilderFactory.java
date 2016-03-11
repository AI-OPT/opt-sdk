package com.ai.opt.sdk.cache.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.cache.builder.ICacheClientBuilder;
import com.ai.opt.sdk.cache.builder.impl.PaaSCacheClientBuilder;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.helper.OptConfHelper;

public final class CacheClientBuilderFactory {

    private CacheClientBuilderFactory() {

    }

    // IPAAS的MCS提供缓存
    private static final String IPAAS_MCS_TYPE = "ipaas_mcs";

    private static Map<String, ICacheClientBuilder> baseMap = new ConcurrentHashMap<String, ICacheClientBuilder>();

    public static ICacheClientBuilder getCacheClientBuilder() {
        String cacheType = OptConfHelper.getInstance().getCacheType();

        if (!baseMap.containsKey(cacheType)) {
            ICacheClientBuilder builder = getInstance(cacheType);
            baseMap.put(cacheType, builder);
        }

        return baseMap.get(cacheType);
    }

    private static ICacheClientBuilder getInstance(String cacheType) {
        ICacheClientBuilder builder = null;
        if (IPAAS_MCS_TYPE.equals(cacheType)) {
            builder = new PaaSCacheClientBuilder();
        } else {
            throw new OptSDKException("not supported cache type[" + cacheType + "]");
        }
        return builder;
    }

}
