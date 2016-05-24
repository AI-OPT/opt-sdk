package com.ai.opt.sdk.constants;

public final class SDKConstants {

    private SDKConstants() {

    }

    /**
     * PaaS配置中心配置文件
     */
    public static final String PAAS_CONFIG_FILE = "paas/paas-conf.properties";

    // 配置某种场景下用哪个缓存服务ID
    // {"com.ai.opt.xxx.xxx":"MCS001","com.ai.opt.xxx.yyy":"MCS002","com.ai.opt.xxx.zzz":"MCS003"}
    public static final String PAAS_CACHENS_MCS_MAPPED_PATH = "/com/ai/opt/paas-cachens-mcs-mapped";

    // 配置某种场景下用哪个消息服务ID
    // {"baasSmcCheckTopic":"MDS001","baasAmcTopic":"MDS002","baasOmcTopic":"MDS003"}
    public static final String PAAS_MDSNS_MDS_MAPPED_PATH = "/com/ai/opt/paas-mdsns-mds-mapped";

    // 配置消息服务ID与实际kakfa topic名称的映射关系
    // {"MDS001":"BCA976731EF24B899B143755A3AF5794_MDS001_1743120261","MDS002":"BCA976731EF24B899B143755A3AF5794_MDS001_1743120261"}
    public static final String PAAS_MDS_TOPIC_MAPPED_PATH = "/com/ai/opt/paas-mds-topic-mapped";

    // 配置某种场景下用哪个文档存储服务ID
    // {"com.ai.opt.xxx.xxx":"DSS001","com.ai.opt.xxx.yyy":"DSS002","com.ai.opt.xxx.zzz":"DSS003"}
    public static final String PAAS_DSSNS_DSS_MAPPED_PATH = "/com/ai/opt/paas-dssns-dss-mapped";
    
    // 配置某种场景下用哪个图片服务ID
    // {"com.ai.opt.xxx.xxx":"IDPS001","com.ai.opt.xxx.yyy":"IDPS002","com.ai.opt.xxx.zzz":"IDPS003"}
    public static final String PAAS_IDPSNS_IDPS_MAPPED_PATH = "/com/ai/opt/paas-idpsns-idps-mapped";

    // 配置某种场景下用哪个搜索服务ID
    // {"com.ai.opt.xxx.xxx":"SES001","com.ai.opt.xxx.yyy":"SES002","com.ai.opt.xxx.zzz":"SES003"}
    public static final String PAAS_SESNS_SES_MAPPED_PATH = "/com/ai/opt/paas-sesns-ses-mapped";

    // 技术服务与密码的映射关系 {"MCS001":"password","DSS001":"password","MDS001":"password"}
    public static final String PAAS_SERVICE_PWD_MAPPED_PATH = "/com/ai/opt/paas-service-pwd-mapped";

    /**
     * db-conf的配置信息 /com/ai/opt/db-conf { "opt-uac-db": { "driverClassName":"com.mysql.jdbc.Driver",
     * "jdbcUrl":
     * "jdbc:mysql://10.1.228.222:39306/devibssgndb1?useUnicode=true&characterEncoding=UTF-8",
     * "username":"devibssgnusr1", "password":"devibssgnusr1", "autoCommit":"true",
     * "connectionTimeout":"30000", "idleTimeout":"600000", "maxLifetime":"1800000",
     * "maximumPoolSize":"10" }, "opt-sys-db": { "driverClassName":"com.mysql.jdbc.Driver",
     * "jdbcUrl"
     * :"jdbc:mysql://10.1.228.222:39306/devibsscmdb1?useUnicode=true&characterEncoding=UTF-8",
     * "username":"devibsscmusr1", "password":"devibsscmusr1", "autoCommit":"true",
     * "connectionTimeout":"30000", "idleTimeout":"600000", "maxLifetime":"1800000",
     * "maximumPoolSize":"10" } }
     */
    public static final String DB_CONF_PATH = "/com/ai/opt/db-conf";

    /**
     * /com/ai/opt/dubbo/provider {"dubbo.provider.retries":"0","dubbo.registry.address":
     * "10.123.121.253\:39181","dubbo.provider.timeout":"30000"}
     */
    public static final String DUBBO_PROVIDER_CONF_PATH = "/com/ai/opt/dubbo/provider";

    /**
     * /com/ai/opt/dubbo-rest/provider
     * {"dubbo-rest.appname":"opt-customer-center","dubbo-rest.registry.protocol"
     * :"zookeeper","dubbo-rest.registry.address":
     * "10.123.121.253\:39181","dubbo-rest.protocol":"rest","dubbo-rest.server":"jetty","dubbo-rest.contextpath":"customer","dubbo-rest.port":"21000","dubbo-rest.provider.timeou
     * t " : " 3 0 0 0" }
     */
    public static final String DUBBO_REST_PROVIDER_CONF_PATH = "/com/ai/opt/dubbo-rest/provider";

    /**
     * /com/ai/opt/dubbo/consumer
     * {"opt-customer.registry.address":"10.123.121.253\:39181","opt-bis.registry.address":
     * "10.123.121.253\:39181"}
     */
    public static final String DUBBO_CONSUMER_CONF_PATH = "/com/ai/opt/dubbo/consumer";

}
