package com.ai.opt.sdk.test.paas.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;

public class HttpClientTest {

	
	@Test
	public void testSendPostHello(){
		String url="http://10.1.245.9:10887/slp-order/demo/hello";
		String json=HttpClientUtil.sendPost(url, "worlddemo");
		System.out.println("testSendPostClient="+json);
	}
	@Test
	public void testSendPostHelloParam(){
		String url="http://10.1.245.9:10887/slp-order/demo/helloParam";
		Map<String,String> parameters=new HashMap<String,String>();
		parameters.put("name", "worldparam");
		String data=JSON.toJSONString(parameters);
		String json=HttpClientUtil.sendPost(url, data);
		System.out.println("testSendPostClient="+json);
	}
	 @Test
    public void orderListTestByRest() {
	   Map<String,String> params=new HashMap<String,String>();
	   params.put("orderId", "78436478");
	   params.put("tenantId", "SLP");
        String url="http://10.1.245.9:10887/slp-order/orderlist/queryOrder";
        String param=JSON.toJSONString(params);
        String result=HttpClientUtil.sendPost(url, param);
        System.out.println("param="+JSON.toJSONString(params));
        System.out.println("result="+result);
    }
	
}
