package com.ai.opt.sdk.components.ses;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.search.service.ISearchClient;
import com.ai.paas.ipaas.search.service.SearchClientFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;

public final class SESClientFactory {

    private static Map<String, ISearchClient> baseMap = new ConcurrentHashMap<String, ISearchClient>();

    private SESClientFactory() {

    }

    public static ISearchClient getSearchClient(String sesns) {
        if (StringUtil.isBlank(sesns)) {
            throw new SDKException("请输入搜索服务配置映射的常量标识");
        }
        String sesId = ConfigTool.getSESId(sesns);
        String sesPwd = ConfigTool.getServicePwd(sesId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), sesPwd, sesId);
        ISearchClient client;
        try {
            if (!baseMap.containsKey(sesId)) {
                client = SearchClientFactory.getSearchClient(authDescriptor);
                baseMap.put(sesId, client);
            } else {
                client = baseMap.get(sesId);
            }
        } catch (Exception e) {
            throw new SDKException("无法获取SES服务[" + sesId + "]对应的客户端实例", e);
        }
        return client;
    }
}
