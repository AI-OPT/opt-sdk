package com.ai.opt.sdk.components.mcs;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.mcs.CacheCmpFactory;
import com.ai.paas.ipaas.mcs.CacheFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;
import com.alibaba.fastjson.JSON;

/**
 * MCS客户端工厂
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class MCSClientFactory {
	private static final Logger LOG = LoggerFactory.getLogger(MCSClientFactory.class);
	private static Map<String, ICacheClient> baseMap_serviceMode = new ConcurrentHashMap<String, ICacheClient>();
	private static Map<String, ICacheClient> baseMap_sdkMode = new ConcurrentHashMap<String, ICacheClient>();

    private MCSClientFactory() {

    }

    /**
     * 获取缓存客户端
     * @param cachens 缓存命名空间名称
     * @return 缓存客户端
     * @author
     */
    public static ICacheClient getCacheClient(String cachens) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
    	if(StringUtil.isBlank(authInfo.getPaasSdkMode())||SDKConstants.PAASMODE.PAAS_SERVICE_MODE.equals(authInfo.getPaasSdkMode())){
    		return getCacheClientByServiceMode(cachens);
    	}
    	else{
    		return getCacheClientBySdkMode(cachens);
    	}
    }

    /**
     * 由服务模式获取缓存客户端
     * @param cachens 缓存命名空间名称
     * @return 缓存客户端
     * @author
     */
	private static ICacheClient getCacheClientByServiceMode(String cachens) {
		if (StringUtil.isBlank(cachens)) {
            throw new SDKException("请输入缓存服务配置映射的常量标识");
        }
        String mcsId = ConfigTool.getMCSId(cachens);
        String mcsPwd = ConfigTool.getServicePwd(mcsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), mcsPwd, mcsId);
        String keyId=authInfo.getPid()+"."+mcsId;
        ICacheClient client;
        try {
        	if (!baseMap_serviceMode.containsKey(keyId)) {
        		client = CacheFactory.getClient(authDescriptor);
    			baseMap_serviceMode.put(keyId, client);
    		}
        	else{
        		client=baseMap_serviceMode.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取缓存服务[" + mcsId + "]对应的客户端实例", e);
        }
        return client;
	}
	/**
	 * 由sdk模式获取缓存客户端
	 * @param cachens
	 * @return
	 * @author
	 */
	private static ICacheClient getCacheClientBySdkMode(String cachens) {
		if (StringUtil.isBlank(cachens)) {
			throw new SDKException("请输入缓存服务配置映射的常量标识");
		}
		String mcsId = ConfigTool.getMCSId(cachens);
		PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
		String appname = authInfo.getCcsAppName();
		Properties redisProp=ConfigTool.assembleMcsProperties(cachens);
		LOG.info("authInfo="+JSON.toJSONString(authInfo));
		
		String keyId=appname+"."+mcsId;
		ICacheClient client;
		try {
			if (!baseMap_sdkMode.containsKey(keyId)) {
				client = CacheCmpFactory.getClient(redisProp);
				baseMap_sdkMode.put(keyId, client);
			}
			else{
				client=baseMap_sdkMode.get(keyId);
			}
		} catch (Exception e) {
			throw new SDKException("无法获取缓存服务[" + mcsId + "]对应的客户端实例", e);
		}
		return client;
	}

}
