package com.ai.opt.sdk.configcenter.client.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.paas.ipaas.ccs.constants.AddMode;
import com.ai.paas.ipaas.ccs.constants.BundleKeyConstant;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.ccs.zookeeper.ConfigWatcher;
import com.ai.paas.ipaas.ccs.zookeeper.ZKClient;
import com.ai.paas.ipaas.ccs.zookeeper.impl.ZKPool;
import com.ai.paas.ipaas.ccs.zookeeper.impl.ZKPoolFactory;
import com.ai.paas.ipaas.util.ResourceUtil;
import com.ai.paas.ipaas.util.StringUtil;

public class ZookeeperCCSClientImpl implements IConfigCenterClient {

    private static final Logger LOG = LogManager.getLogger(ZookeeperCCSClientImpl.class);

    private String authInfo;

    private String zkUser;

    private ZKPool zkPool;

    private String zkAddr;

    private String zkAuthSchema;

    public ZookeeperCCSClientImpl(String zkAddr, String zkUser, String zkPassword,
            String zkAuthSchema, int timeout) {
        try {
            this.authInfo = (zkUser + ":" + zkPassword);
            this.zkUser = zkUser;
            this.zkAddr = zkAddr;
            this.zkAuthSchema = zkAuthSchema;
            this.zkPool = ZKPoolFactory.getZKPool(zkAddr, zkUser, zkPassword, timeout);
        } catch (Exception e) {
            LOG.error(e);
            throw new OptSDKException(
                    ResourceUtil.getMessage(BundleKeyConstant.GET_CONFIG_CLIENT_FAILED), e);
        }
    }

    @Override
    public String get(String path) {
        try {
            if (!exists(path)) {
                throw new Exception("节点[" + path + "]不存在");
            }
            return get(path, null);
        } catch (Exception e) {
            LOG.error(e);
            throw new OptSDKException("获取节点数据失败", e);
        }
    }

    @Override
    public void add(String path, String config) {
        byte[] data = null;
        if (!StringUtil.isBlank(config)) {
            try {
                data = config.getBytes("UTF-8");
                add(path, data, AddMode.PERSISTENT);
            } catch (UnsupportedEncodingException | ConfigException e) {
                LOG.error(e);
                throw new OptSDKException(
                        ResourceUtil.getMessage(BundleKeyConstant.CONVERT_DATA_FAILED), e);
            }
        }

    }

    @Override
    public void modify(String path, String config) {
        try {
            if (!exists(path)) {
                throw new Exception("节点[" + path + "]不存在");
            }
            byte[] data = null;
            if (!StringUtil.isBlank(config)) {
                try {
                    data = config.getBytes("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new ConfigException(
                            ResourceUtil.getMessage(BundleKeyConstant.CONVERT_DATA_FAILED), e);
                }
                modify(path, data);
            }
        } catch (Exception e) {
            LOG.error(e);
            throw new OptSDKException("修改节点配置失败", e);
        }
    }

    private void modify(String configPath, byte[] value) throws ConfigException {
        if (!exists(configPath)) {
            throw new ConfigException(ResourceUtil.getMessage(BundleKeyConstant.PATH_NOT_EXISTS,
                    new String[] { configPath }));
        }
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            client.setNodeData(configPath, value);
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new ConfigException(ResourceUtil.getMessage(
                        BundleKeyConstant.USER_AUTH_FAILED, new String[] { configPath }));
            }
            throw new ConfigException(ResourceUtil.getMessage(BundleKeyConstant.MODIFY_FAILED), e);
        }
    }

    @Override
    public boolean exists(String path) {
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            return client.exists(path);
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new RuntimeException(ResourceUtil.getMessage(
                        BundleKeyConstant.USER_AUTH_FAILED, new String[] { path }));
            }
            throw new OptSDKException(ResourceUtil.getMessage(BundleKeyConstant.PATH_NOT_EXISTS,
                    new String[] { path }), e);
        }
    }

    public String get(String configPath, ConfigWatcher watcher) throws ConfigException {
        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            return client.getNodeData(configPath, watcher);
        } catch (Exception e) {
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new ConfigException(ResourceUtil.getMessage(
                        BundleKeyConstant.USER_AUTH_FAILED, new String[] { configPath }));
            }
            throw new ConfigException(ResourceUtil.getMessage(BundleKeyConstant.ADD_CONFIG_FAILED),
                    e);
        }
    }

    private ZKClient getZkClientFromPool() throws Exception {
        ZKClient zkClient = this.zkPool.getZkClient(this.zkAddr, this.zkUser);

        if (zkClient == null) {
            throw new ConfigException(
                    ResourceUtil.getMessage(BundleKeyConstant.GET_CONFIG_CLIENT_FAILED));
        }
        return zkClient;
    }

    private void add(String configPath, byte[] bytes, AddMode mode) throws ConfigException {
        if (exists(configPath)) {
            throw new ConfigException(ResourceUtil.getMessage(BundleKeyConstant.PATH_EXISTS,
                    new String[] { configPath }));
        }

        ZKClient client = null;
        try {
            client = getZkClientFromPool();
            client.createNode(configPath, createWritableACL(), bytes,
                    AddMode.convertMode(mode.getFlag()));
        } catch (Exception e) {
            LOG.error(e);
            if ((e instanceof KeeperException.NoAuthException)) {
                throw new ConfigException(ResourceUtil.getMessage(
                        BundleKeyConstant.USER_AUTH_FAILED, new String[] { configPath }));
            }
            throw new ConfigException(ResourceUtil.getMessage(BundleKeyConstant.ADD_CONFIG_FAILED),
                    e);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private List<ACL> createWritableACL() throws NoSuchAlgorithmException {
        List acls = new ArrayList();
        Id id1 = new Id(this.zkAuthSchema, DigestAuthenticationProvider.generateDigest(this.authInfo));
        ACL userACL = new ACL(31, id1);
        acls.add(userACL);
        return acls;
    }

}
