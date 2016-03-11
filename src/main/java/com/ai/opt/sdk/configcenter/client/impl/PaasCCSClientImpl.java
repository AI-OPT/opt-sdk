package com.ai.opt.sdk.configcenter.client.impl;

import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.ccs.zookeeper.ConfigWatcher;

public class PaasCCSClientImpl implements IConfigCenterClient {

    private IConfigClient client;

    public PaasCCSClientImpl(IConfigClient client) {
        this.client = client;
    }

    @Override
    public String get(String path) {
        try {
            return client.get(path);
        } catch (ConfigException e) {
            return null;
        }
    }

    @Override
    public String get(String path, ConfigWatcher configWatcher) throws ConfigException {
       return client.get(path, configWatcher);
    }

    @Override
    public void add(String path, String config) {
        try {
            client.add(path, config);
        } catch (ConfigException e) {
            throw new OptSDKException("add config error", e);
        }
    }

    @Override
    public void modify(String path, String config) {
        try {
            client.modify(path, config);
        } catch (ConfigException e) {
            throw new OptSDKException("add config error", e);
        }
    }

    @Override
    public boolean exists(String path) {
        try {
            return client.exists(path);
        } catch (ConfigException e) {
            return false;
        }
    }

}
