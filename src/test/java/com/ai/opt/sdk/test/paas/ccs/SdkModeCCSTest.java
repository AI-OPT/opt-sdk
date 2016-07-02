package com.ai.opt.sdk.test.paas.ccs;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.alibaba.fastjson.JSON;

public class SdkModeCCSTest {

    private IConfigClient client;

    @Before
    public void initData() {
        this.client = CCSClientFactory.getDefaultConfigClient();
    }

    @Ignore
    @Test
    public void testGetConfig() throws Exception {
        client.add("/test", "test");
        assertEquals("test", client.get("/test"));
        System.out.println("aaaaaa");
    }

    @Ignore
    @Test
    public void addMcsConfig() throws ConfigException {
        // 缓存服务主机
        String mcs002 = "MCS001";
        // 缓存空间
        String cachesnsConfig = "{\"com.ai.opt.test.mcs\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gncfgproperties\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gnservicerouteconfig\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gndepart\":\"" + mcs002
                + "\",\"com.ai.runner.center.common.cache.gnsubject\":\"" + mcs002
                + "\",\"com.ai.runner.center.cache.test\":\"" + mcs002 + "\"}";
        
        StringBuilder sb = new StringBuilder();

        sb.append("{																																																				");
        sb.append("		\"MCS001\":             ");
        sb.append("		{                                                                                                     ");
        sb.append("			\"mcs.host\":\"127.0.0.1:6379\",   ");
        sb.append("			\"mcs.maxtotal\":\"200\",   ");
        sb.append("			\"mcs.maxIdle\":\"10\",     ");
        sb.append("			\"mcs.minIdle\":\"5\",   ");
        sb.append("			\"mcs.testOnBorrow\":\"true\",    ");
//        sb.append("			\"connectionTimeout\":\"30000\",   ");
//        sb.append("			\"idleTimeout\":\"600000\",     ");
//        sb.append("			\"maxLifetime\":\"1800000\",    ");
        sb.append("			\"mcs.password\":\"123456\"                                                                              ");
        sb.append("		}                                                                                                     ");
        sb.append("}              ");

        // 缓存空间配置
        if (!client.exists(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH))
            client.add(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH,
                    cachesnsConfig);
        else {
            client.modify(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH,
                    cachesnsConfig);
        }
        // redis配置
        if (!client.exists(SDKConstants.SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH))
            client.add(SDKConstants.SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH,
            		sb.toString());
        else {
            client.modify(SDKConstants.SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH,
                    sb.toString());
        }
    }
    @Ignore
    @Test
    public void readMcsConfig() throws ConfigException {
    	
    	String cachesns=client.get(SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH);
    	String redisinfo=client.get(SDKConstants.SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH);
    	
    	System.out.println("cachesns:"+cachesns);
    	System.out.println("redisinfo:"+redisinfo);
    	
    }
    @Test
    public void readMcsRedisConfig() throws ConfigException {
    	String namespace="com.ai.opt.test.mcs";
    	Properties p=ConfigTool.assembleMcsProperties(namespace);
    	
    	System.out.println("p:"+JSON.toJSONString(p));
    	
    }

