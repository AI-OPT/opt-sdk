package com.ai.opt.sdk.model;


import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.util.StringUtil;

public class PaasAuthInfo {

    private String ccsZkAddress;

    private String ccsUserName;

    // 认证地址
    private String authUrl;

    // 分配给平台的配置中心服务密码
    private String ccsPassword;

    // 分配给PaaS层的用户
    private String userName;

    // 分配给平台的配置中心地址
    private String ccsServiceId;

    private String mcsMaxtotal;

    private String mcsMaxIdle;

    private String mcsMinIdle;

    private String mcsTestOnBorrow;

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        if (StringUtil.isBlank(authUrl)) {
            throw new OptSDKException(
                    "认证地址为空，请确认是否在runner-paas-conf.properties中是否配置[paas.auth.url]");
        }
        this.authUrl = authUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (StringUtil.isBlank(userName)) {
            throw new OptSDKException("config userName is null");
        }
        this.userName = userName;
    }

    public String getCcsPassword() {
        return ccsPassword;
    }

    public void setCcsPassword(String ccsPassword) {
        if (StringUtil.isBlank(ccsPassword)) {
            throw new OptSDKException("config service passpord is null");
        }
        this.ccsPassword = ccsPassword;
    }

    public String getCcsServiceId() {
        return ccsServiceId;
    }

    public void setCcsServiceId(String ccsServiceId) {
        if (StringUtil.isBlank(ccsServiceId)) {
            throw new OptSDKException("config service Id is null");
        }
        this.ccsServiceId = ccsServiceId;
    }

    public String getCcsZkAddress() {
        return ccsZkAddress;
    }

    public void setCcsZkAddress(String ccsZkAddress) {
        this.ccsZkAddress = ccsZkAddress;
    }

    public String getCcsUserName() {
        return ccsUserName;
    }

    public void setCcsUserName(String ccsUserName) {
        this.ccsUserName = ccsUserName;
    }

    public String getMcsMaxtotal() {
        return mcsMaxtotal;
    }

    public void setMcsMaxtotal(String mcsMaxtotal) {
        this.mcsMaxtotal = mcsMaxtotal;
    }

    public String getMcsMaxIdle() {
        return mcsMaxIdle;
    }

    public void setMcsMaxIdle(String mcsMaxIdle) {
        this.mcsMaxIdle = mcsMaxIdle;
    }

    public String getMcsMinIdle() {
        return mcsMinIdle;
    }

    public void setMcsMinIdle(String mcsMinIdle) {
        this.mcsMinIdle = mcsMinIdle;
    }

    public String getMcsTestOnBorrow() {
        return mcsTestOnBorrow;
    }

    public void setMcsTestOnBorrow(String mcsTestOnBorrow) {
        this.mcsTestOnBorrow = mcsTestOnBorrow;
    }

}
