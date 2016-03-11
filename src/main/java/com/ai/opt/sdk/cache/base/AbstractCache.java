package com.ai.opt.sdk.cache.base;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractCache {

    protected Log getLogger() {
        return LogFactory.getLog(this.getClass());
    }

    /**
     * 写入缓存到配置中心
     * 
     * @throws Exception
     * @author zhangchao
     */
    public abstract void write() throws Exception;

}
