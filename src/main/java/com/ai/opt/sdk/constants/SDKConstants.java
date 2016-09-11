package com.ai.opt.sdk.constants;

public final class SDKConstants {

    private SDKConstants() {

    }

    /**
     * PaaS配置中心配置文件
     */
    public static final String PAAS_CONFIG_FILE = "paas/paas-conf.properties";
    
    /**
     * PaaS使用模式
     */
    public static final class PAASMODE {
    	private PAASMODE(){};
    	public static final String PAAS_SERVICE_MODE="0";//服务模式
    	public static final String PAAS_SDK_MODE="1";//sdk模式
    }

    /**
     * 配置某种场景下用哪个缓存服务ID
     * {"com.ai.opt.xxx.xxx":"MCS001","com.ai.opt.xxx.yyy":"MCS002","com.ai.opt.xxx.zzz":"MCS003"}
     */
    public static final String PAAS_CACHENS_MCS_MAPPED_PATH = "/com/ai/opt/paas-cachens-mcs-mapped";
    
    /**
     * sdk模式下，mcs对应的真实redis主机信息<br/>
     * <p/>
     * 示例数据：
     * {
     *   "MCS001":
     *   {
     * 		  "mcs.host":"127.0.0.1:6379", 
     * 	  	  "mcs.maxtotal":"200", 
     * 		  "mcs.maxIdle":"10",
     * 		  "mcs.minIdle":"5",
     * 		  "mcs.testOnBorrow":"true",
     * 		  "mcs.password":"123456"
     *   },
     *   "MCS002":
     *   {
     *        "mcs.host":"192.168.0.21:6379;192.168.0.22:6379;192.168.0.23:6379", 
     * 	  	  "mcs.maxtotal":"200", 
     * 		  "mcs.maxIdle":"10",
     * 		  "mcs.minIdle":"5",
     * 		  "mcs.testOnBorrow":"true",
     * 		  "mcs.password":"123456"
     *   }
     * }
     */
    public static final String SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH = "/com/ai/opt/sdkmode-paas-mcs-redis-mapped";

    /**
     * 配置某种场景下用哪个消息服务ID
     * {"baasSmcCheckTopic":"MDS001","baasAmcTopic":"MDS002","baasOmcTopic":"MDS003"}
     */
    public static final String PAAS_MDSNS_MDS_MAPPED_PATH = "/com/ai/opt/paas-mdsns-mds-mapped";
    
    /**
     * sdk模式下，mds sender对应的真实kafka信息
     * {
     * 	"MDS001":
     *  {
     *  	"metadata.broker.list":"10.1.245.7:49091,10.1.245.6:39091,10.1.245.7:39091",
     *  	"serializer.class":"kafka.serializer.DefaultEncoder",
     *  	"key.serializer.class":"kafka.serializer.StringEncoder",
     *  	"partitioner.class":"com.ai.paas.ipaas.mds.impl.sender.ModPartitioner",
     *  	"request.required.acks":"1",
     *  	"queue.buffering.max.messages":"1048576",
     *  	"producer.type":"sync",
     *  	"message.send.max.retries":"3",
     *  	"compression.codec":"none",
     *  	"request.timeout.ms":"20000",
     *  	"batch.num.messages":"200",
     *  	"maxProducer":"0","send.buffer.bytes":"67108864",
     *  	"mds.topic":"bmc_kafka"
     *   }
     * }
     */
    public static final String SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH = "/com/ai/opt/sdkmode-paas-mds-sender-mapped";
    /**
     * sdk模式下，mds consumer对应的真实kafka信息
     * {
     *   "MDS001":
     *   {
     *   	"kafka.zookeeper.hosts":"10.1.245.5:29181,10.1.245.6:29181,10.1.245.7:29181",
     *   	"kafka.zookeeper.broker.path":"/brokers",
     *   	"kafka.zookeeper.user":"",
     *   	"kafka.zookeeper.user.passwd":"",
     *   	"mds.consumer.base.path":"/baas/MDS/MDS001",
     *   	"mds.zookeeper.hosts":"127.0.0.1:2181",
     *   	"mds.topic":"bmc_kafka"
     *   }
     * }
     */
    public static final String SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH = "/com/ai/opt/sdkmode-paas-mds-consumer-mapped";

    // 配置消息服务ID与实际kakfa topic名称的映射关系
    // {"MDS001":"BCA976731EF24B899B143755A3AF5794_MDS001_1743120261","MDS002":"BCA976731EF24B899B143755A3AF5794_MDS001_1743120261"}
    public static final String PAAS_MDS_TOPIC_MAPPED_PATH = "/com/ai/opt/paas-mds-topic-mapped";