    @Ignore
    @Test
    public void addMdsConfig() throws ConfigException {
        // 
        String mds001 = "MDS001";
        // 空间
        String mdssnsConfig = "{\"baas-bmc-topic\":\"" + mds001
                + "\",\"baas-amc-topic\":\"" + mds001
                + "\",\"baas-omc-topic\":\"" + mds001
                + "\",\"baas-smc-topic\":\"" + mds001 + "\"}";

        // MDS空间配置
        if (!client.exists(SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH))
            client.add(SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH,
                    mdssnsConfig);
        else {
            client.modify(SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH,
                    mdssnsConfig);
        }
        
        
        StringBuilder sb_kafkaSend = new StringBuilder();
        sb_kafkaSend.append("{																																																				");
        sb_kafkaSend.append("		\"MDS001\":                                                                                   ");
        sb_kafkaSend.append("		{                                                                                                     ");
        sb_kafkaSend.append("			\"metadata.broker.list\":\"192.168.0.15:9092\",                                                          ");
        sb_kafkaSend.append("			\"serializer.class\":\"kafka.serializer.DefaultEncoder\",   ");
        sb_kafkaSend.append("			\"key.serializer.class\":\"kafka.serializer.StringEncoder\",                                                                         ");
        sb_kafkaSend.append("			\"partitioner.class\":\"com.ai.paas.ipaas.mds.impl.sender.ModPartitioner\",                                                                         ");
        sb_kafkaSend.append("			\"request.required.acks\":\"1\",                                                                                ");
        sb_kafkaSend.append("			\"queue.buffering.max.messages\":\"1048576\",                                                                        ");
        sb_kafkaSend.append("			\"producer.type\":\"sync\",                                                                             ");
        sb_kafkaSend.append("			\"message.send.max.retries\":\"3\",                                                                            ");
        sb_kafkaSend.append("			\"compression.codec\":\"none\",                                                                            ");
        sb_kafkaSend.append("			\"request.timeout.ms\":\"20000\",                                                                            ");
        sb_kafkaSend.append("			\"batch.num.messages\":\"64000\",                                                                            ");
        sb_kafkaSend.append("			\"send.buffer.bytes\":\"67108864\",                                                                            ");
        sb_kafkaSend.append("			\"maxProducer\":\"5\",                                                                            ");
        sb_kafkaSend.append("			\"mds.topic\":\"test\"                                                                              ");
        sb_kafkaSend.append("		}                                                                                                     ");
        sb_kafkaSend.append("}                                                                                                        ");
        
        String kafkaSendConf=sb_kafkaSend.toString();
        
        //kafkaSendConf 
        if (!client.exists(SDKConstants.SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH))
            client.add(SDKConstants.SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH,
            		kafkaSendConf);
        else {
            client.modify(SDKConstants.SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH,
            		kafkaSendConf);
        }
        
        StringBuilder sb_kafkaConsume = new StringBuilder();
        sb_kafkaConsume.append("{																																																				");
        sb_kafkaConsume.append("		\"MDS001\":                                                                                   ");
        sb_kafkaConsume.append("		{                                                                                                     ");
        sb_kafkaConsume.append("			\"kafka.zookeeper.hosts\":\"192.168.0.15:2181\",                                                          ");
        sb_kafkaConsume.append("			\"kafka.zookeeper.broker.path\":\"/brokers\",   ");
        sb_kafkaConsume.append("			\"kafka.zookeeper.user\":\"\",                                                                         ");
        sb_kafkaConsume.append("			\"kafka.zookeeper.user.passwd\":\"\",                                                                         ");
        sb_kafkaConsume.append("			\"kafka.consumer.id\":\"mds-consumer-id-test\",                                                                                ");
        sb_kafkaConsume.append("			\"mds.partition.runninglock.path\":\"/baas/MDS/MDS001/consumer/partitions\",                                                                        ");
        sb_kafkaConsume.append("			\"mds.partition.pauselock.path\":\"/baas/MDS/MDS001/consumer/partitions\",                                                                             ");
        sb_kafkaConsume.append("			\"mds.partition.offset.basepath\":\"/baas/MDS/MDS001/consumer/offsets\",                                                                            ");
        sb_kafkaConsume.append("			\"mds.consumer.base.path\":\"/baas/MDS/MDS001\",                                                                            ");
        sb_kafkaConsume.append("			\"mds.zookeeper.hosts\":\"127.0.0.1:2181\",                                                                            ");
        sb_kafkaConsume.append("			\"mds.topic\":\"test\"                                                                              ");
        sb_kafkaConsume.append("		}                                                                                                     ");
        sb_kafkaConsume.append("}                                                                                                        ");
        
        String kafkaConsumeConf=sb_kafkaConsume.toString();
        
        //kafkaConsumeConf 
        if (!client.exists(SDKConstants.SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH))
            client.add(SDKConstants.SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH,
            		kafkaConsumeConf);
        else {
            client.modify(SDKConstants.SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH,
            		kafkaConsumeConf);
        }
        
    }
    @Test
    public void readMdsConf() throws ConfigException{
    	String mdsns=client.get(SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH);
    	String mdsSend=client.get(SDKConstants.SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH);
    	String mdConsumer=client.get(SDKConstants.SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH);
    	System.out.println("mdsns="+mdsns);
    	System.out.println("mdsSend="+mdsSend);
    	System.out.println("mdConsumer="+mdConsumer);
    }
    @Ignore
    @Test
    public void addDssConfig() throws ConfigException {
        // 缓存服务主机
        String dssId = "DSS001";
        // 缓存空间
        String dssnsConfig = "{\"baas-bmc-dss\":\"" + dssId
                + "\",\"baas-amc-dss\":\"" + dssId
                + "\",\"baas-omc-dss\":\"" + dssId
                + "\",\"baas-smc-dss\":\"" + dssId + "\"}";
        
        

        // 缓存空间配置
        if (!client.exists(SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH))
            client.add(SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH,
                    dssnsConfig);
        else {
            client.modify(SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH,
                    dssnsConfig);
        }
        

        StringBuilder sb_mongodb = new StringBuilder();
        sb_mongodb.append("{																																																				");
        sb_mongodb.append("		\"DSS001\":                                                                                   ");
        sb_mongodb.append("		{                                                                                                     ");
        sb_mongodb.append("			\"mongoServer\":\"127.0.0.1:27017\",                                                          ");
        sb_mongodb.append("			\"database\":\"foobar\",   ");
        sb_mongodb.append("			\"userName\":\"foobaruser\",                                                                         ");
        sb_mongodb.append("			\"password\":\"foobaruser\",                                                                         ");
        sb_mongodb.append("			\"bucket\":\"mygridfs01\"                                                                              ");
        sb_mongodb.append("		}                                                                                                     ");
        sb_mongodb.append("}                                                                                                        ");
        
        String mongodbConf=sb_mongodb.toString();
        if (!client.exists(SDKConstants.SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH))
            client.add(SDKConstants.SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH,
            		mongodbConf);
        else {
            client.modify(SDKConstants.SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH,
            		mongodbConf);
        }
        
        System.out.println("dssns="+client.get(SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH));
        System.out.println("mongodb="+client.get(SDKConstants.SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH));
        
    }
    @Ignore
    @Test
    public void addIdpsConfig() throws ConfigException {
        // 缓存服务主机
        String idpsId = "IDPS001";
        // 缓存空间
        String idpsnsConfig = "{\"slp-mall-web-idps\":\"" + idpsId
//                + "\",\"slp-mall-web-idps2\":\"" + idpsId
//                + "\",\"baas-omc-dss\":\"" + idpsId
                + "\",\"slp-mall-web-idps2\":\"" + idpsId + "\"}";
        
        

        // 缓存空间配置
        if (!client.exists(SDKConstants.PAAS_IDPSNS_IDPS_MAPPED_PATH))
            client.add(SDKConstants.PAAS_IDPSNS_IDPS_MAPPED_PATH,
                    idpsnsConfig);
        else {
            client.modify(SDKConstants.PAAS_IDPSNS_IDPS_MAPPED_PATH,
                    idpsnsConfig);
        }
    }

