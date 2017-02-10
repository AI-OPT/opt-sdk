package com.ai.opt.sdk.appserver;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ai.opt.sdk.cache.base.AbstractCache;

/**
 * 启动缓存服务类
 * Date: 2017年2月9日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class CacheServiceStart {

    private static final Logger LOG = LoggerFactory.getLogger(CacheServiceStart.class);

    private static final String PATH = "classpath:context/core-context.xml";
    private CacheServiceStart(){}
    /**
     * 主程序
     * @param args
     * @author
     */
    public static void main(String[] args) {
        LOG.error("开始刷新缓存......");
        @SuppressWarnings("resource")
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { PATH });
        context.start();
        Map<String, AbstractCache> caches = context
                .getBeansOfType(AbstractCache.class);
        for (AbstractCache cache : caches.values()) {
            try {
                cache.write();
            } catch (Exception ex) {
                LOG.error("缓存写入失败",ex);
            }

        }
        // 刷新缓存结束，需要提示
        LOG.error("缓存刷新结束,请通过日志查看是否刷新成功.....");
    }
}
