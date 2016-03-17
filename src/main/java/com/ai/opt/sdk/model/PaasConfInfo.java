package com.ai.opt.sdk.model;

import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.util.StringUtil;

public class PaasConfInfo {

    private String ccsZkAddress;

    private String ccsUserName;

    private String ccsPassword;

    private String mcsMaxtotal;

    private String mcsMaxIdle;

    private String mcsMinIdle;

    private String mcsTestOnBorrow;

    public String getCcsPassword() {
        return ccsPassword;
    }

    public void setCcsPassword(String ccsPassword) {
        if (StringUtil.isBlank(ccsPassword)) {
            throw new OptSDKException("config service passpord is null");
        }
        this.ccsPassword = ccsPassword;
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
