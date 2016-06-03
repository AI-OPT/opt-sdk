package com.ai.opt.sdk.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    public static String sendPostRequest(String url, String data, Map<String, String> header) throws IOException,
            URISyntaxException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(new URL(url).toURI());
        for (Map.Entry<String, String> entry : header.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        StringEntity dataEntity = new StringEntity(data, ContentType.APPLICATION_JSON);
        httpPost.setEntity(dataEntity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        entity.getContent()));
                StringBuffer buffer = new StringBuffer();
                String tempStr;
                while ((tempStr = reader.readLine()) != null)
                    buffer.append(tempStr);
                return buffer.toString();
            } else {
                throw new RuntimeException("error code " + response.getStatusLine().getStatusCode()
                        + ":" + response.getStatusLine().getReasonPhrase());
            }
        } finally {
            response.close();
            httpclient.close();
        }
    }

    public static String send(String address, String param) {
        logger.info("restful address : " + address);
        logger.info("param : " + param);
        String result = "";
        try {
            result = HttpClientUtil.sendPostRequest(address, param, null);
            logger.info("result : " + result);
        } catch (IOException e) {
            String errorMessage = e.getMessage();
            logger.error(errorMessage, e);
        } catch (URISyntaxException e) {
            String errorMessage = e.getMessage();
            logger.error(errorMessage, e);
        }
        // 请求发生异常后，result 为 空
        return result;
    }

    /**
     * 发送GET请求
     * 
     * @param url
     *            目的地址
     * @param parameters
     *            请求参数，Map类型。
     * @return 远程响应结果
     */
    public static String sendGet(String url, Map<String, String> parameters) {
        StringBuffer buffer = new StringBuffer();// 返回的结果
        BufferedReader in = null;// 读取响应输入流
        StringBuffer sb = new StringBuffer();// 存储参数
        String params = "";// 编码之后的参数
        try {
            // 编码请求参数
            if (parameters.size() == 1) {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=")
                            .append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"));
                }
                params = sb.toString();
            } else {
                for (String name : parameters.keySet()) {
                    sb.append(name).append("=")
                            .append(java.net.URLEncoder.encode(parameters.get(name), "UTF-8"))
                            .append("&");
                }
                String temp_params = sb.toString();
                params = temp_params.substring(0, temp_params.length() - 1);
            }
            String full_url = url + "?" + params;
            logger.info("restful address : " + full_url);
            // 创建URL对象
            URL connURL = new URL(full_url);
            // 打开URL连接
            java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
                    .openConnection();
            // 建立实际的连接
            httpConn.connect();
            // 定义BufferedReader输入流来读取URL的响应,并设置编码方式
            in = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), "UTF-8"));
            String line;
            // 读取返回的内容
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws IOException, URISyntaxException {

//        String result = HttpClientUtil.sendPostRequest(
//                "http://10.1.228.222:15101/serviceAgent/rest/ipaas/dubbo-testA/dubbo-test/testServiceMethod", "{\"count\":1,\"SrcSysCode\":\"1005\"}");
//        System.out.println("++++++++++++  " + result);

        Map<String, String> headerValue = new HashMap<String, String>();
        headerValue.put("appkey","03379980ba661ad9ba678d386e39c1ca");
        headerValue.put("sign","12345");

        String result = HttpClientUtil.sendPostRequest(
                "http://10.1.235.246:8081/serviceAgent/http/BIS-3A-USERADD", "{\"loginname\":\"xj109\",\"orgcode\":\"4001\",\"password\":\"abcdEFG123\",\"status\":\"active\"}", headerValue);
        System.out.println("++++++++++++  " + result);

//        Map<String, String> headerValue = new HashMap<String, String>();
//        headerValue.put("appkey","893f09f81402f23bf5b2bd5596d668b0");
//        headerValue.put("sign","12346");
//
//        String result = HttpClientUtil.sendPostRequest(
//                "http://10.1.235.246:8081/serviceAgent/http/RUNNER-QUERYCUSTIDBYPHONENUM-001",
//                "{\"tenantId\":\"HX\",\"custPhoneNum\":\"15930008252\"}", headerValue);
//        System.out.println("++++++++++++  " + result);


    }
}
