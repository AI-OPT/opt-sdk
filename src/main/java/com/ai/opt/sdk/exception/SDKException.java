package com.ai.opt.sdk.exception;

import java.io.Serializable;

/**
 * SDK异常
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class SDKException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public SDKException(String exception) {
        super(exception);
    }

    public SDKException(Exception exception) {
        super(exception);
    }

    public SDKException(String message, Exception exception) {
        super(message, exception);
    }
}
