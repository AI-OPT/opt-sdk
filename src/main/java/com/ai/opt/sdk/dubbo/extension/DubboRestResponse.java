package com.ai.opt.sdk.dubbo.extension;

import java.io.Serializable;

/**
 * Dubbo Rest返回
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class DubboRestResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String resultCode;

    private String resultMessage;

    private Object data;

    public DubboRestResponse() {
		super();
	}

	public DubboRestResponse(String resultCode, String resultMessage, Object data) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.data = data;
    }

    public DubboRestResponse(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
