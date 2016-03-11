package com.ai.opt.sdk.configcenter.builder.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.opt.sdk.configcenter.builder.IConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.configcenter.client.impl.ZookeeperCCSClientImpl;
import com.ai.opt.sdk.helper.OptConfHelper;

public class ZookeeperConfigCenterBuilder implements IConfigCenterBuilder {

    private static final Logger LOG = LogManager.getLogger(ZookeeperConfigCenterBuilder.class);

    @Override
    public IConfigCenterClient getConfigCenterClient() {
        String zkHost = OptConfHelper.getInstance().getOwnerZKHost();
        String zkUser = OptConfHelper.getInstance().getOwnerZKUser();
        String zkPassword = OptConfHelper.getInstance().getOwnerZKPassword();
        int zkTimeout = OptConfHelper.getInstance().getOwnerZKTimeout();
        String zkAuthSchema = OptConfHelper.getInstance().getOwnerZKAuthSchema();
        LOG.debug("owner zkHost:" + zkHost);
        LOG.debug("owner zkUser:" + zkUser);
        LOG.debug("owner zkPassword:" + zkPassword);
        LOG.debug("owner zkTimeout:" + zkTimeout);
        LOG.debug("owner zkAuthSchema:" + zkAuthSchema);
        return new ZookeeperCCSClientImpl(zkHost, zkUser, zkPassword, zkAuthSchema, zkTimeout);
    }
}
