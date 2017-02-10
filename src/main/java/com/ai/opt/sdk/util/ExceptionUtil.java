package com.ai.opt.sdk.util;

import java.io.StringWriter;

/**
 * 异常工具类
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class ExceptionUtil {

    private ExceptionUtil() {

    }

    /**
     * 获取堆栈信息
     * @param t
     * @return
     * @author
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        return stringWriter.getBuffer().toString();
    }

}
