package com.ai.opt.sdk.util;

import java.util.UUID;

public final class UUIDUtil {
    private UUIDUtil() {
    }

    public static String genId32() {
        return UUID.randomUUID().toString().replaceAll("\\-", "").toUpperCase();
    }

    public static int genShortId() {
        return genId32().hashCode();
    }

}
