package com.ai.opt.sdk.test.paas.configcenter;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.ai.opt.sdk.configcenter.builder.IConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.builder.impl.PaaSConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.builder.impl.ZookeeperConfigCenterBuilder;
import com.ai.opt.sdk.constants.SDKConstants;

public class IConfigCenterClientTest {

    private IConfigCenterBuilder builder;

    @Before
    public void initData() {
        this.builder = new PaaSConfigCenterBuilder();
    }

    // @Test
    public void testGetConfig() throws Exception {
        builder.getConfigCenterClient().add("/test", "test");
        assertEquals("test", builder.getConfigCenterClient().get("/test"));
    }

//    @Test
    public void addMcsConfig() {
        // 缓存服务主机
        String mcsHost = "127.0.0.1:6379";
        // 缓存空间
        String cachesnsConfig = "{\"com.ai.opt.test.mcs\":\"" + mcsHost
                + "\",\"com.ai.runner.center.common.cache.gncfgproperties\":\"" + mcsHost
                + "\",\"com.ai.runner.center.common.cache.gnservicerouteconfig\":\"" + mcsHost
                + "\",\"com.ai.runner.center.common.cache.gndepart\":\"" + mcsHost
                + "\",\"com.ai.runner.center.common.cache.gnsubject\":\"" + mcsHost
                + "\",\"com.ai.runner.center.cache.test\":\"" + mcsHost + "\"}";
        // 缓存服务主机和密码设置
        if (!builder.getConfigCenterClient().exists(
                SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.MCS)) {
            builder.getConfigCenterClient().add(
                    SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.MCS,
                    "{\"" + mcsHost + "\":\"\"}");
        } else {
            builder.getConfigCenterClient().modify(
                    SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.MCS,
                    "{\"" + mcsHost + "\":\"\"}");
        }

        // 缓存空间配置
        if (!builder.getConfigCenterClient().exists(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH))
            builder.getConfigCenterClient().add(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH,
                    cachesnsConfig);
        else {
            builder.getConfigCenterClient().modify(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH,
                    cachesnsConfig);
        }
    }

    /**
     * Txs配置
     */
    // @Test
    public void addTxsConfig() {
        System.out.println("TXS config ... start");
        String serviceId = "TXS001";
        if (!builder.getConfigCenterClient().exists(SDKConstants.PAAS_TXS_PATH)) {
            builder.getConfigCenterClient().add(SDKConstants.PAAS_TXS_PATH, serviceId);
        } else {
            builder.getConfigCenterClient().modify(SDKConstants.PAAS_TXS_PATH, serviceId);
        }

        if (!builder.getConfigCenterClient().exists(
                SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.TXS))
            builder.getConfigCenterClient().add(
                    SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.TXS,
                    "{" + serviceId + ":123456}");
        else {
            builder.getConfigCenterClient().modify(
                    SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.TXS,
                    "{" + serviceId + ":123456}");
        }
        System.out.println("TXS config ... end");
    }

    /**
     * ATS配置
     */
    // @Test
    public void addAtsConfig() {
        System.out.println("ATS config ... start");
        String signatureId = "signatureId-79831126-1ef2-4b53-b5e1-cb4792df1577";
        if (!builder.getConfigCenterClient().exists(SDKConstants.PAAS_ATS_SIGNATUREID_PATH)) {
            builder.getConfigCenterClient()
                    .add(SDKConstants.PAAS_ATS_SIGNATUREID_PATH, signatureId);
        } else {
            builder.getConfigCenterClient().modify(SDKConstants.PAAS_ATS_SIGNATUREID_PATH,
                    signatureId);
        }
        System.out.println("ATS config ... end");
    }

    /**
     * DBS配置
     */
    // @Test
    public void addDbsConfig() {
        System.out.println("DBS config ... start");
        String serviceId = "DBS003";
        if (!builder.getConfigCenterClient().exists(SDKConstants.PAAS_DBS_PATH)) {
            builder.getConfigCenterClient().add(SDKConstants.PAAS_DBS_PATH, serviceId);
        } else {
            builder.getConfigCenterClient().modify(SDKConstants.PAAS_DBS_PATH, serviceId);
        }

        if (!builder.getConfigCenterClient().exists(
                SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.DBS))
            builder.getConfigCenterClient().add(
                    SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.DBS,
                    "{" + serviceId + ":123456}");
        else {
            builder.getConfigCenterClient().modify(
                    SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + SDKConstants.DBS,
                    "{" + serviceId + ":123456}");
        }

        System.out.println("DBS config ... end");
    }

    // @Test
    public void testAddOwnerZK() {
        IConfigCenterBuilder b = new ZookeeperConfigCenterBuilder();
        b.getConfigCenterClient().add("/com/ai/runner/test/ab", "123");
    }

    // @Test
    public void testGetOwnerZK() {
        IConfigCenterBuilder b = new ZookeeperConfigCenterBuilder();
        String c = b.getConfigCenterClient().get("/com/ai/runner/test/ab");
        System.out.println(c);
    }

    /**
     * DBS配置
     */
     @Test
    public void addDbConfInfo() {
        System.out.println("DBConf config ... start");
        StringBuilder sb = new StringBuilder();

        sb.append("{																																																				");
        sb.append("		\"bis-db\":                                                                                   ");
        sb.append("		{                                                                                                     ");
        sb.append("			\"driverClassName\":\"com.mysql.jdbc.Driver\",                                                          ");
        sb.append("			\"jdbcUrl\":\"jdbc:mysql://10.1.228.222:39306/devbisdb1?useUnicode=true&characterEncoding=UTF-8\",   ");
        sb.append("			\"username\":\"devbisusr1\",                                                                         ");
        sb.append("			\"password\":\"devbisusr1\",                                                                         ");
        sb.append("			\"autoCommit\":\"true\",                                                                                ");
        sb.append("			\"connectionTimeout\":\"30000\",                                                                        ");
        sb.append("			\"idleTimeout\":\"600000\",                                                                             ");
        sb.append("			\"maxLifetime\":\"1800000\",                                                                            ");
        sb.append("			\"maximumPoolSize\":\"10\"                                                                              ");
        sb.append("		}                                                                                                     ");
        sb.append("}                                                                                                        ");

        if (!builder.getConfigCenterClient().exists(SDKConstants.DB_CONF_PATH)) {
            builder.getConfigCenterClient().add(SDKConstants.DB_CONF_PATH, sb.toString());
        } else {
            builder.getConfigCenterClient().modify(SDKConstants.DB_CONF_PATH, sb.toString());
        }

        System.out.println("DBConf config ... end");
    }

}