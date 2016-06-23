package com.ai.opt.sdk.dts.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.base.exception.SystemException;
import com.ai.opt.sdk.util.StringUtil;

public final class ThreadPoolFactory {

    private ThreadPoolFactory() {
    }

    private static final Map<String, ThreadPool> pools = new ConcurrentHashMap<String, ThreadPool>();

    public static ThreadPool createOrGetThreadPool(String poolName) {
        return createOrGetThreadPool(poolName, 5);
    }

    public static ThreadPool createOrGetThreadPool(String poolName, int nThread) {
        if (StringUtil.isBlank(poolName)){
            throw new SystemException("pool name cannot be null");
        }
        ThreadPool pool = pools.get(poolName);
        if (pool == null){
            pool = ThreadPool.newInstance(poolName, nThread);
        }
        pools.put(poolName, pool);
        return pools.get(poolName);
    }
}
