package com.ai.opt.sdk.components.mcs;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.mcs.CacheFactory;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;

public final class MCSFactory {

    private MCSFactory() {

    }

    public static ICacheClient getCacheClient(String cachens) {
        if (StringUtil.isBlank(cachens)) {
            throw new SDKException("请输入缓存服务配置映射的常量标识");
        }
        String ccsId = ConfigTool.getCCSId(cachens);
        String ccsPwd = ConfigTool.getServicePwd(ccsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), ccsPwd, ccsId);
        ICacheClient client;
        try {
            client = CacheFactory.getClient(authDescriptor);
        } catch (Exception e) {
            throw new SDKException("无法获取缓存服务[" + ccsId + "]对应的客户端实例", e);
        }
        return client;
    }

}
