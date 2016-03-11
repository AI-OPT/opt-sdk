package com.ai.opt.sdk.tools;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import com.ai.opt.sdk.configcenter.builder.IConfigCenterBuilder;
import com.ai.opt.sdk.configcenter.factory.ConfigCenterBuilderFactory;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariConfig;

public final class PaaSServiceTool {

    private PaaSServiceTool() {
        // 私有构造函数，不运行此类被实例化
    }

    public static String getMCSPwd(String mcsId) {
        return getPaaSServicePwd(mcsId, SDKConstants.MCS);

    }

    public static String getTXSPwd(String txsId) {
        return getPaaSServicePwd(txsId, SDKConstants.TXS);
    }

    public static String getDBSPwd(String dbsId) {
        return getPaaSServicePwd(dbsId, SDKConstants.DBS);
    }

    public static String getDSSPwd(String dssId) {
        return getPaaSServicePwd(dssId, SDKConstants.DSS);
    }

    public static String getSESPwd(String sesId) {
        return getPaaSServicePwd(sesId, SDKConstants.SES);
    }

    public static String getTXSId() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get paas txs id  because IConfigCenterBuilder is null");
        }
        String txsId = configCenterBuilder.getConfigCenterClient().get(SDKConstants.PAAS_TXS_PATH);
        if (StringUtil.isBlank(txsId)) {
            throw new OptSDKException("cann't get paas txs id from path["
                    + SDKConstants.PAAS_TXS_PATH + "]");
        }
        return txsId;
    }

    public static String getDBSId() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get paas dbs id  because IConfigCenterBuilder is null");
        }
        String dbsId = configCenterBuilder.getConfigCenterClient().get(SDKConstants.PAAS_DBS_PATH);
        if (StringUtil.isBlank(dbsId)) {
            throw new OptSDKException("cann't get paas dbs id from path["
                    + SDKConstants.PAAS_DBS_PATH + "]");
        }
        return dbsId;
    }

    public static HikariConfig getDBConf(String dataSourceName) {
        HikariConfig dbconf = null;
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get database conf because IConfigCenterBuilder is null");
        }
        // 获取配置信息字符串
        String dbConfStr = configCenterBuilder.getConfigCenterClient().get(
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

    /**
     * 获取ES的配置信息
     * 
     * @return
     * @author zhangchao
     */
    public static JSONObject getESConf() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get elastic search conf because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.ELASTIC_SEARCH_CONF_PATH);
        if (StringUtil.isBlank(conf)) {
            throw new OptSDKException("cann't get elastic search conf from path["
                    + SDKConstants.ELASTIC_SEARCH_CONF_PATH + "]");
        }
        JSONObject data = JSONObject.fromObject(conf);
        return data;
    }

    public static String getATSSignatureId() {
        List<String> list = getATSSignatureIds();
        return list.get(0);
    }

    /**
     * 获取所有的ATS签名标识
     * 
     * @return
     * @author zhangchao
     */
    public static List<String> getATSSignatureIds() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get paas ats SignatureId  because IConfigCenterBuilder is null");
        }
        String signatureIds = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.PAAS_ATS_SIGNATUREID_PATH);
        if (StringUtil.isBlank(signatureIds)) {
            throw new OptSDKException("cann't get paas ats SignatureId from path["
                    + SDKConstants.PAAS_ATS_SIGNATUREID_PATH + "]");
        }
        return Arrays.asList(signatureIds.split(","));
    }

    /**
     * 获取ATS服务对应的ATS消息签名ID<br>
     * 如果没有配置，则取默认第一个签名ID<br>
     * 
     * @param interfaceClazz
     * @param method
     * @return
     * @author zhangchao
     */
    public static String getATSServiceSignatureId(String interfaceClazz, String method) {
        if (StringUtil.isBlank(interfaceClazz)) {
            throw new OptSDKException(
                    "cann't get paas ats SignatureId because interfaceClazz is null");
        }
        if (StringUtil.isBlank(method)) {
            throw new OptSDKException("cann't get paas ats SignatureId because method is null");
        }
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get paas ats SignatureId mapped because IConfigCenterBuilder is null");
        }
        String jsonString = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.PAAS_ATS_SERVICE_SIGNATUREID_MAPPED_PATH);
        String signatureId = getATSSignatureId();
        if (StringUtil.isBlank(jsonString)) {
            return signatureId;
        }
        JSONObject json = JSONObject.fromObject(jsonString);
        String key = interfaceClazz + "#" + method;
        return json.getString(key) == null ? signatureId : json.getString(key);
    }

    private static String getPaaSServicePwd(String serviceId, String serviceType) {
        if (StringUtil.isBlank(serviceId)) {
            throw new OptSDKException(
                    "cann't get paas service's password because serviceId is null");
        }
        if (StringUtil.isBlank(serviceType)) {
            throw new OptSDKException(
                    "cann't get paas service's password because serviceType is null");
        }
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get paas service's password  because IConfigCenterBuilder is null");
        }
        String confs = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + serviceType);
        if (StringUtil.isBlank(confs)) {
            throw new OptSDKException("cann't get paas service's passwordfrom path["
                    + SDKConstants.PAAS_SERVICE_PWD_MAPPED_PATH + serviceType + "]");
        }
        String password = new Gson().fromJson(confs, JsonObject.class).get(serviceId).getAsString();
        return password;
    }

    /**
     * 获取DUBBO-REST的提供者信息
     * 
     * @return
     * @author zhangchao
     * @ApiDocMethod
     */
    public static JSONObject getDubboRestProviderConf() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dubbo rest provider conf because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient().get(
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
     * @author zhangchao
     */
    public static JSONObject getDubboProviderConf() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dubbo provider conf because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient().get(
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
     * @author zhangchao
     */
    public static JSONObject getDubboConsumerConf() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dubbo consumer conf because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.DUBBO_CONSUMER_CONF_PATH);
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        JSONObject data = JSONObject.fromObject(conf);
        return data;
    }

    /**
     * 根据使用场景的命名空间获取DSS服务ID
     * 
     * @param namespaces
     * @return
     * @author zhangchao
     */
    public static String getDSSId(String namespaces) {
        if (StringUtil.isBlank(namespaces)) {
            throw new OptSDKException("cann't get paas dss serviceid because namespaces is null");
        }
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get paas dss serviceid because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.PAAS_NS_DSS_MAPPED_PATH);
        if (StringUtil.isBlank(conf)) {
            throw new OptSDKException("cann't get paas dss config from path["
                    + SDKConstants.PAAS_NS_DSS_MAPPED_PATH + "]");
        }
        JSONObject data = JSONObject.fromObject(conf);
        String dssId = data.getString(namespaces);
        if (StringUtil.isBlank(dssId)) {
            throw new OptSDKException("cann't get paas dss service id from path["
                    + SDKConstants.PAAS_NS_DSS_MAPPED_PATH + "] by key=" + namespaces);
        }
        return dssId;
    }

    /**
     * 从配置中心获取DTS的zk配置信息
     * 
     * @return
     * @author zhangchao
     */
    public static JSONObject getDTSZKConf() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dts zookeeper conf because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient()
                .get(SDKConstants.DTS_ZK_CONF_PATH);
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        JSONObject data = JSONObject.fromObject(conf);
        return data;
    }

    /**
     * 获取DTS的属性配置
     * 
     * @return
     * @author zhangchao
     */
    public static Properties getDTSQuartzProperties() {
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get dts quartz conf because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.DTS_QUARTZ_CONF_PATH);
        if (StringUtil.isBlank(conf)) {
            return null;
        }
        Properties p = new Properties();
        JSONObject data = JSONObject.fromObject(conf);
        for (@SuppressWarnings("rawtypes")
        Iterator iter = data.keys(); iter.hasNext();) {
            String key = (String) iter.next();
            String value = data.getString(key);
            p.put(key, value);
        }
        return p;
    }

    /**
     * 根据使用场景的命名空间获取DSS服务ID
     * 
     * @param namespaces
     * @return
     * @author gucl
     */
    public static String getSESId(String namespaces) {
        if (StringUtil.isBlank(namespaces)) {
            throw new OptSDKException("cann't get paas ses serviceid because namespaces is null");
        }
        IConfigCenterBuilder configCenterBuilder = ConfigCenterBuilderFactory
                .getConfigCenterBuilder();
        if (configCenterBuilder == null) {
            throw new OptSDKException(
                    "cann't get paas ses serviceid because IConfigCenterBuilder is null");
        }
        String conf = configCenterBuilder.getConfigCenterClient().get(
                SDKConstants.PAAS_NS_SES_MAPPED_PATH);
        if (StringUtil.isBlank(conf)) {
            throw new OptSDKException("cann't get paas ses config from path["
                    + SDKConstants.PAAS_NS_SES_MAPPED_PATH + "]");
        }
        JSONObject data = JSONObject.fromObject(conf);
        String sesId = data.getString(namespaces);
        if (StringUtil.isBlank(sesId)) {
            throw new OptSDKException("cann't get paas ses service id from path["
                    + SDKConstants.PAAS_NS_SES_MAPPED_PATH + "] by key=" + namespaces);
        }
        return sesId;
    }

}
