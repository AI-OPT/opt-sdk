package com.ai.opt.sdk.components.ccs;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.ccs.client.SdkModeConfigClient;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.ccs.ConfigFactory;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.alibaba.fastjson.JSON;

/**
 * 获取指定服务ID的配置中心技术组件服务实例<br>
 * Date: 2016年5月6日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * 
 * @author gucl
 */
public final class CCSClientFactory {

    private static final Logger LOG = LoggerFactory.getLogger(CCSClientFactory.class);
    
    private static Map<String, IConfigClient> baseMap_serviceMode = new ConcurrentHashMap<String, IConfigClient>();
    private static Map<String, IConfigClient> baseMap_sdkMode = new ConcurrentHashMap<String, IConfigClient>();

    private CCSClientFactory() {

    }

    /**
     * 获取配置中心客户端
     * @return 配置中心客户端
     * @author gucl
     */
    public static IConfigClient getDefaultConfigClient() {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
    	if(StringUtil.isBlank(authInfo.getPaasSdkMode())||SDKConstants.PAASMODE.PAAS_SERVICE_MODE.equals(authInfo.getPaasSdkMode())){
    		return getDefaultConfigClientByServiceMode();
    	}
    	else{
    		return getDefaultConfigClientBySDkMode();
    	}
    }
    /**
     * 从sdk模式获取默认配置信息
     * @return
     * @author
     */
    private static IConfigClient getDefaultConfigClientBySDkMode() {
		LOG.info("getDefaultConfigClientBySDkMode开始");
    	IConfigClient client = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String appname = authInfo.getCcsAppName();
		String zkAddr = authInfo.getCcsZkAddress();
        LOG.info("authInfo="+JSON.toJSONString(authInfo));
        try {
        	if (!baseMap_sdkMode.containsKey(appname)) {
        		client = getConfigClientBySdkMode(appname, zkAddr);
        		baseMap_sdkMode.put(appname, client);
    			LOG.info("从baseMap直接取数据["+appname+"]"+JSON.toJSONString(baseMap_sdkMode));
    		}
        	else{
        		client=baseMap_sdkMode.get(appname);
        		LOG.info("baseMap无数据["+appname+"]"+JSON.toJSONString(baseMap_sdkMode));
        	}
            
        } catch (Exception e) {
            LOG.error("get paas config sdk mode center error", e);
            throw new SDKException(e);
        }
        LOG.info("getDefaultConfigClientBySDkMode结束");
        return client;
	}
    /**
     * 从服务模式获取默认配置Client
     * @return
     * @author
     */
	private static IConfigClient getDefaultConfigClientByServiceMode() {
		LOG.info("getDefaultConfigClient开始");
    	IConfigClient client = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), authInfo.getCcsPassword(), authInfo.getCcsServiceId());
        String keyId=authInfo.getPid()+"."+authInfo.getCcsServiceId();
        LOG.info("authInfo="+JSON.toJSONString(authInfo));
        try {
        	if (!baseMap_serviceMode.containsKey(keyId)) {
        		client = ConfigFactory.getConfigClient(authDescriptor);
    			baseMap_serviceMode.put(keyId, client);
    			LOG.info("从baseMap直接取数据["+keyId+"]"+JSON.toJSONString(baseMap_serviceMode));
    		}
        	else{
        		client=baseMap_serviceMode.get(keyId);
        		LOG.info("baseMap无数据["+keyId+"]"+JSON.toJSONString(baseMap_serviceMode));
        	}
            
        } catch (Exception e) {
            LOG.error("get paas config center error", e);
            throw new SDKException(e);
        }
        LOG.info("getDefaultConfigClient结束");
        return client;
	}

	/**
	 * 以paas-service方式获取指定的配置中心客户端
	 * @param serviceId
	 * @param password
	 */
    public static IConfigClient getConfigClient(String serviceId, String password) {
    	IConfigClient client = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), password, serviceId);
        String keyId=authInfo.getPid()+"."+authInfo.getCcsServiceId();
        try {
        	if (!baseMap_serviceMode.containsKey(keyId)) {
        		client = ConfigFactory.getConfigClient(authDescriptor);
    			baseMap_serviceMode.put(keyId, client);
    		}
        	else{
        		client=baseMap_serviceMode.get(keyId);
        	}
        } catch (Exception e) {
            LOG.error("get paas config center error", e);
            throw new SDKException(e);
        }
        return client;
    }

    /**
     * 以paas-sdk方式获取指定的配置中心客户端
     * @param appname
     * @param zkAddr
     * @return
     * @author gucl
     * @ApiDocMethod
     * @ApiCode
     * @RestRelativeURL
     */
	public static IConfigClient getConfigClientBySdkMode(String appname, String zkAddr) {
		LOG.info("getConfigClientBySdkMode开始");
    	IConfigClient client = null;
        try {
        	if (!baseMap_sdkMode.containsKey(appname)) {
        		client = new SdkModeConfigClient(appname, zkAddr);
        		baseMap_sdkMode.put(appname, client);
    			LOG.info("从baseMap直接取数据["+appname+"]"+JSON.toJSONString(baseMap_sdkMode));
    		}
        	else{
        		client=baseMap_sdkMode.get(appname);
        		LOG.info("baseMap无数据["+appname+"]"+JSON.toJSONString(baseMap_sdkMode));
        	}
            
        } catch (Exception e) {
            LOG.error("get paas config sdk mode center error", e);
            throw new SDKException(e);
        }
        LOG.info("getConfigClientBySdkMode结束");
        return client;
	}
}
