package com.ai.opt.sdk.components.mo;


import com.ai.opt.sdk.exception.SDKException;
import com.alibaba.dubbo.common.utils.StringUtils;

public class PaasConf {
    // 认证地址
    private String authUrl;

    // 分配给PaaS层的用户
    private String pid;
    
    // 分配给平台的配置中心服务密码
    private String ccsPassword;

    // 分配给平台的配置中心地址
    private String ccsServiceId;

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        if (StringUtils.isBlank(authUrl)) {
            throw new SDKException("认证地址为空，请确认是否在paas-conf.properties中是否配置[paas.auth.url]");
        }
        this.authUrl = authUrl;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        if (StringUtils.isBlank(pid)) {
            throw new SDKException("config pid is null");
        }
        this.pid = pid;
    }

    public String getCcsPassword() {
        return ccsPassword;
    }

    public void setCcsPassword(String ccsPassword) {
        if (StringUtils.isBlank(ccsPassword)) {
            throw new SDKException("config service passpord is null");
        }
        this.ccsPassword = ccsPassword;
    }

    public String getCcsServiceId() {
        return ccsServiceId;
    }

    public void setCcsServiceId(String ccsServiceId) {
        if (StringUtils.isBlank(ccsServiceId)) {
            throw new SDKException("config service Id is null");
        }
        this.ccsServiceId = ccsServiceId;
    }


}
