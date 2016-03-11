package com.ai.opt.sdk.model;

public class McsConnectInfo {
    private int mcsMaxtotal;

    private int mcsMaxIdle;

    private int mcsMinIdle;

    private boolean mcsTestOnBorrow;

    private String mcsHost;

    private String mcsPassword;

    public String getMcsHost() {
        return mcsHost;
    }

    public void setMcsHost(String mcsHost) {
        this.mcsHost = mcsHost;
    }

    public String getMcsPassword() {
        return mcsPassword;
    }

    public void setMcsPassword(String mcsPassword) {
        this.mcsPassword = mcsPassword;
    }

    public int getMcsMaxtotal() {
        return mcsMaxtotal;
    }

    public void setMcsMaxtotal(int mcsMaxtotal) {
        this.mcsMaxtotal = mcsMaxtotal;
    }

    public int getMcsMaxIdle() {
        return mcsMaxIdle;
    }

    public void setMcsMaxIdle(int mcsMaxIdle) {
        this.mcsMaxIdle = mcsMaxIdle;
    }

    public int getMcsMinIdle() {
        return mcsMinIdle;
    }

    public void setMcsMinIdle(int mcsMinIdle) {
        this.mcsMinIdle = mcsMinIdle;
    }

    public boolean isMcsTestOnBorrow() {
        return mcsTestOnBorrow;
    }

    public void setMcsTestOnBorrow(boolean mcsTestOnBorrow) {
        this.mcsTestOnBorrow = mcsTestOnBorrow;
    }
}