    /**
     * 配置某种场景下用哪个文档存储服务ID
     * {"com.ai.opt.xxx.xxx":"DSS001","com.ai.opt.xxx.yyy":"DSS002","com.ai.opt.xxx.zzz":"DSS003"}
     */
    public static final String PAAS_DSSNS_DSS_MAPPED_PATH = "/com/ai/opt/paas-dssns-dss-mapped";
    
    //
    //
    /**
     * sdk模式下，paas服务对应的真实mongodb信息
     * {
     *   "DSS001":
     *   {
     *   	"mongoServer":"10.1.xxx.xxx:37017;10.1.xxx.xxx:37017",
     *   	"database":"image",
     *   	"userName":"sa",
     *   	"password":"sa",
     *   	"bucket":"mygridfs01"
     *   },
     *   "DSS002":
     *   {
     *   	"mongoServer":"10.1.xxx.xxx:37017;10.1.xxx.xxx:37017",
     *   	"database":"image",
     *   	"userName":"sa",
     *   	"password":"sa",
     *   	"bucket":"mygridfs01"
     *   }
     * }
     */
    public static final String SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH = "/com/ai/opt/sdkmode-paas-dss-mongodb-mapped";
    
    /**
     * 配置某种场景下用哪个图片服务ID
     * {"com.ai.opt.xxx.xxx":"IDPS001","com.ai.opt.xxx.yyy":"IDPS002","com.ai.opt.xxx.zzz":"IDPS003"}
     */
    public static final String PAAS_IDPSNS_IDPS_MAPPED_PATH = "/com/ai/opt/paas-idpsns-idps-mapped";
    
    /**
     * sdk模式下，paas服务对应的真实elasticsearch信息
     * {
     *   "IDPS001":
     *   {
     *   	"interUrl":"http://10.1.245.8:18030/iPaas-IDPS",
     *   	"intraUrl":"http://10.1.245.8:18030/iPaas-IDPS"     
     *   },
     *   "IDPS002":
     *   {
     *   	"interUrl":"http://www.xxx.com/iPaas-IDPS/",
     *   	"intraUrl":"http://10.1.245.226:18000/iPaas-IDPS/"  
     *   }
     * }
     */
    public static final String SDK_MODE_PAAS_IDPS_GM_MAPPED_PATH = "/com/ai/opt/sdkmode-paas-idps-gm-mapped";

    /**
     * 配置某种场景下用哪个搜索服务ID
     * {"com.ai.opt.xxx.xxx":"SES001","com.ai.opt.xxx.yyy":"SES002","com.ai.opt.xxx.zzz":"SES003"}
     */
    public static final String PAAS_SESNS_SES_MAPPED_PATH = "/com/ai/opt/paas-sesns-ses-mapped";

    /**
     * sdk模式下，paas服务对应的真实elasticsearch信息
     * {
     *   "SES001":
     *   {
     *   	"eshosts":"127.0.0.1:9300,127.0.0.1:9300",
     *   	"indexname":"user-detail-index",
     *   	"mappingid":"userid",
     *   	"mapping":
	 *    			{
	 *					"userInfo" : {
	 *						    "properties" : {
	 *						     	"userId" :  {"type" : "string", "store" : "yes","index": "not_analyzed"},
	 *						       	"name" : {"type" : "string", "store" : "yes","analyzer":"ik_max_word"},
	 *						       	"age" : {"type" : "integer"},
	 *						       	"created" : {"type" : "date", "format" : "strict_date_optional_time||epoch_millis"}
	 *						    }   
	 *					}  
	 *		},
     *      "shards":"2",
     *   	"replicas":"1"
     *   },
     *   "SES002":
     *   {
     *   	"eshosts":"127.0.0.1:9300,127.0.0.1:9300",
     *   	"indexname":"order-detail-index",
     *   	"mappingid":"orderid",
     *   	"mapping":
	 *    			{
	 *					"userInfo" : {
	 *						    "properties" : {
	 *						     	"userId" :  {"type" : "string", "store" : "yes","index": "not_analyzed"},
	 *						       	"name" : {"type" : "string", "store" : "yes","analyzer":"ik_max_word"},
	 *						       	"age" : {"type" : "integer"},
	 *						       	"created" : {"type" : "date", "format" : "strict_date_optional_time||epoch_millis"}
	 *						    }   
	 *					}  
	 *		},
     *      "shards":"2",
     *   	"replicas":"1"
     *   }
     * }
     */
    public static final String SDK_MODE_PAAS_SES_ELASTICSEARCH_MAPPED_PATH = "/com/ai/opt/sdkmode-paas-ses-elasticsearch-mapped";
    
    
    
    
    /**
     * 技术服务与密码的映射关系 {"MCS001":"password","DSS001":"password","MDS001":"password"}
     */
    public static final String PAAS_SERVICE_PWD_MAPPED_PATH = "/com/ai/opt/paas-service-pwd-mapped";

