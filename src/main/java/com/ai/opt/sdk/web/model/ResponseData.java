package com.ai.opt.sdk.web.model;

import java.io.Serializable;

import com.ai.opt.base.vo.BaseResponse;

public class ResponseData<T> extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String AJAX_STATUS_SUCCESS = "1";

    public static final String AJAX_STATUS_FAILURE = "0";

    private String statusCode;

    private String statusInfo;

    private T data;

    public ResponseData(String statusCode, String statusInfo) {
        this.statusCode = statusCode;
        this.statusInfo = statusInfo;
    }

    public ResponseData(String statusCode, String statusInfo, T data) {
        this.statusCode = statusCode;
        this.statusInfo = statusInfo;
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
