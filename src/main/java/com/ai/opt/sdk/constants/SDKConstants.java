package com.ai.opt.sdk.constants;

public final class SDKConstants {

    private SDKConstants() {

    }

    /**
     * PaaS配置中心配置文件
     */
    public static final String PAAS_CONFIG_FILE = "paas/paas-conf.properties";
    
    /**
     * db-conf的配置信息 /com/ai/opt/db-conf 
     * { 
     *   "common-center-db": 
     *   {
     * 		"driverClassName":"com.mysql.jdbc.Driver", "jdbcUrl":
     * 		"jdbc:mysql://10.1.228.222:39306/devibssgndb1?useUnicode=true&characterEncoding=UTF-8",
     * 		"username":"devibssgnusr1", "password":"devibssgnusr1", "autoCommit":"true",
     * 		"connectionTimeout":"30000", "idleTimeout":"600000", "maxLifetime":"1800000",
     * 		"maximumPoolSize":"10" 
     *   }, 
     *  "customer-center-db": 
     *  { 
     *  	"driverClassName":"com.mysql.jdbc.Driver",
     * 		"jdbcUrl":"jdbc:mysql://10.1.228.222:39306/devibsscmdb1?useUnicode=true&characterEncoding=UTF-8",
     * 		"username":"devibsscmusr1", "password":"devibsscmusr1", "autoCommit":"true",
     * 		"connectionTimeout":"30000", "idleTimeout":"600000", "maxLifetime":"1800000",
     * 		"maximumPoolSize":"10" 
     *   } 
     * }
     */
    public static final String DB_CONF_PATH = "/com/ai/opt/db-conf";
    
    /**
     * 缓存空间和redis主机(集群)ID的配置<br/>
     * <p/>
     * {"namespaceA":"redisclusterA", "namspaceB":"redisclusterB", "namspaceC":"redisclusterC"}
     */
    public static final String PAAS_CACHENS_MCS_MAPPED_PATH = "/com/ai/opt/paas-cachens-mcs-mapped";
    
    /**
     * 缓存空间和redis集群ID的配置<br/>
     * <p/>
     * 示例数据：
     * {
     *   "redisclusterA":
     *   {
     * 		  "mcsHost":"127.0.0.1:6379", 
     * 	  	  "mcsMaxtotal":"200", 
     * 		  "mcsMaxIdle":"10",
     * 		  "mcsMinIdle":"5",
     * 		  "mcsTestOnBorrow":"true",
     * 		  "mcsPassword":"123456"
     *   },
     *   "redisclusterB":
     *   {
     *        "mcsHost":"192.168.0.21:6379;192.168.0.22:6379;192.168.0.23:6379", 
     * 	  	  "mcsMaxtotal":"200", 
     * 		  "mcsMaxIdle":"10",
     * 		  "mcsMinIdle":"5",
     * 		  "mcsTestOnBorrow":"true",
     * 		  "mcsPassword":"123456"
     *   }
     * }
     */
    public static final String PAAS_CACHE_REDIS_CLUSTER_MAPPED_PATH = "/com/ai/opt/paas-cache-redis-cluster-mapped";

    
    
    /**
     * /com/ai/opt/dubbo/provider {"dubbo.provider.retries":"0","dubbo.registry.address":
     * "10.123.121.253\:39181","dubbo.provider.timeout":"30000"}
     */
    public static final String DUBBO_PROVIDER_CONF_PATH = "/com/ai/opt/dubbo/provider";

    /**
     * /com/ai/opt/dubbo-rest/provider
     * {"dubbo-rest.appname":"opt-customer-center","dubbo-rest.registry.protocol"
     * :"zookeeper","dubbo-rest.registry.address":
     * "10.123.121.253\:39181","dubbo-rest.protocol":"rest","dubbo-rest.server":"jetty","dubbo-rest.contextpath":"customer","dubbo-rest.port":"21000","dubbo-rest.provider.timeout
     * " : " 3 0 0 0" }
     */
    public static final String DUBBO_REST_PROVIDER_CONF_PATH = "/com/ai/opt/dubbo-rest/provider";

    /**
     * /com/ai/opt/dubbo/consumer
     * {"opt-customer.registry.address":"10.123.121.253\:39181","opt-bis.registry.address":
     * "10.123.121.253\:39181"}
     */
    public static final String DUBBO_CONSUMER_CONF_PATH = "/com/ai/opt/dubbo/consumer";


    public static final String MCS = "mcs";

    /**
     * ccs默认超时时间
     */
    public static final int PAAS_CCS_DEFAULT_TIMEOUT = 1000 * 10;
}
