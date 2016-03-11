package com.ai.opt.sdk.test.paas;

import com.ai.opt.sdk.model.McsConnectInfo;
import com.alibaba.fastjson.JSON;

public class Test {
    public static void main(String[] args) {
        McsConnectInfo connectInfo = new McsConnectInfo();
        connectInfo.setMcsHost("127.0.0.1:6379");
        connectInfo.setMcsMaxIdle(10);
        connectInfo.setMcsMaxtotal(20);
        connectInfo.setMcsMinIdle(5);
        connectInfo.setMcsPassword("123456");
        connectInfo.setMcsTestOnBorrow(true);
        System.out.println(JSON.toJSONString(connectInfo));
    }
}
