package com.ai.opt.sdk.components.ccs;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.exception.SDKException;
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
    
    private static Map<String, IConfigClient> baseMap = new ConcurrentHashMap<String, IConfigClient>();

    private CCSClientFactory() {

    }

    public static IConfigClient getDefaultConfigClient() {
    	LOG.debug("getDefaultConfigClient开始");
    	IConfigClient client = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), authInfo.getCcsPassword(), authInfo.getCcsServiceId());
        String keyId=authInfo.getPid()+"."+authInfo.getCcsServiceId();
        LOG.error("authInfo="+JSON.toJSONString(authInfo));
        try {
        	if (!baseMap.containsKey(keyId)) {
        		client = ConfigFactory.getConfigClient(authDescriptor);
    			baseMap.put(keyId, client);
    			LOG.debug("从baseMap直接取数据["+keyId+"]"+JSON.toJSONString(baseMap));
    		}
        	else{
        		client=baseMap.get(keyId);
        		LOG.debug("baseMap无数据["+keyId+"]"+JSON.toJSONString(baseMap));
        	}
            
        } catch (Exception e) {
            LOG.error("get paas config center error", e);
            throw new SDKException(e);
        }
        LOG.debug("getDefaultConfigClient结束");
        return client;
    }

    public static IConfigClient getConfigClient(String serviceId, String password) {
    	IConfigClient client = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), password, serviceId);
        String ccsId=authInfo.getCcsServiceId();
        try {
        	if (!baseMap.containsKey(ccsId)) {
        		client = ConfigFactory.getConfigClient(authDescriptor);
    			baseMap.put(ccsId, client);
    		}
        	else{
        		client=baseMap.get(ccsId);
        	}
        } catch (Exception e) {
            LOG.error("get paas config center error", e);
            throw new SDKException(e);
        }
        return client;
    }
}
