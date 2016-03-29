package com.ai.opt.sdk.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.ai.opt.sdk.configcenter.client.IConfigCenterClient;
import com.ai.opt.sdk.configcenter.factory.ConfigCenterFactory;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.model.McsConnectInfo;
import com.ai.opt.sdk.model.PaasConfInfo;
import com.ai.opt.sdk.util.StringUtil;
import com.zaxxer.hikari.HikariConfig;

import net.sf.json.JSONObject;

public final class OptConfHelper {

    private static OptConfHelper instance = null;

    private static Properties prop = new Properties();

    private OptConfHelper() {
        // 私有构造函数，不运行此类被外部实例化
    }

    public static OptConfHelper getInstance() {
        if (instance == null) {
            loadProp();
            instance = new OptConfHelper();
        }
        return instance;
    }
    /**
     * 手动加载paas-conf配置
     * @param p paas-conf配置  最小配置如下：
     * ccs.appname=aiopt-baas-xxx
     * ccs.zk_address=127.0.0.1:2181
     * @return
     * @author gucl
     * @ApiDocMethod
     * @ApiCode
     */
    public static OptConfHelper loadPaaSConf(Properties p) {
    	if (instance == null) {
    		prop=p;
    		instance = new OptConfHelper();
    	}
    	return instance;
    }

    private static void loadProp() {
        InputStream is = OptConfHelper.class.getClassLoader().getResourceAsStream(
                SDKConstants.PAAS_CONFIG_FILE);
        try {
            prop.load(is);
        } catch (IOException e) {
            throw new OptSDKException("loading paas config file failed", e);
        }
    }

    public PaasConfInfo getPaasConfInfo() {
    	String ccsAppName = prop.getProperty("ccs.appname");
        String ccsZkAddress = prop.getProperty("ccs.zk_address");

        if (StringUtil.isBlank(ccsAppName)) {
            throw new OptSDKException("paas ccs appname is null");
        }
        if (StringUtil.isBlank(ccsZkAddress)) {
            throw new OptSDKException("paas ccs zk_address is null");
        }
        
        PaasConfInfo paasConf = new PaasConfInfo();
        paasConf.setCcsAppName(ccsAppName);
        paasConf.setCcsZkAddress(ccsZkAddress);

        return paasConf;
    }


    public String getPropValue(String key) {
        if (StringUtil.isBlank(key)) {
            throw new OptSDKException("cannt get value because key is null");
        }
        return prop.containsKey(key) ? prop.getProperty(key) : null;
    }

	public Properties assembleMcsProperties(String namespace) {
		Properties mcsProperties=new Properties();
		IConfigCenterClient configCenterClient = ConfigCenterFactory.getConfigCenterClient();
        if (configCenterClient == null) {
            throw new OptSDKException("cann't get mcs conf because IConfigCenterClient is null");
        }
        // 获取mcs namespace映射信息
        String cacheNSConfStr = configCenterClient.get(
                SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH);
        if (StringUtil.isBlank(cacheNSConfStr)) {
            throw new OptSDKException("cann't get mcs conf from path["
                    + SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH + "]");
        }
        // 转换为JSON对象
        JSONObject cacheNSJson = JSONObject.fromObject(cacheNSConfStr);
        //namespace对应的redis集群标识
		String redisClusterId=cacheNSJson.getString(namespace);
		if(StringUtil.isBlank(redisClusterId)){
			throw new OptSDKException("cann't get mcs rediscluster id of namespace["
                    + namespace + "]");
		}
		// 获取redis集群配置信息
		String redisConfStr=configCenterClient.get(SDKConstants.PAAS_CACHE_REDIS_CLUSTER_MAPPED_PATH);
		
		if(StringUtil.isBlank(redisConfStr)){
			throw new OptSDKException("cann't get mcs redis conf of namespace["
                    + namespace + "],redisClusterId["+redisClusterId+"]");
		}
		
		JSONObject redisConfJson = JSONObject.fromObject(redisConfStr);
		JSONObject redisJson=(JSONObject) redisConfJson.get(redisClusterId);
		McsConnectInfo mcsInfo = (McsConnectInfo) JSONObject.toBean(redisJson, McsConnectInfo.class);
		mcsProperties.put("mcs.maxtotal", mcsInfo.getMcsMaxtotal());
        mcsProperties.put("mcs.maxIdle", mcsInfo.getMcsMaxIdle());
        mcsProperties.put("mcs.minIdle", mcsInfo.getMcsMinIdle());
        mcsProperties.put("mcs.testOnBorrow", mcsInfo.isMcsTestOnBorrow());
        mcsProperties.put("mcs.host", mcsInfo.getMcsHost());
        mcsProperties.put("mcs.password", mcsInfo.getMcsPassword());
		
		return mcsProperties;
	}

	
	/**
     * 获取DUBBO-REST的提供者信息
     * 
     * @return
     * @author gucl
     * @ApiDocMethod
     */
    public JSONObject getDubboRestProviderConf() {
        IConfigCenterClient configCenterBuilder = ConfigCenterFactory
                .getConfigCenterClient();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dubbo rest provider conf because IConfigCenterClient is null");
        }
        String conf = configCenterBuilder.get(
                SDKConstants.DUBBO_REST_PROVIDER_CONF_PATH);
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        JSONObject data = JSONObject.fromObject(conf);
        return data;
    }

    /**
     * 获取DUBBO提供者配置信息
     * 
     * @return
     * @author gucl
     */
    public JSONObject getDubboProviderConf() {
        IConfigCenterClient configCenterBuilder = ConfigCenterFactory
                .getConfigCenterClient();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dubbo provider conf because IConfigCenterClient is null");
        }
        String conf = configCenterBuilder.get(
                SDKConstants.DUBBO_PROVIDER_CONF_PATH);
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        JSONObject data = JSONObject.fromObject(conf);
        return data;
    }

    /**
     * 获取DUBBO消费者配置信息
     * 
     * @return
     * @author gucl
     */
    public JSONObject getDubboConsumerConf() {
        IConfigCenterClient configCenterBuilder = ConfigCenterFactory
                .getConfigCenterClient();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dubbo consumer conf because IConfigCenterClient is null");
        }
        String conf = configCenterBuilder.get(
                SDKConstants.DUBBO_CONSUMER_CONF_PATH);
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        JSONObject data = JSONObject.fromObject(conf);
        return data;
    }
    
    public HikariConfig getDBConf(String dataSourceName) {
        HikariConfig dbconf = null;
        IConfigCenterClient configCenterBuilder = ConfigCenterFactory
                .getConfigCenterClient();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get database conf because IConfigCenterBuilder is null");
        }
        // 获取配置信息字符串
        String dbConfStr = configCenterBuilder.get(
                SDKConstants.DB_CONF_PATH);
        if (StringUtil.isBlank(dbConfStr)) {
            throw new OptSDKException("cann't get database conf from path["
                    + SDKConstants.DB_CONF_PATH + "]");
        }
        // 转换为JSON对象
        JSONObject dbConfJson = JSONObject.fromObject(dbConfStr);

        // 获取指定dataSourceName对应的配置信息
        JSONObject confObject = (JSONObject) dbConfJson.get(dataSourceName);
        if (confObject == null) {
            throw new OptSDKException("cann't get database config info of dataSourceName["
                    + dataSourceName + "]");
        }
        dbconf = (HikariConfig) JSONObject.toBean(confObject, HikariConfig.class);

        return dbconf;
    }
}
