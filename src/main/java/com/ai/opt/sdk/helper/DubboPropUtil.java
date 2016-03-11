package com.ai.opt.sdk.helper;

import java.util.Iterator;

import net.sf.json.JSONObject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.tools.PaaSServiceTool;
import com.ai.paas.ipaas.util.StringUtil;

public class DubboPropUtil {

    private static final Logger LOG = LogManager.getLogger(DubboPropUtil.class);

    // 是否启用本地配置文件
    private static final String LOCAL_CONFIG = "true";

    @SuppressWarnings("rawtypes")
    public static void setDubboRestProviderProperties() {
        String local = OptConfHelper.getInstance().getPropValue(
                "dubbo.rest.provider.userlocalconfig");
        if (LOG.isDebugEnabled()) {
            LOG.debug("当前dubbo.rest.provider使用本地的服务配置:" + local);
        }
        if (LOCAL_CONFIG.equals(local)) {
            return;
        }
        JSONObject props = PaaSServiceTool.getDubboRestProviderConf();
        if (props == null) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("获取到的DUBBO-REST-PROVIDER的配置信息:" + props.toString());
        }
        Iterator iter = props.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = props.getString(key);
            if (!StringUtil.isBlank(key)) {
                if (StringUtil.isBlank(value)) {
                    throw new OptSDKException("cannt get value for key[" + key + "] from ["
                            + SDKConstants.DUBBO_REST_PROVIDER_CONF_PATH + "]");
                }
                System.setProperty(key.trim(), value);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static void setDubboProviderProperties() {
        String local = OptConfHelper.getInstance()
                .getPropValue("dubbo.provider.userlocalconfig");
        if (LOG.isDebugEnabled()) {
            LOG.debug("当前dubbo.provider使用本地的服务配置:" + local);
        }
        if (LOCAL_CONFIG.equals(local)) {
            return;
        }
        JSONObject props = PaaSServiceTool.getDubboProviderConf();
        if (props == null) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("获取到的DUBBO-PROVIDER的配置信息:" + props.toString());
        }
        Iterator iter = props.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = props.getString(key);
            if (!StringUtil.isBlank(key)) {
                if (StringUtil.isBlank(value)) {
                    throw new OptSDKException("cannt get value for key[" + key + "] from ["
                            + SDKConstants.DUBBO_PROVIDER_CONF_PATH + "]");
                }
                System.setProperty(key.trim(), value);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static void setDubboConsumerProperties() {
        String local = OptConfHelper.getInstance()
                .getPropValue("dubbo.consumer.userlocalconfig");
        if (LOG.isDebugEnabled()) {
            LOG.debug("当前dubbo.consumer使用本地的服务配置:" + local);
        }
        if (LOCAL_CONFIG.equals(local)) {
            return;
        }
        JSONObject props = PaaSServiceTool.getDubboConsumerConf();
        if (props == null) {
            return;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("获取到的DUBBO-CONSUMER的配置信息:" + props.toString());
        }
        Iterator iter = props.keys();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = props.getString(key);
            if (!StringUtil.isBlank(key)) {
                if (StringUtil.isBlank(value)) {
                    throw new OptSDKException("cannt get value for key[" + key + "] from ["
                            + SDKConstants.DUBBO_CONSUMER_CONF_PATH + "]");
                }
                System.setProperty(key.trim(), value);
            }
        }
    }

}
