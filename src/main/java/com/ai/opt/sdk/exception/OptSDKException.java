package com.ai.opt.sdk.exception;

import java.io.Serializable;

public class OptSDKException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    public OptSDKException(String exception) {
        super(exception);
    }

    public OptSDKException(Exception exception) {
        super(exception);
    }

    public OptSDKException(String message, Exception exception) {
        super(message, exception);
    }
}
