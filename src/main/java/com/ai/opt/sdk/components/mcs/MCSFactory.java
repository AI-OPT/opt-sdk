package com.ai.opt.sdk.components.mcs;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.ccs.ConfigFactory;
import com.ai.paas.ipaas.mcs.CacheFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;

public final class MCSFactory {
	private static Map<String, ICacheClient> baseMap = new ConcurrentHashMap<String, ICacheClient>();

    private MCSFactory() {

    }

    public static ICacheClient getCacheClient(String cachens) {
        if (StringUtil.isBlank(cachens)) {
            throw new SDKException("请输入缓存服务配置映射的常量标识");
        }
        String mcsId = ConfigTool.getMCSId(cachens);
        String mcsPwd = ConfigTool.getServicePwd(mcsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), mcsPwd, mcsId);
        ICacheClient client;
        try {
        	if (!baseMap.containsKey(mcsId)) {
        		client = CacheFactory.getClient(authDescriptor);
    			baseMap.put(mcsId, client);
    		}
        	else{
        		client=baseMap.get(mcsId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取缓存服务[" + mcsId + "]对应的客户端实例", e);
        }
        return client;
    }

}
