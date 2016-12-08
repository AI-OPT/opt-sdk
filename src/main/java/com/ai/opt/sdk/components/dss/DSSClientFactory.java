package com.ai.opt.sdk.components.dss;

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
import com.ai.paas.ipaas.dss.DSSFactory;
import com.ai.paas.ipaas.dss.base.DSSBaseFactory;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;
import com.alibaba.fastjson.JSON;

public final class DSSClientFactory {
	private static final Logger LOG = LoggerFactory.getLogger(DSSClientFactory.class);
	private static Map<String, IDSSClient> baseMap_serviceMode = new ConcurrentHashMap<String, IDSSClient>();
	private static Map<String, IDSSClient> baseMap_sdkMode = new ConcurrentHashMap<String, IDSSClient>();

    private DSSClientFactory() {

    }

    public static IDSSClient getDSSClient(String dssns) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
    	if(StringUtil.isBlank(authInfo.getPaasSdkMode())||SDKConstants.PAASMODE.PAAS_SERVICE_MODE.equals(authInfo.getPaasSdkMode())){
    		return getDssClientByServiceMode(dssns);
    	}
    	else{
    		return getDssClientBySdkMode(dssns);
    	}
    }

    private static IDSSClient getDssClientBySdkMode(String dssns) {
		if (StringUtil.isBlank(dssns)) {
            throw new SDKException("请输入文档存储服务配置映射的常量标识");
        }
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String appname = authInfo.getCcsAppName();
		LOG.info("authInfo="+JSON.toJSONString(authInfo));
		
        String dssId = ConfigTool.getDSSId(dssns);
        Properties dssProp=ConfigTool.assembleDssProperties(dssns);
        String mongoJson=JSON.toJSONString(dssProp);
        String keyId=appname+"."+dssId;
        IDSSClient client;
        try {
        	if (!baseMap_sdkMode.containsKey(keyId)) {
        		client = DSSBaseFactory.getClient(mongoJson);
        		baseMap_sdkMode.put(keyId, client);
    		}
        	else{
        		client=baseMap_sdkMode.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取文档存储服务[" + dssId + "]对应的客户端实例", e);
        }
        return client;
	}
	private static IDSSClient getDssClientByServiceMode(String dssns) {
		if (StringUtil.isBlank(dssns)) {
            throw new SDKException("请输入文档存储服务配置映射的常量标识");
        }
        String dssId = ConfigTool.getDSSId(dssns);
        String dssPwd = ConfigTool.getServicePwd(dssId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), dssPwd, dssId);
        String keyId=authInfo.getPid()+"."+dssId;
        IDSSClient client;
        try {
        	if (!baseMap_serviceMode.containsKey(keyId)) {
        		client = DSSFactory.getClient(authDescriptor);
    			baseMap_serviceMode.put(keyId, client);
    		}
        	else{
        		client=baseMap_serviceMode.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取文档存储服务[" + dssId + "]对应的客户端实例", e);
        }
        return client;
	}

}
