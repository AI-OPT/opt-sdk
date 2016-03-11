package com.ai.opt.sdk.configcenter.builder.impl;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.opt.sdk.configcenter.builder.IConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.configcenter.client.impl.PaasCCSClientImpl;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.helper.OptConfHelper;
import com.ai.opt.sdk.model.PaasAuthInfo;
import com.ai.paas.ipaas.ccs.ConfigFactory;
import com.ai.paas.ipaas.ccs.IConfigClient;

/**
 * 配置中心Client的构建器，目前返回为PaaS的配置中心的Client，
 */
public class PaaSConfigCenterBuilder implements IConfigCenterBuilder {

    private static final Logger LOG = LogManager.getLogger(PaaSConfigCenterBuilder.class);

    public IConfigCenterClient getConfigCenterClient() {
        PaasAuthInfo authInfo = OptConfHelper.getInstance().getPaasAuthInfo();
        Properties config = OptConfHelper.getInstance().assembleCcsProperties(authInfo);
        IConfigClient client = null;
        try {
            client = ConfigFactory.getConfigClient(config, SDKConstants.PAAS_CCS_DEFAULT_TIMEOUT);
        } catch (Exception e) {
            LOG.error("get paas config center error", e);
            throw new OptSDKException(e);
        }
        return new PaasCCSClientImpl(client);
    }

}
