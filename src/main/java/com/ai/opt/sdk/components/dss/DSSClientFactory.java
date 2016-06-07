package com.ai.opt.sdk.components.dss;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.dss.DSSFactory;
import com.ai.paas.ipaas.dss.base.interfaces.IDSSClient;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;

public final class DSSClientFactory {
	private static Map<String, IDSSClient> baseMap = new ConcurrentHashMap<String, IDSSClient>();

    private DSSClientFactory() {

    }

    public static IDSSClient getDSSClient(String dssns) {
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
        	if (!baseMap.containsKey(keyId)) {
        		client = DSSFactory.getClient(authDescriptor);
    			baseMap.put(keyId, client);
    		}
        	else{
        		client=baseMap.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取文档存储服务[" + dssId + "]对应的客户端实例", e);
        }
        return client;
    }

}
