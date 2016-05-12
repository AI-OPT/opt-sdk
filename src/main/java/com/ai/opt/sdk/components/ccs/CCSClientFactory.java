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
    	IConfigClient client = null;
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), authInfo.getCcsPassword(), authInfo.getCcsServiceId());
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
