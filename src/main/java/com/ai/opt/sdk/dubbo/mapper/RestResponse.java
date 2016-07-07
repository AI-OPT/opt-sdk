package com.ai.opt.sdk.dubbo.mapper;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RestResponse implements Serializable {

	private static final long serialVersionUID = -2685764073290390958L;
	private String resultCode;
	private String resultMsg;
	private Object info;

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public RestResponse(String resultCode, String resultMsg, Object info) {
		setResultCode(resultCode);
		setResultMsg(resultMsg);
		this.info = info;
	}

	public RestResponse(String resultCode, String resultMsg) {
		setResultCode(resultCode);
		setResultMsg(resultMsg);
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

}
