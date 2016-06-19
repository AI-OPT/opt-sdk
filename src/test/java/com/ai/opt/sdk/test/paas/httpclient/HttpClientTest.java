package com.ai.opt.sdk.test.paas.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;

public class HttpClientTest {

	
	@Test
	public void testSendPostDemo(){
		String url="http://10.1.245.9:20882/slp-order/demo/hello";
		Map<String,String> parameters=new HashMap<String,String>();
		parameters.put("name", "worlddemo");
		String data=JSON.toJSONString(parameters);
		String json=HttpClientUtil.sendPost(url, "worlddemo");
		System.out.println("testSendPostClient="+json);
	}
	@Test
	public void testSendPostOrder(){
		String url="http://10.1.245.9:20882/slp-order/demo/hello";
		Map<String,String> parameters=new HashMap<String,String>();
		parameters.put("name", "worlddemo");
		String data=JSON.toJSONString(parameters);
		String json=HttpClientUtil.sendPost(url, "worlddemo");
		System.out.println("testSendPostClient="+json);
	}
	
}
