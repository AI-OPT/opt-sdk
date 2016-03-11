package com.ai.opt.sdk.util;

import java.util.HashMap;
import java.util.Map;

public final class ThreadLocalUtils {
    public static final ThreadLocal<Map<String, String>> threadLocals = new ThreadLocal<Map<String, String>>();

    private ThreadLocalUtils() {

    }

    public static void set(String key, String value) {
        Map<String, String> values = threadLocals.get();
        if (values == null) {
            values = new HashMap<String, String>();
            threadLocals.set(values);
        }
        values.put(key, value);
    }

    public static String get(String key) {
        Map<String, String> values = threadLocals.get();
        if (values == null){
            return null;
        }
        return values.get(key);
    }
}
