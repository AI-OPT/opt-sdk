package com.ai.opt.sdk.test.paas.httpclient;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ai.opt.sdk.dubbo.util.HttpClientUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
        
        /*param为构造请求参数报文体（JSON格式字符串），示例如下：
	        {
			    "orderId": "78436478",
			    "tenantId": "SLP"
			}
        */
        String param=JSON.toJSONString(params);
        
        /*result为获取请求结果报文体（JSON格式字符串），请求结果格式示例如下：
	        {
	            "data": "{"responseHeader":{"isSuccess":true,"resultCode":"000000","resultMessage":"成功","info":"","success":true},"ordOrderVo":{"tenantId":null,"tenantPwd":null,"orderId":78436478,"busiCode":"1","orderType":"100000","state":"91","stateName":"关闭","payStyle":null,"payStyleName":"","payTime":1466062037000,"orderTime":1466062037000,"phoneCount":0,"totalFee":93000,"discountFee":0,"operDiscountFee":0,"adjustFee":93000,"paidFee":0,"payFee":93000,"payDataList":null,"productList":[{"tenantId":null,"tenantPwd":null,"orderId":78436478,"skuId":"3","prodName":"测试销售属性商品 20160603无限额修改修改修改测试销售属性商品 20160603无限额修改修改修改","buySum":1,"salePrice":93000,"totalFee":93000,"discountFee":0,"operDiscountFee":0,"adjustFee":93000,"basicOrgId":"","basicOrgName":"","provinceCode":"","provinceName":"","chargeFee":"","productImage":{"vfsId":null,"picType":null},"imageUrl":null,"prodExtendInfo":""}]}}",
	            "resultCode": "000000",
	            "resultMessage": "请求成功"
	        }
	               其中，resultCode="000000"表示请求成功，否则请求失败；resultMessage为请求的提示信息
	             data为请求的真实数据报文体
         */
        
        String result=HttpClientUtil.sendPost(url, param);
        //将返回结果，转换为JSON对象 
        JSONObject json=JSON.parseObject(result);
        String reqResultCode=json.getString("resultCode");
        if("000000".equals(reqResultCode)){
        	//请求过程成功
        	
        	/*获取真实的数据报文体data,示例如下：
        	 * "{"responseHeader":{"isSuccess":true,"resultCode":"000000","resultMessage":"成功","info":"","success":true},"ordOrderVo":{"tenantId":null,"tenantPwd":null,"orderId":78436478,"busiCode":"1","orderType":"100000","state":"91","stateName":"关闭","payStyle":null,"payStyleName":"","payTime":1466062037000,"orderTime":1466062037000,"phoneCount":0,"totalFee":93000,"discountFee":0,"operDiscountFee":0,"adjustFee":93000,"paidFee":0,"payFee":93000,"payDataList":null,"productList":[{"tenantId":null,"tenantPwd":null,"orderId":78436478,"skuId":"3","prodName":"测试销售属性商品 20160603无限额修改修改修改测试销售属性商品 20160603无限额修改修改修改","buySum":1,"salePrice":93000,"totalFee":93000,"discountFee":0,"operDiscountFee":0,"adjustFee":93000,"basicOrgId":"","basicOrgName":"","provinceCode":"","provinceName":"","chargeFee":"","productImage":{"vfsId":null,"picType":null},"imageUrl":null,"prodExtendInfo":""}]}}"
        	*/
            String dataStr=(String)json.get("data");
            /*将真实的报文体转换为JSON对象:
             * {"responseHeader":{"isSuccess":true,"resultCode":"000000","resultMessage":"成功","info":"","success":true},"ordOrderVo":{"tenantId":null,"tenantPwd":null,"orderId":78436478,"busiCode":"1","orderType":"100000","state":"91","stateName":"关闭","payStyle":null,"payStyleName":"","payTime":1466062037000,"orderTime":1466062037000,"phoneCount":0,"totalFee":93000,"discountFee":0,"operDiscountFee":0,"adjustFee":93000,"paidFee":0,"payFee":93000,"payDataList":null,"productList":[{"tenantId":null,"tenantPwd":null,"orderId":78436478,"skuId":"3","prodName":"测试销售属性商品 20160603无限额修改修改修改测试销售属性商品 20160603无限额修改修改修改","buySum":1,"salePrice":93000,"totalFee":93000,"discountFee":0,"operDiscountFee":0,"adjustFee":93000,"basicOrgId":"","basicOrgName":"","provinceCode":"","provinceName":"","chargeFee":"","productImage":{"vfsId":null,"picType":null},"imageUrl":null,"prodExtendInfo":""}]}}
             *
            */
            JSONObject dataJson=JSON.parseObject(dataStr);
            JSONObject responseHeader=dataJson.getJSONObject("responseHeader");
            JSONObject ordOrderVo=dataJson.getJSONObject("ordOrderVo");
            System.out.println("isSuccess="+responseHeader.getBoolean("isSuccess"));
            System.out.println("orderId="+ordOrderVo.getString("orderId"));
            
            
            System.out.println("请求参数报文体[param]="+JSON.toJSONString(params));
            System.out.println("请求结果报文头[result]="+result);
            System.out.println("请求结果真实数据报文体[dataJson]="+JSON.toJSONString(dataJson));
        }
        else{
        	//请求过程失败
        	System.out.println("请求失败,请求错误码:"+reqResultCode);
        }
        
    }
	
}
