package com.ai.opt.sdk.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 本地线程工具类
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class ThreadLocalUtils {
    public static final ThreadLocal<Map<String, String>> threadLocals = new ThreadLocal<Map<String, String>>();

    private ThreadLocalUtils() {

    }

    /**
     * 设置
     * @param key
     * @param value
     * @author
     */
    public static void set(String key, String value) {
        Map<String, String> values = threadLocals.get();
        if (values == null) {
            values = new HashMap<String, String>();
            threadLocals.set(values);
        }
        values.put(key, value);
    }

    /**
     * 获取
     * @param key
     * @return
     * @author
     */
    public static String get(String key) {
        Map<String, String> values = threadLocals.get();
        if (values == null){
            return null;
        }
        return values.get(key);
    }
}
