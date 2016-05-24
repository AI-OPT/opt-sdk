package com.ai.opt.sdk.components.idps;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.image.IImageClient;
import com.ai.paas.ipaas.image.ImageClientFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;

public final class IDPSClientFactory {
	private static Map<String, IImageClient> baseMap = new ConcurrentHashMap<String, IImageClient>();

    private IDPSClientFactory() {

    }

    public static IImageClient getImageClient(String idpsns) {
        if (StringUtil.isBlank(idpsns)) {
            throw new SDKException("请输入图片服务配置映射的常量标识");
        }
        String idpsId = ConfigTool.getIDPSId(idpsns);
        String idpsPwd = ConfigTool.getServicePwd(idpsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), idpsPwd, idpsId);
        IImageClient client;
        try {
        	if (!baseMap.containsKey(idpsId)) {
        		client = ImageClientFactory.getClient(authDescriptor);
    			baseMap.put(idpsId, client);
    		}
        	else{
        		client=baseMap.get(idpsId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取图片服务[" + idpsId + "]对应的客户端实例", e);
        }
        return client;
    }

}
