package com.ai.opt.sdk.test.paas.ccs;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.components.ccs.CCSFactory;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;

public class CCSTest {

    private IConfigClient client;

    @Before
    public void initData() {
        this.client = CCSFactory.getDefaultConfigClient();
    }

    @Ignore
    @Test
    public void testGetConfig() throws Exception {
        client.add("/test", "test");
        assertEquals("test", client.get("/test"));
        System.out.println("aaaaaa");
    }

    @Test
    public void addServiceIdPwdMap() throws ConfigException {
    	String cachesnsConfig = "{\"MCS001\":\"" + "123456"     
    			+ "\",\"MCS002\":\"" + "123456"
    			+ "\"}";
        
        // paas serviceid password 映射配置
        if (!client.exists(SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH))
            client.add(SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH,
                    cachesnsConfig);
        else {
            client.modify(SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH,
                    cachesnsConfig);
        }
    }
    //@Ignore
    @Test
    public void addMcsConfig() throws ConfigException {
        // 缓存服务主机
        String mcs002 = "MCS002";
        // 缓存空间
        String cachesnsConfig = "{\"com.ai.opt.test.mcs\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gncfgproperties\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gnservicerouteconfig\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gndepart\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gnsubject\":\"" + mcs002
                + "\",\"com.ai.runner.center.cache.test\":\"" + mcs002 + "\"}";
        
        

        // 缓存空间配置
        if (!client.exists(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH))
            client.add(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH,
                    cachesnsConfig);
        else {
            client.modify(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH,
                    cachesnsConfig);
        }
    }
    //@Ignore
    @Test
    public void readMcsConfig() throws ConfigException {
    	
    	String cachesns=client.get(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH);
    	
    	System.out.println("cachesns:"+cachesns);
    	
    }

    

    /**
     * DBS配置
     * @throws ConfigException 
     */
     @Test
    public void addDbConfInfo() throws ConfigException {
        System.out.println("DBConf config ... start");
        StringBuilder sb = new StringBuilder();

        sb.append("{																																																				");
        sb.append("		\"opt-uac-db\":                                                                                   ");
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

        if (!client.exists(SDKConstants.DB_CONF_PATH)) {
            client.add(SDKConstants.DB_CONF_PATH, sb.toString());
        } else {
            client.modify(SDKConstants.DB_CONF_PATH, sb.toString());
        }

        System.out.println("DBConf config ... end");
    }

}