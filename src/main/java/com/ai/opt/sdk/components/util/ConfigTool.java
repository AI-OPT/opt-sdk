package com.ai.opt.sdk.components.util;

import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.ccs.CCSClientFactory;
import com.ai.opt.sdk.components.dss.constants.DSSConsants;
import com.ai.opt.sdk.components.mcs.constants.MCSConstants;
import com.ai.opt.sdk.components.mds.constants.MDSConsumerConstants;
import com.ai.opt.sdk.components.mds.constants.MDSSenderConstants;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.ccs.IConfigClient;
import com.ai.paas.ipaas.ccs.constants.ConfigException;
import com.ai.paas.ipaas.util.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zaxxer.hikari.HikariConfig;

public final class ConfigTool {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigTool.class);

    private ConfigTool() {

    }

    public static final String getServicePwd(String serviceId) {
        try {
            if (StringUtil.isBlank(serviceId)) {
                throw new SDKException("服务ID为空，无法获取服务密码");
            }
            String conf = CCSClientFactory.getDefaultConfigClient().get(
                    SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH);
            if (StringUtil.isBlank(conf)) {
                throw new SDKException("获取的服务标识与密码映射配置为空，请检查默认配置服务中的相关配置");
            }
            JSONObject data = JSON.parseObject(conf);
            String pwd = data.getString(serviceId);
            if (StringUtil.isBlank(pwd)) {
                throw new SDKException("从默认配置服务中无法获取服务[" + serviceId + "]对应的密码");
            }
            return pwd;
        } catch (ConfigException e) {
            throw new SDKException("获取服务标识与密码映射配置错误", e);
        }
    }

    public static final String getMCSId(String cachens) {
        try {
            if (StringUtil.isBlank(cachens)) {
                throw new SDKException("命名空间为空，无法获取缓存服务ID");
            }
            String conf = CCSClientFactory.getDefaultConfigClient().get(
                    SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH);
            if (StringUtil.isBlank(conf)) {
                throw new SDKException("获取不到缓存应用场景对应的CCS服务ID，请检查默认配置服务中的相关配置");
            }
            JSONObject data = JSON.parseObject(conf);
            String mcsId = data.getString(cachens);
            if (StringUtil.isBlank(mcsId)) {
                throw new SDKException("从默认配置服务中无法获取缓存命名空间[" + cachens + "]对应的CCS服务ID");
            }
            return mcsId;
        } catch (ConfigException e) {
            throw new SDKException("获取缓存命名空间对应的服务ID错误", e);
        }
    }

    public static final String getDSSId(String dssns) {
        try {
            if (StringUtil.isBlank(dssns)) {
                throw new SDKException("命名空间为空，无法获取文档存储服务ID");
            }
            String conf = CCSClientFactory.getDefaultConfigClient().get(
                    SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH);
            if (StringUtil.isBlank(conf)) {
                throw new SDKException("获取不到文档存储应用场景对应的CCS服务ID，请检查默认配置服务中的相关配置");
            }
            JSONObject data = JSON.parseObject(conf);
            String dssId = data.getString(dssns);
            if (StringUtil.isBlank(dssId)) {
                throw new SDKException("从默认配置服务中无法获取文档存储命名空间[" + dssns + "]对应的DSS服务ID");
            }
            return dssId;
        } catch (ConfigException e) {
            throw new SDKException("获取文档存储命名空间对应的服务ID错误", e);
        }
    }

    public static final String getSESId(String sesns) {
        try {
            if (StringUtil.isBlank(sesns)) {
                throw new SDKException("命名空间为空，无法获取搜索服务服务ID");
            }
            String conf = CCSClientFactory.getDefaultConfigClient().get(
                    SDKConstants.PAAS_SESNS_SES_MAPPED_PATH);
            if (StringUtil.isBlank(conf)) {
                throw new SDKException("获取不到文档存储应用场景对应的CCS服务ID，请检查默认配置服务中的相关配置");
            }
            JSONObject data = JSON.parseObject(conf);
            String dssId = data.getString(sesns);
            if (StringUtil.isBlank(dssId)) {
                throw new SDKException("从默认配置服务中无法获取搜索服务命名空间[" + sesns + "]对应的SES服务ID");
            }
            return dssId;
        } catch (ConfigException e) {
            throw new SDKException("获取搜索服务命名空间对应的SES服务ID错误", e);
        }
    }

    /**
     * 获取业务场景对应的MDS ID
     * 
     * @param mdsns
     * @return
     * @author gucl
     * @ApiDocMethod
     * @ApiCode
     */
    public static final String getMDSId(String mdsns) {
        try {
            if (StringUtil.isBlank(mdsns)) {
                throw new SDKException("命名空间为空，无法获取消息服务ID");
            }
            String conf = CCSClientFactory.getDefaultConfigClient().get(
                    SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH);
            if (StringUtil.isBlank(conf)) {
                throw new SDKException("获取不到消息应用场景对应的MDS服务ID，请检查默认配置服务中的相关配置");
            }
            JSONObject data = JSON.parseObject(conf);
            String mdsId = data.getString(mdsns);
            if (StringUtil.isBlank(mdsId)) {
                throw new SDKException("从默认配置服务中无法获取消息命名空间[" + mdsns + "]对应的MDS服务ID");
            }
            return mdsId;
        } catch (ConfigException e) {
            throw new SDKException("获取消息命名空间对应的服务ID错误", e);
        }
    }
    
    public static final String getIDPSId(String idpsns) {
        try {
            if (StringUtil.isBlank(idpsns)) {
                throw new SDKException("命名空间为空，无法获取图片服务ID");
            }
            String conf = CCSClientFactory.getDefaultConfigClient().get(
                    SDKConstants.PAAS_IDPSNS_IDPS_MAPPED_PATH);
            if (StringUtil.isBlank(conf)) {
                throw new SDKException("获取不到图片应用场景对应的CCS服务ID，请检查默认配置服务中的相关配置");
            }
            JSONObject data = JSON.parseObject(conf);
            String dssId = data.getString(idpsns);
            if (StringUtil.isBlank(dssId)) {
                throw new SDKException("从默认配置服务中无法获取图片命名空间[" + idpsns + "]对应的IDPS服务ID");
            }
            return dssId;
        } catch (ConfigException e) {
            throw new SDKException("获取图片命名空间对应的服务ID错误", e);
        }
    }

    public static final String getMDSTopic(String mdsId) {
        try {
            if (StringUtil.isBlank(mdsId)) {
                throw new SDKException("消息服务ID为空，无法获取消息服务对应的topic");
            }
            String conf = CCSClientFactory.getDefaultConfigClient().get(
                    SDKConstants.PAAS_MDS_TOPIC_MAPPED_PATH);
            if (StringUtil.isBlank(conf)) {
                throw new SDKException("获取不到消息ID对应的topic，请检查默认配置服务中的相关配置");
            }
            JSONObject data = JSON.parseObject(conf);
            String mdsTopic = data.getString(mdsId);
            if (StringUtil.isBlank(mdsTopic)) {
                throw new SDKException("从默认配置服务中无法获取消息服务ID[" + mdsId + "]对应的topic");
            }
            return mdsTopic;
        } catch (ConfigException e) {
            throw new SDKException("获取消息ID对应的topic错误", e);
        }
    }

    public static HikariConfig getDBConf(String dataSourceName) {
        String data;
        try {
            data = CCSClientFactory.getDefaultConfigClient().get(SDKConstants.DB_CONF_PATH);
        } catch (ConfigException e) {
            throw new SDKException("get database conf error from path[" + SDKConstants.DB_CONF_PATH
                    + "]", e);
        }
        if (StringUtil.isBlank(data)) {
            throw new SDKException("cann't get database conf from path["
                    + SDKConstants.DB_CONF_PATH + "]");
        }
        JSONObject dbConfJson = JSONObject.parseObject(data);
        JSONObject confObject = (JSONObject) dbConfJson.get(dataSourceName);
        if (confObject == null) {
            throw new SDKException("cann't get database config info of dataSourceName["
                    + dataSourceName + "]");
        }
        HikariConfig dbconf = JSONObject.toJavaObject(confObject, HikariConfig.class);
        return dbconf;
    }
    
    
    
    public static Properties getDTSQuartzProperties() {
        String conf="";
		try {
			conf = CCSClientFactory.getDefaultConfigClient().get(
			        SDKConstants.DTS_QUARTZ_CONF_PATH);
		} catch (ConfigException e) {
			throw new SDKException("get dts conf error from path[" + SDKConstants.DTS_QUARTZ_CONF_PATH
                    + "]", e);
		}
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        Properties p = new Properties();
        
        JSONObject data = JSONObject.parseObject(conf);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
        	String key=entry.getKey();
        	String value=(String) entry.getValue();
        	p.put(key, value);
        }
        
        return p;
    }
    
    
    public static Properties assembleMcsProperties(String namespace) {
		Properties mcsProperties=new Properties();
		IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        if (configClient == null) {
            throw new SDKException("cann't get sdkmode mcs conf because IConfigCenterClient is null");
        }
        // 获取mcs namespace映射信息
        String cacheNSConfStr="";
		try {
			cacheNSConfStr = configClient.get(
			        SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH);
		} catch (ConfigException e) {
			LOG.error("error！cann't get sdkmode mcs cachens conf from path["
                    + SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH + "]",e);
			throw new SDKException("error！cann't get sdkmode mcs cachens conf from path["
                    + SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH + "]");
		}
        if (StringUtil.isBlank(cacheNSConfStr)) {
            throw new SDKException("cann't get sdkmode mcs cachens conf from path["
                    + SDKConstants.PAAS_CACHENS_MCS_MAPPED_PATH + "]");
        }
        // 转换为JSON对象
        JSONObject cacheNSJson = JSONObject.parseObject(cacheNSConfStr);
        //namespace对应的redis服务标识，如MCS001，MCS002
		String mcsId=cacheNSJson.getString(namespace);
		if(StringUtil.isBlank(mcsId)){
			throw new SDKException("cann't get sdkmoe mcsId of namespace["
                    + namespace + "]");
		}
		// 获取redis集群配置信息
		String redisConfStr="";
		try {
			redisConfStr = configClient.get(SDKConstants.SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH);
		} catch (ConfigException e) {
			LOG.error("error！cann't get sdkmode mcs redis info from path["
                    + SDKConstants.SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH + "]",e);
			throw new SDKException("error！cann't get sdkmode mcs redis info from path["
                    + SDKConstants.SDK_MODE_PAAS_MCS_REDIS_MAPPED_PATH + "]");
		}
		
		if(StringUtil.isBlank(redisConfStr)){
			throw new SDKException("cann't get sdkmode mcs redis conf of namespace["
                    + namespace + "],mcsId["+mcsId+"]");
		}
		
		JSONObject redisConfJson = JSONObject.parseObject(redisConfStr);
		JSONObject redisJson=(JSONObject) redisConfJson.get(mcsId);
		mcsProperties.put(MCSConstants.MCS_HOST, redisJson.get(MCSConstants.MCS_HOST));
		mcsProperties.put(MCSConstants.MCS_MAXTOTAL, redisJson.get(MCSConstants.MCS_MAXTOTAL));
        mcsProperties.put(MCSConstants.MCS_MAXIDLE, redisJson.get(MCSConstants.MCS_MAXIDLE));
        mcsProperties.put(MCSConstants.MCS_MINIDLE, redisJson.get(MCSConstants.MCS_MINIDLE));
        mcsProperties.put(MCSConstants.MCS_TESTONBORROW, redisJson.get(MCSConstants.MCS_TESTONBORROW));
        mcsProperties.put(MCSConstants.MCS_PASSWORD, redisJson.get(MCSConstants.MCS_PASSWORD));
		
		return mcsProperties;
	}
    
    public static Properties assembleMdsSenderProperties(String namespace) {
		Properties mdsSenderProperties=new Properties();
		IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        if (configClient == null) {
            throw new SDKException("cann't get sdkmode mds conf because IConfigClient is null");
        }
        // 获取mds namespace映射信息
        String mdsNSConfStr="";
		try {
			mdsNSConfStr = configClient.get(
			        SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH);
		} catch (ConfigException e) {
			LOG.error("error！cann't get sdkmode mds mdsns conf from path["
                    + SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH + "]",e);
			throw new SDKException("error！cann't get sdkmode mds mdsns conf from path["
                    + SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH + "]");
		}
        if (StringUtil.isBlank(mdsNSConfStr)) {
            throw new SDKException("cann't get sdkmode mds mdsns conf from path["
                    + SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH + "]");
        }
        // 转换为JSON对象
        JSONObject mdsNSJson = JSONObject.parseObject(mdsNSConfStr);
        //namespace对应的redis服务标识，如MDS001，MDS002
		String mdsId=mdsNSJson.getString(namespace);
		if(StringUtil.isBlank(mdsId)){
			throw new SDKException("cann't get sdkmoe mdsId of namespace["
                    + namespace + "]");
		}
		// 获取kafka集群配置信息
		String kafkaConfStr="";
		try {
			kafkaConfStr = configClient.get(SDKConstants.SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH);
		} catch (ConfigException e) {
			LOG.error("error！cann't get sdkmode mds sender kafka info from path["
                    + SDKConstants.SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH + "]",e);
			throw new SDKException("error！cann't get sdkmode mds sender kafka info from path["
                    + SDKConstants.SDK_MODE_PAAS_MDS_SENDER_MAPPED_PATH + "]");
		}
		
		if(StringUtil.isBlank(kafkaConfStr)){
			throw new SDKException("cann't get sdkmode mds sender kafka conf of namespace["
                    + namespace + "],mdsId["+mdsId+"]");
		}
		
		JSONObject kafkaConfJson = JSONObject.parseObject(kafkaConfStr);
		JSONObject kafkaJson=(JSONObject) kafkaConfJson.get(mdsId);
		mdsSenderProperties.put(MDSSenderConstants.METADATA_BROKER_LIST, kafkaJson.get(MDSSenderConstants.METADATA_BROKER_LIST));
		mdsSenderProperties.put(MDSSenderConstants.SERIALIZER_CLASS, kafkaJson.get(MDSSenderConstants.SERIALIZER_CLASS));
        mdsSenderProperties.put(MDSSenderConstants.KEY_SERIAL_CLASS, kafkaJson.get(MDSSenderConstants.KEY_SERIAL_CLASS));
        mdsSenderProperties.put(MDSSenderConstants.PARTITIONER_CLASS, kafkaJson.get(MDSSenderConstants.PARTITIONER_CLASS));
        mdsSenderProperties.put(MDSSenderConstants.REQUEST_REQUIRED_ACKS, kafkaJson.get(MDSSenderConstants.REQUEST_REQUIRED_ACKS));
        mdsSenderProperties.put(MDSSenderConstants.QUEUE_BUFFERING_MAX_MESSAGES, kafkaJson.get(MDSSenderConstants.QUEUE_BUFFERING_MAX_MESSAGES));
        mdsSenderProperties.put(MDSSenderConstants.PRODUCER_TYPE, kafkaJson.get(MDSSenderConstants.PRODUCER_TYPE));
        mdsSenderProperties.put(MDSSenderConstants.MESSAGE_SEND_MAX_RETRIES, kafkaJson.get(MDSSenderConstants.MESSAGE_SEND_MAX_RETRIES));
        mdsSenderProperties.put(MDSSenderConstants.COMPRESSION_CODEC, kafkaJson.get(MDSSenderConstants.COMPRESSION_CODEC));
        mdsSenderProperties.put(MDSSenderConstants.REQUEST_TIMEOUT_MS, kafkaJson.get(MDSSenderConstants.REQUEST_TIMEOUT_MS));
        mdsSenderProperties.put(MDSSenderConstants.BATCH_NUM_MESSAGES, kafkaJson.get(MDSSenderConstants.BATCH_NUM_MESSAGES));
        mdsSenderProperties.put(MDSSenderConstants.SEND_BUFFER_BYTES, kafkaJson.get(MDSSenderConstants.SEND_BUFFER_BYTES));
        mdsSenderProperties.put(MDSSenderConstants.MAXPRODUCER, kafkaJson.get(MDSSenderConstants.MAXPRODUCER));
        mdsSenderProperties.put(MDSSenderConstants.MDS_TOPIC, kafkaJson.get(MDSSenderConstants.MDS_TOPIC));
		
		return mdsSenderProperties;
	}
    public static Properties assembleMdsConsumerProperties(String namespace) {
    	Properties mdsConsumerProperties=new Properties();
    	IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
    	if (configClient == null) {
    		throw new SDKException("cann't get sdkmode mds conf because IConfigClient is null");
    	}
    	// 获取mds namespace映射信息
    	String mdsNSConfStr="";
    	try {
    		mdsNSConfStr = configClient.get(
    				SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH);
    	} catch (ConfigException e) {
    		LOG.error("error！cann't get sdkmode mds mdsns conf from path["
    				+ SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH + "]",e);
    		throw new SDKException("error！cann't get sdkmode mds mdsns conf from path["
    				+ SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH + "]");
    	}
    	if (StringUtil.isBlank(mdsNSConfStr)) {
    		throw new SDKException("cann't get sdkmode mds mdsns conf from path["
    				+ SDKConstants.PAAS_MDSNS_MDS_MAPPED_PATH + "]");
    	}
    	// 转换为JSON对象
    	JSONObject mdsNSJson = JSONObject.parseObject(mdsNSConfStr);
    	//namespace对应的redis服务标识，如MDS001，MDS002
    	String mdsId=mdsNSJson.getString(namespace);
    	if(StringUtil.isBlank(mdsId)){
    		throw new SDKException("cann't get sdkmoe mdsId of namespace["
    				+ namespace + "]");
    	}
    	// 获取kafka集群配置信息
    	String kafkaConfStr="";
    	try {
    		kafkaConfStr = configClient.get(SDKConstants.SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH);
    	} catch (ConfigException e) {
    		LOG.error("error！cann't get sdkmode mds consumer kafka info from path["
    				+ SDKConstants.SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH + "]",e);
    		throw new SDKException("error！cann't get sdkmode mds consumer kafka info from path["
    				+ SDKConstants.SDK_MODE_PAAS_MDS_CONSUMER_MAPPED_PATH + "]");
    	}
    	
    	if(StringUtil.isBlank(kafkaConfStr)){
    		throw new SDKException("cann't get sdkmode mds consumer kafka conf of namespace["
    				+ namespace + "],mdsId["+mdsId+"]");
    	}
    	
    	JSONObject kafkaConfJson = JSONObject.parseObject(kafkaConfStr);
    	JSONObject kafkaJson=(JSONObject) kafkaConfJson.get(mdsId);
    	mdsConsumerProperties.put(MDSConsumerConstants.KAFKA_ZOOKEEPER_HOSTS, kafkaJson.get(MDSConsumerConstants.KAFKA_ZOOKEEPER_HOSTS));
    	mdsConsumerProperties.put(MDSConsumerConstants.KAFKA_ZOOKEEPER_BROKER_PATH, kafkaJson.get(MDSConsumerConstants.KAFKA_ZOOKEEPER_BROKER_PATH));
    	mdsConsumerProperties.put(MDSConsumerConstants.KAFKA_ZOOKEEPER_USER, kafkaJson.get(MDSConsumerConstants.KAFKA_ZOOKEEPER_USER));
    	mdsConsumerProperties.put(MDSConsumerConstants.KAFKA_ZOOKEEPER_USER_PASSWD, kafkaJson.get(MDSConsumerConstants.KAFKA_ZOOKEEPER_USER_PASSWD));
    	mdsConsumerProperties.put(MDSConsumerConstants.MDS_CONSUMER_BASE_PATH, kafkaJson.get(MDSConsumerConstants.MDS_CONSUMER_BASE_PATH));
    	mdsConsumerProperties.put(MDSConsumerConstants.MDS_ZOOKEEPER_HOSTS, kafkaJson.get(MDSConsumerConstants.MDS_ZOOKEEPER_HOSTS));
    	mdsConsumerProperties.put(MDSConsumerConstants.MDS_TOPIC, kafkaJson.get(MDSConsumerConstants.MDS_TOPIC));
    	
    	return mdsConsumerProperties;
    }
    
    public static Properties assembleDssProperties(String namespace) {
		Properties dssProperties=new Properties();
		IConfigClient configClient = CCSClientFactory.getDefaultConfigClient();
        if (configClient == null) {
            throw new SDKException("cann't get sdkmode dss conf because IConfigClient is null");
        }
        // 获取dss namespace映射信息
        String dssNSConfStr="";
		try {
			dssNSConfStr = configClient.get(
			        SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH);
		} catch (ConfigException e) {
			LOG.error("error！cann't get sdkmode dss dssns conf from path["
                    + SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH + "]",e);
			throw new SDKException("error！cann't get sdkmode dss dssns conf from path["
                    + SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH + "]");
		}
        if (StringUtil.isBlank(dssNSConfStr)) {
            throw new SDKException("cann't get sdkmode dss dssns conf from path["
                    + SDKConstants.PAAS_DSSNS_DSS_MAPPED_PATH + "]");
        }
        // 转换为JSON对象
        JSONObject dssNSJson = JSONObject.parseObject(dssNSConfStr);
        //namespace对应的redis服务标识，如MCS001，MCS002
		String dssId=dssNSJson.getString(namespace);
		if(StringUtil.isBlank(dssId)){
			throw new SDKException("cann't get sdkmoe dssId of namespace["
                    + namespace + "]");
		}
		// 获取dss集群配置信息
		String dssConfStr="";
		try {
			dssConfStr = configClient.get(SDKConstants.SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH);
		} catch (ConfigException e) {
			LOG.error("error！cann't get sdkmode dss mongodb info from path["
                    + SDKConstants.SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH + "]",e);
			throw new SDKException("error！cann't get sdkmode dss mongodb info from path["
                    + SDKConstants.SDK_MODE_PAAS_DSS_MONGO_MAPPED_PATH + "]");
		}
		
		if(StringUtil.isBlank(dssConfStr)){
			throw new SDKException("cann't get sdkmode dss mongodb conf of namespace["
                    + namespace + "],dssId["+dssId+"]");
		}
		
		JSONObject redisConfJson = JSONObject.parseObject(dssConfStr);
		JSONObject redisJson=(JSONObject) redisConfJson.get(dssId);
		dssProperties.put(DSSConsants.MONGOSERVER, redisJson.get(DSSConsants.MONGOSERVER));
		dssProperties.put(DSSConsants.DATABASE, redisJson.get(DSSConsants.DATABASE));
        dssProperties.put(DSSConsants.USERNAME, redisJson.get(DSSConsants.USERNAME));
        dssProperties.put(DSSConsants.PASSWORD, redisJson.get(DSSConsants.PASSWORD));
        dssProperties.put(DSSConsants.BUCKET, redisJson.get(DSSConsants.BUCKET));
		
		return dssProperties;
	}

}
