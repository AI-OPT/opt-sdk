package com.ai.opt.sdk.components.idps;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.idps.constants.IDPSConsants;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.image.ImageCmpFactory;
import com.ai.paas.ipaas.image.ImageFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;
import com.alibaba.fastjson.JSON;

/**
 * IDPS客户端工厂
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class IDPSClientFactory {
	private static final Logger LOG = LoggerFactory.getLogger(IDPSClientFactory.class);
	private static Map<String, IImageClient> baseMap = new ConcurrentHashMap<String, IImageClient>();
	private static Map<String, IImageClient> baseMap_sdkMode = new ConcurrentHashMap<String, IImageClient>();

    private IDPSClientFactory() {

    }

    /**
     * 获取图片服务客户端
     * @param idpsns
     * @return
     * @author
     */
    public static IImageClient getImageClient(String idpsns) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
    	if(StringUtil.isBlank(authInfo.getPaasSdkMode())||SDKConstants.PAASMODE.PAAS_SERVICE_MODE.equals(authInfo.getPaasSdkMode())){
    		return getImageClientByServiceMode(idpsns);
    	}
    	else{
    		return getImageClientBySdkMode(idpsns);
    	}
    }
    /**
     * 由sdk模式获取图片服务客户端
     * @param idpsns
     * @return
     * @author
     */
    private static IImageClient getImageClientBySdkMode(String idpsns) {
    	if (StringUtil.isBlank(idpsns)) {
    		throw new SDKException("请输入图片服务配置映射的常量标识");
    	}
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String appname = authInfo.getCcsAppName();
		LOG.info("authInfo="+JSON.toJSONString(authInfo));
		
    	String idpsId = ConfigTool.getIDPSId(idpsns);
    	Properties idpsProp=ConfigTool.assembleIdpsProperties(idpsns);
    	String keyId=appname+"."+idpsId;
    	IImageClient client;
    	try {
    		if (!baseMap_sdkMode.containsKey(keyId)) {
    			client = ImageCmpFactory.getClient(idpsProp.getProperty(IDPSConsants.INTERURL),idpsProp.getProperty(IDPSConsants.INTRAURL));
    			baseMap_sdkMode.put(keyId, client);
    		}
    		else{
    			client=baseMap_sdkMode.get(keyId);
    		}
    	} catch (Exception e) {
    		throw new SDKException("无法获取图片服务[" + idpsId + "]对应的客户端实例", e);
    	}
    	return client;
    }
    /**
     * 由服务模式获取图片服务客户端
     * @param idpsns
     * @return
     * @author
     */
    private static IImageClient getImageClientByServiceMode(String idpsns) {
    	if (StringUtil.isBlank(idpsns)) {
    		throw new SDKException("请输入图片服务配置映射的常量标识");
    	}
    	String idpsId = ConfigTool.getIDPSId(idpsns);
    	String idpsPwd = ConfigTool.getServicePwd(idpsId);
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
    	AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
    			authInfo.getPid(), idpsPwd, idpsId);
    	String keyId=authInfo.getPid()+"."+idpsId;
    	IImageClient client;
    	try {
    		if (!baseMap.containsKey(keyId)) {
    			client = ImageFactory.getClient(authDescriptor);
    			baseMap.put(keyId, client);
    		}
    		else{
    			client=baseMap.get(keyId);
    		}
    	} catch (Exception e) {
    		throw new SDKException("无法获取图片服务[" + idpsId + "]对应的客户端实例", e);
    	}
    	return client;
    }

}
