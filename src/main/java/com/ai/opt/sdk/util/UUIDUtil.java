package com.ai.opt.sdk.util;

import java.util.UUID;

/**
 * UUID工具类
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class UUIDUtil {
    private UUIDUtil() {
    }

    /**
     * 获取32位Id
     * @return
     * @author
     */
    public static String genId32() {
        return UUID.randomUUID().toString().replaceAll("\\-", "").toUpperCase();
    }

    /**
     * 获取短Id
     * @return id
     * @author
     */
    public static int genShortId() {
        return genId32().hashCode();
    }

}
