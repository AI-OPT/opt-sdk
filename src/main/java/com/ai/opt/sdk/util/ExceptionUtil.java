package com.ai.opt.sdk.util;

import java.io.StringWriter;

public final class ExceptionUtil {

    private ExceptionUtil() {

    }

    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        return stringWriter.getBuffer().toString();
    }

}
