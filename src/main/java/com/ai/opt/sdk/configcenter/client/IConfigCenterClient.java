package com.ai.opt.sdk.configcenter.client;

import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.ccs.zookeeper.ConfigWatcher;

/**
 * 配置服务的Client，主要完成添加配置和获取配置的值
 */
public interface IConfigCenterClient {

    String get(String path);

    String get(String path, ConfigWatcher configWatcher) throws ConfigException;

    void add(String path, String config);

    void modify(String path, String config);

    boolean exists(String path);

}
