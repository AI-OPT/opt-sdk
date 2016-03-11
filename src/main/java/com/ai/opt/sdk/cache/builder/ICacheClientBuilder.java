package com.ai.opt.sdk.cache.builder;

import com.ai.opt.sdk.cache.client.ICacheClient;

public interface ICacheClientBuilder {

    ICacheClient getCacheClient(String namespace);
}