    /**
     * db-conf的配置信息 /com/ai/opt/db-conf 
     * { 
     * 		"opt-uac-db": 
     * 		{ 
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devibssgndb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devibssgnusr1", "password":"devibssgnusr1", "autoCommit":"true",
     * 			"connectionTimeout":"30000", "idleTimeout":"600000", "maxLifetime":"1800000",
     * 			"maximumPoolSize":"10" },
     * 		 "opt-sys-db": 
     * 		{ 
     * 			"driverClassName":"com.mysql.jdbc.Driver",
     * 			"jdbcUrl" :"jdbc:mysql://10.1.228.222:39306/devibsscmdb1?useUnicode=true&characterEncoding=UTF-8",
     * 			"username":"devibsscmusr1", "password":"devibsscmusr1", "autoCommit":"true",
     * 			"connectionTimeout":"30000", "idleTimeout":"600000", "maxLifetime":"1800000",
     * 			"maximumPoolSize":"10" 
     * 		} 
     * }
     */
    public static final String DB_CONF_PATH = "/com/ai/opt/db-conf";

    /**
     * 暂时不用该配置
     * /com/ai/opt/dubbo/provider {"dubbo.provider.retries":"0","dubbo.registry.address":
     * "10.123.121.253\:39181","dubbo.provider.timeout":"30000"}
     */
    public static final String DUBBO_PROVIDER_CONF_PATH = "/com/ai/opt/dubbo/provider";

    /**
     * 暂时不用该配置
     * /com/ai/opt/dubbo-rest/provider
     * {"dubbo-rest.appname":"opt-customer-center","dubbo-rest.registry.protocol"
     * :"zookeeper","dubbo-rest.registry.address":
     * "10.123.121.253\:39181","dubbo-rest.protocol":"rest","dubbo-rest.server":"jetty","dubbo-rest.contextpath":"customer","dubbo-rest.port":"21000","dubbo-rest.provider.timeou
     * t " : " 3 0 0 0" }
     */
    public static final String DUBBO_REST_PROVIDER_CONF_PATH = "/com/ai/opt/dubbo-rest/provider";

    /**
     * 暂时不用该配置
     * /com/ai/opt/dubbo/consumer
     * {"opt-customer.registry.address":"10.123.121.253\:39181","opt-bis.registry.address":
     * "10.123.121.253\:39181"}
     */
    public static final String DUBBO_CONSUMER_CONF_PATH = "/com/ai/opt/dubbo/consumer";

    
    
    /**
     * DTS所需要的QUARTZ的配置信息 
     * {
     *  "org.quartz.jobStore.dataSource":"myDS",
     *  "org.quartz.dataSource.myDS.driver":"com.mysql.jdbc.Driver",
     *  "org.quartz.dataSource.myDS.URL":"jdbc:mysql://10.1.245.7:31306/devslpdtsdb1",
     *  "org.quartz.dataSource.myDS.user":"devslpdtsusr1",
     *  "org.quartz.dataSource.myDS.password":"devslpdtsusr1@8899",
     *  "org.quartz.dataSource.myDS.maxConnections":"5",
     *  "org.quartz.dataSource.myDS.validationQuery":"select 0",
     *  "org.quartz.jobStore.misfireThreshold":"60000",
     *  "org.quartz.jobStore.useProperties":"false",
     *  "org.quartz.jobStore.tablePrefix":"QRTZ_",
     *  "org.quartz.jobStore.class":"com.ai.opt.sdk.dts.jdbcstore.DTSJobStore",
     *  "org.quartz.jobStore.isClustered":"true",
     *  "org.quartz.scheduler.skipUpdateCheck":"true",
     *  "org.quartz.threadPool.threadCount":"5",
     *  "org.quartz.threadPool.class":"org.quartz.simpl.SimpleThreadPool",
     *  "org.quartz.threadPool.threadPriority":"5"
     * }
     */
	public static final String DTS_QUARTZ_CONF_PATH = "/com/ai/opt/dts/quartz-conf";

}