    /**
     * DB配置
     * @throws ConfigException 
     */
    @Ignore
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
    
    @Ignore
    @Test
    public void addDtsInfo() throws ConfigException {
        System.out.println("dts config ... start");
        StringBuilder sb = new StringBuilder();
       sb.append("{                                                                                          ");
       sb.append("	   \"org.quartz.jobStore.dataSource\":\"myDS\",                                              ");
       sb.append("	   \"org.quartz.dataSource.myDS.driver\":\"com.mysql.jdbc.Driver\",                          ");
       sb.append("	   \"org.quartz.dataSource.myDS.URL\":\"jdbc:mysql://10.1.245.7:31306/devslpdtsdb1\",        ");
       sb.append("	   \"org.quartz.dataSource.myDS.user\":\"devslpdtsusr1\",                                    ");
       sb.append("	   \"org.quartz.dataSource.myDS.password\":\"devslpdtsusr1@8899\",                           ");
       sb.append("	   \"org.quartz.dataSource.myDS.maxConnections\":\"5\",                                      ");
       sb.append("	   \"org.quartz.dataSource.myDS.validationQuery\":\"select 0\",                              ");
       sb.append("	   \"org.quartz.jobStore.misfireThreshold\":\"60000\",                                       ");
       sb.append("	   \"org.quartz.jobStore.useProperties\":\"false\",                                          ");
       sb.append("	   \"org.quartz.jobStore.tablePrefix\":\"QRTZ_\",                                            ");
       sb.append("	   \"org.quartz.jobStore.class\":\"com.ai.opt.sdk.dts.jdbcstore.DTSJobStore\",               ");
       sb.append("	   \"org.quartz.jobStore.isClustered\":\"true\",                                             ");
       sb.append("	   \"org.quartz.scheduler.skipUpdateCheck\":\"true\",                                        ");
       sb.append("	   \"org.quartz.threadPool.threadCount\":\"5\",                                              ");
       sb.append("	   \"org.quartz.threadPool.class\":\"org.quartz.simpl.SimpleThreadPool\",                    ");
       sb.append("	   \"org.quartz.threadPool.threadPriority\":\"5\"                                            ");
       sb.append("	}                                                                                        ");
        
        
        if (!client.exists(SDKConstants.DTS_QUARTZ_CONF_PATH)) {
            client.add(SDKConstants.DTS_QUARTZ_CONF_PATH, sb.toString());
        } else {
            client.modify(SDKConstants.DTS_QUARTZ_CONF_PATH, sb.toString());
        }

        System.out.println("dts config ... end");
    }

}