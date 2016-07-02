package com.ai.opt.sdk.components.mds;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mds.constants.MDSConsumerConstants;
import com.ai.opt.sdk.components.mds.constants.MDSSenderConstants;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.constants.SDKConstants;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;
import com.ai.paas.ipaas.mds.MsgConsumerCmpFactory;
import com.ai.paas.ipaas.mds.MsgConsumerFactory;
import com.ai.paas.ipaas.mds.MsgSenderCmpFactory;
import com.ai.paas.ipaas.mds.MsgSenderFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;
import com.alibaba.fastjson.JSON;

public final class MDSClientFactory {
	private static final Logger LOG = LoggerFactory.getLogger(MDSClientFactory.class);
	private static Map<String, IMessageSender> sendMap_serviceMode = new ConcurrentHashMap<String, IMessageSender>();
	private static Map<String, IMessageConsumer> recvMap_serviceMode = new ConcurrentHashMap<String, IMessageConsumer>();
	private static Map<String, IMessageSender> sendMap_sdkMode = new ConcurrentHashMap<String, IMessageSender>();
	private static Map<String, IMessageConsumer> recvMap_sdkMode = new ConcurrentHashMap<String, IMessageConsumer>();

    private MDSClientFactory() {

    }

    public static IMessageSender getSenderClient(String mdsns) {
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
    	if(StringUtil.isBlank(authInfo.getPaasSdkMode())||SDKConstants.PAASMODE.PAAS_SERVICE_MODE.equals(authInfo.getPaasSdkMode())){
    		return getSenderClientByServiceMode(mdsns);
    	}
    	else{
    		return getSenderClientBySdkMode(mdsns);
    	}
    }
    public static IMessageConsumer getConsumerClient(String mdsns, IMsgProcessorHandler msgProcessorHandler){
    	PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
    	if(StringUtil.isBlank(authInfo.getPaasSdkMode())||SDKConstants.PAASMODE.PAAS_SERVICE_MODE.equals(authInfo.getPaasSdkMode())){
    		return getConsumerClientByServiceMode(mdsns, msgProcessorHandler);
    	}
    	else{
    		return getConsumerClientBySdkMode(mdsns, msgProcessorHandler);
    	}
    }

	private static IMessageSender getSenderClientByServiceMode(String mdsns) {
		if (StringUtil.isBlank(mdsns)) {
            throw new SDKException("请输入消息服务配置映射的常量标识");
        }
        String mdsId = ConfigTool.getMDSId(mdsns);
        String mdsPwd = ConfigTool.getServicePwd(mdsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), mdsPwd, mdsId);
        String keyId=authInfo.getPid()+"."+mdsId;
        IMessageSender client;
        try {
        	if (!sendMap_serviceMode.containsKey(keyId)) {
        		client = MsgSenderFactory.getClient(authDescriptor);
    			sendMap_serviceMode.put(keyId, client);
    		}
        	else{
        		client=sendMap_serviceMode.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取消息服务[" + mdsId + "]对应的客户端实例", e);
        }
        return client;
	}
    
	private static IMessageConsumer getConsumerClientByServiceMode(String mdsns,
			IMsgProcessorHandler msgProcessorHandler) {
		if (StringUtil.isBlank(mdsns)) {
            throw new SDKException("请输入消息服务配置映射的常量标识");
        }
        String mdsId = ConfigTool.getMDSId(mdsns);
        String mdsPwd = ConfigTool.getServicePwd(mdsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), mdsPwd, mdsId);
        String keyId=authInfo.getPid()+"."+mdsId;
        IMessageConsumer client;
        try {
        	if (!recvMap_serviceMode.containsKey(keyId)) {
        		client = MsgConsumerFactory.getClient(authDescriptor, msgProcessorHandler);
        		recvMap_serviceMode.put(keyId, client);
    		}
        	else{
        		client=recvMap_serviceMode.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取消息服务[" + mdsId + "]对应的客户端实例", e);
        }
        return client;
	}
	private static IMessageSender getSenderClientBySdkMode(String mdsns) {
		if (StringUtil.isBlank(mdsns)) {
            throw new SDKException("请输入消息服务配置映射的常量标识");
        }
        String mdsId = ConfigTool.getMDSId(mdsns);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String appname = authInfo.getCcsAppName();
		LOG.debug("authInfo="+JSON.toJSONString(authInfo));
		Properties kafkaSenderProp=ConfigTool.assembleMdsSenderProperties(mdsns);
		String topicId=kafkaSenderProp.getProperty(MDSSenderConstants.MDS_TOPIC);
        String keyId=appname+"."+mdsId;
        IMessageSender client;
        try {
        	if (!sendMap_sdkMode.containsKey(keyId)) {
        		client = MsgSenderCmpFactory.getClient(kafkaSenderProp,topicId);
        		sendMap_sdkMode.put(keyId, client);
    		}
        	else{
        		client=sendMap_sdkMode.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取消息服务[" + mdsId + "]对应的客户端实例", e);
        }
        return client;
	}
    
	private static IMessageConsumer getConsumerClientBySdkMode(String mdsns,
			IMsgProcessorHandler msgProcessorHandler) {
		if (StringUtil.isBlank(mdsns)) {
			throw new SDKException("请输入消息服务配置映射的常量标识");
		}
		String mdsId = ConfigTool.getMDSId(mdsns);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        String appname = authInfo.getCcsAppName();
		LOG.debug("authInfo="+JSON.toJSONString(authInfo));
		Properties kafkaConsumerProp=ConfigTool.assembleMdsConsumerProperties(mdsns);
		String topicId=kafkaConsumerProp.getProperty(MDSConsumerConstants.MDS_TOPIC);
        String keyId=appname+"."+mdsId;
		
		IMessageConsumer client;
		try {
			if (!recvMap_sdkMode.containsKey(keyId)) {
				client = MsgConsumerCmpFactory.getClient(kafkaConsumerProp,topicId, msgProcessorHandler);
				recvMap_sdkMode.put(keyId, client);
			}
			else{
				client=recvMap_sdkMode.get(keyId);
			}
		} catch (Exception e) {
			throw new SDKException("无法获取消息服务[" + mdsId + "]对应的客户端实例", e);
		}
		return client;
	}

}
