package com.ai.opt.sdk.cache.builder.impl;

import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.opt.sdk.cache.builder.ICacheClientBuilder;
import com.ai.opt.sdk.cache.client.ICacheClient;
import com.ai.opt.sdk.cache.client.impl.PaaSCacheClientImpl;
import com.ai.opt.sdk.configcenter.factory.ConfigCenterBuilderFactory;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.helper.OptConfHelper;
import com.ai.opt.sdk.model.PaasAuthInfo;
import com.ai.opt.sdk.tools.PaaSServiceTool;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.ccs.zookeeper.ConfigWatcher;
import com.ai.paas.ipaas.ccs.zookeeper.ConfigWatcherEvent;
import com.ai.paas.ipaas.mcs.CacheFactory;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PaaSCacheClientBuilder implements ICacheClientBuilder {

    private static final Logger LOG = LogManager.getLogger(PaaSCacheClientBuilder.class);

    private static final StringBuilder cacheClientConfig = new StringBuilder();

    private static ConfigWatcher watcher = new ConfigWatcher() {
        @Override
        public void processEvent(ConfigWatcherEvent configWatcherEvent) {
            if (configWatcherEvent.getType() == ConfigWatcher.Event.EventType.NodeDataChanged) {
                LOG.info("缓存配置已经被修改，正在更新缓存配置.....");
                cacheClientConfig.delete(0, cacheClientConfig.length());
                cacheClientConfig.append(ConfigCenterBuilderFactory.getConfigCenterBuilder()
                        .getConfigCenterClient().get(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH));
                LOG.info("缓存配置已经被修改，更新缓存配置完成");
            }
        }
    };

    static {
        try {
            cacheClientConfig.delete(0, cacheClientConfig.length());
            cacheClientConfig.append(ConfigCenterBuilderFactory.getConfigCenterBuilder()
                    .getConfigCenterClient()
                    .get(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH, watcher));
        } catch (ConfigException e) {
            LOG.error("获取CacheClient配置出错", e);
            throw new OptSDKException("获取CacheClient配置出错 ["
                    + SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH + "] ");
        }
    }

    public ICacheClient getCacheClient(String namespace) {
        if (StringUtils.isBlank(cacheClientConfig)) {
            throw new OptSDKException("cann't get paas cache mcs mapped config from ["
                    + SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH + "] ");
        }
        JsonObject jsonObject = new Gson().fromJson(cacheClientConfig.toString(), JsonObject.class);

        JsonElement serviceIdElement = jsonObject.get(namespace);
        if (serviceIdElement == null) {
            throw new OptSDKException("不能找到缓存空间[" + namespace + "]的配置");
        }
        String serviceId = serviceIdElement.getAsString();
        String password = PaaSServiceTool.getMCSPwd(serviceId);
        PaasAuthInfo authInfo = OptConfHelper.getInstance().getPaasAuthInfo();
        Properties mcsProperties = OptConfHelper.getInstance().assembleMcsProperties(authInfo,
                serviceId, password);
        com.ai.paas.ipaas.mcs.interfaces.ICacheClient client = null;
        try {
            long startTime = System.currentTimeMillis();
            client = CacheFactory.getClient(mcsProperties);
            if (LOG.isDebugEnabled()) {
                LOG.debug("获取MCS Client花费时间:{}毫秒", (System.currentTimeMillis() - startTime));
            }
        } catch (Exception e) {
            LOG.error("获取PaaS的缓存Client错误.", e);
            throw new OptSDKException(e);
        }

        return new PaaSCacheClientImpl(client);
    }
}
