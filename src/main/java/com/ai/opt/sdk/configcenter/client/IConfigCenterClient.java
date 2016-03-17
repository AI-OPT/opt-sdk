package com.ai.opt.sdk.configcenter.client;

import java.util.List;

import com.ai.paas.ipaas.ccs.constants.AddMode;
import com.ai.paas.ipaas.ccs.zookeeper.ConfigWatcher;
import com.ai.paas.ipaas.ccs.zookeeper.MutexLock;

/**
 * 配置服务的Client，主要完成添加配置和获取配置的值
 */
public interface IConfigCenterClient {

    MutexLock getMutexLock(String path);
    String get(String path);
    String get(String path, ConfigWatcher configWatcher);
    byte[] readBytes(String path);
    byte[] readBytes(String path, ConfigWatcher configWatcher);
    void add(String path, String config);
    void add(String path, String config, AddMode zkAddMode);
    void add(String path, byte[] bytes);
	void add(String path, byte[] bytes, AddMode zkAddMode);
    void modify(String path, String config);
    void modify(String path, byte[] bytes);
    boolean exists(String path);
    boolean exists(String path, ConfigWatcher configWatcher);
    List<String> listSubPath(String path);
	List<String> listSubPath(String path, ConfigWatcher configWatcher)	;
	void remove(String path);
    
    

}
