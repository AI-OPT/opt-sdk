package com.ai.opt.sdk.constants;

public final class SDKConstants {

    private SDKConstants() {

    }

    /**
     * PaaS配置中心配置文件
     */
    public static final String PAAS_CONFIG_FILE = "paas/paas-conf.properties";

    /**
     * 缓存空间和PaaS缓存ID的配置<br/>
     * <p/>
     * {"namespaceA":"seviceIdA", "namspaceB":"serviceIdA", "namspaceC":"serivceB"}
     */
    public static final String PAAS_CACHENS_MCS_MAPPED_PATH = "/com/ai/opt/paas-cachens-mcs-mapped";

    /**
     * 适用场景命名空间和DSS服务的映射关系配置<br/>
     * <p/>
     * {"namespaceA":"DSS001", "namspaceB":"DSS002", "namspaceC":"serivceB"}
     */
    public static final String PAAS_NS_DSS_MAPPED_PATH = "/com/ai/opt/paas-ns-dss-mapped";

    /**
     * ServiceID和其对应密码，用户名和ServiceID对应关系，每个ServiceID都有一条配置项<br/>
     * <p/>
     * 例如ServiceIdA的配置如下： /com/ai/opt/paas-service-pwd-mapped/mcs {"MCS001":"password"}
     * 
     * /com/ai/opt/paas-service-pwd-mapped/txs {"TXS001":"password"}
     * /com/ai/opt/paas-service-pwd-mapped/dss {"DSS001":"password"}
     * /com/ai/opt/paas-service-pwd-mapped/ses {"SES001":"password","SES002":"password"}
     */
    public static final String PAAS_SERVICE_PWD_MAPPED_PATH = "/com/ai/opt/paas-service-pwd-mapped/";

    /**
     * 当前系统需要使用的TXS服务ID是什么 /com/ai/opt/paas-txs TXS001
     */
    public static final String PAAS_TXS_PATH = "/com/ai/opt/paas-txs";

    /**
     * 适用场景命名空间和SES服务的映射关系配置<br/>
     * <p/>
     * {"namespaceA":"SES001", "namspaceB":"SES002", "namspaceC":"serivceB"}
     */
    public static final String PAAS_NS_SES_MAPPED_PATH = "/com/ai/opt/paas-ns-ses-mapped";

    /**
     * 当前系统需要使用的DBS服务ID是什么 /com/ai/opt/paas-dbs DBS001
     */
    public static final String PAAS_DBS_PATH = "/com/ai/opt/paas-dbs";

    /**
     * 引用该Util的中心产品的ATS的SignId，可以有多个,以逗号隔开。格式如下：
     * <p/>
     * /com/ai/opt/paas-ats-signatureId signatureId-bd5ff9b1-6f4d-4bbe-899e-840fc57b4cf0
     */
    public static final String PAAS_ATS_SIGNATUREID_PATH = "/com/ai/opt/paas-ats-signatureId";

    /**
     * 配置ATS服务对应的签名ID {key:value} 其中key="com.xx.xx.IXXSV#method1" value="signatureId"
     */
    public static final String PAAS_ATS_SERVICE_SIGNATUREID_MAPPED_PATH = "/com/ai/opt/paas-atsservice-signatureId-mapped";

    /**
     * elasticsearch的配置信息 /com/ai/opt/es-conf {"hostname":"10.1.234.160","port":14999}
     */
    public static final String ELASTIC_SEARCH_CONF_PATH = "/com/ai/opt/es-conf";

    /**
     * db-conf的配置信息 /com/ai/opt/db-conf { "common-center-db": {
     * "driverClassName":"com.mysql.jdbc.Driver", "jdbcUrl":
     * "jdbc:mysql://10.1.228.222:39306/devibssgndb1?useUnicode=true&characterEncoding=UTF-8",
     * "username":"devibssgnusr1", "password":"devibssgnusr1", "autoCommit":"true",
     * "connectionTimeout":"30000", "idleTimeout":"600000", "maxLifetime":"1800000",
     * "maximumPoolSize":"10" }, "customer-center-db": { "driverClassName":"com.mysql.jdbc.Driver",
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

    /**
     * DTS所需要的ZOOKEEPER的配置信息 {"dts.zk.address":
     * "10.1.228.222\:19181","dts.zk.schema":"digest","dts.zk.auth":"super:admin"}
     */
    public static final String DTS_ZK_CONF_PATH = "/com/ai/opt/dts/zk-conf";

    /**
     * DTS所需要的QUARTZ的配置信息 {"org.quartz.dataSource.myDS.validationQuery":"select 0",
     * "org.quartz.dataSource.myDS.password"
     * :"devrunnerdtsusr1","org.quartz.scheduler.skipUpdateCheck"
     * :"true","org.quartz.jobStore.dataSource"
     * :"myDS","org.quartz.jobStore.misfireThreshold":"60000"
     * ,"org.quartz.dataSource.myDS.URL":"jdbc:mysql://10.1.228.222:39306/devrunnerdtsdb1"
     * ,"org.quartz.threadPool.threadCount"
     * :"5","org.quartz.jobStore.useProperties":"false","org.quartz.threadPool.class"
     * :"org.quartz.simpl.SimpleThreadPool"
     * ,"org.quartz.dataSource.myDS.driver":"org.postgresql.Driver"
     * ,"org.quartz.jobStore.tablePrefix"
     * :"QRTZ_","org.quartz.dataSource.myDS.maxConnections":"5","org.quartz.jobStore.class"
     * :"com.ai.opt.sdk.dts.jdbcstore.DTSJobStore"
     * ,"org.quartz.jobStore.isClustered":"true","org.quartz.threadPool.threadPriority"
     * :"5","org.quartz.dataSource.myDS.user":"devrunnerdtsusr1"}
     */
    public static final String DTS_QUARTZ_CONF_PATH = "/com/ai/opt/dts/quartz-conf";

    /**
     * FramePageMenu所需要的配置信息 {"systemId":"runner-common-center","systemModeId":"GGGL","returnUrl":
     * "http://10.1.228.222:14201/BIS-Door/door/index"}
     */
    public static final String FRAMEPAGE_CONF_PATH = "/com/ai/runner/framepage-conf";

    public static final String MCS = "mcs";

    public static final String TXS = "txs";

    public static final String DBS = "dbs";

    public static final String DSS = "dss";

    public static final String SES = "ses";

    /**
     * ccs默认超时时间
     */
    public static final int PAAS_CCS_DEFAULT_TIMEOUT = 1000 * 10;
}
