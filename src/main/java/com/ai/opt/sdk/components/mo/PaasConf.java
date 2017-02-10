package com.ai.opt.sdk.components.mo;


import com.ai.opt.sdk.exception.SDKException;
import com.ai.opt.sdk.util.StringUtil;

/**
 * paas服务配置
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class PaasConf {
	
	//========ipaas 服务使用模式 参数  start==========
    /**
     * --ipaas service模式--  认证地址(paasSdkMode=1时有效)
     */
    private String authUrl;

    /**
     * --ipaas service模式--  分配给PaaS层的用户(paasSdkMode=1时有效)
     */
    private String pid;
    
    /**
     * --ipaas service模式--  分配给平台的配置中心服务密码(paasSdkMode=1时有效)
     */
    private String ccsPassword;

    /**
     *  --ipaas service模式--  分配给平台的配置中心地址(paasSdkMode=1时有效)
     */
    private String ccsServiceId;
    //========ipaas 服务使用模式 参数  end==========
    
    
    /**
     * ipaas使用模式 0：以service方式使用   1：以SDK方式使用
     */
  	private String paasSdkMode;
  	/**
  	 * --ipaas service模式--  应用程序名称标识(paasSdkMode=0时有效)
  	 */
    private String ccsAppName;
    /**
     * --ipaas service模式--  zk地址(paasSdkMode=0时有效)
     */
    private String ccsZkAddress;
    //========ipaas SDK使用模式 参数  end==========
    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        /*if (StringUtil.isBlank(authUrl)) {
            throw new SDKException("认证地址为空，请确认是否在paas-conf.properties中是否配置[paas.auth.url]");
        }*/
        this.authUrl = authUrl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
       /* if (StringUtil.isBlank(pid)) {
            throw new SDKException("config pid is null");
        }*/
        this.pid = pid;
    }

    public String getCcsPassword() {
        return ccsPassword;
    }

    public void setCcsPassword(String ccsPassword) {
       /* if (StringUtil.isBlank(ccsPassword)) {
            throw new SDKException("config service passpord is null");
        }*/
        this.ccsPassword = ccsPassword;
    }

    public String getCcsServiceId() {
        return ccsServiceId;
    }

    public void setCcsServiceId(String ccsServiceId) {
        /*if (StringUtil.isBlank(ccsServiceId)) {
            throw new SDKException("config service Id is null");
        }*/
        this.ccsServiceId = ccsServiceId;
    }

	public String getPaasSdkMode() {
		return paasSdkMode;
	}

	public void setPaasSdkMode(String paasSdkMode) {
		this.paasSdkMode = paasSdkMode;
	}

	public String getCcsAppName() {
		return ccsAppName;
	}

	public void setCcsAppName(String ccsAppName) {
		this.ccsAppName = ccsAppName;
	}

	public String getCcsZkAddress() {
		return ccsZkAddress;
	}

	public void setCcsZkAddress(String ccsZkAddress) {
		this.ccsZkAddress = ccsZkAddress;
	}


}
