package com.ai.opt.sdk.components.mds;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.components.util.ConfigTool;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;
import com.ai.paas.ipaas.mds.MsgConsumerFactory;
import com.ai.paas.ipaas.mds.MsgSenderFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;
import com.ai.paas.ipaas.util.StringUtil;

public final class MDSClientFactory {
	private static Map<String, IMessageSender> sendMap = new ConcurrentHashMap<String, IMessageSender>();
	private static Map<String, IMessageConsumer> recvMap = new ConcurrentHashMap<String, IMessageConsumer>();

    private MDSClientFactory() {

    }

    public static IMessageSender getSenderClient(String mdsns) {
        if (StringUtil.isBlank(mdsns)) {
            throw new SDKException("请输入缓存服务配置映射的常量标识");
        }
        String mdsId = ConfigTool.getMDSId(mdsns);
        String mdsPwd = ConfigTool.getServicePwd(mdsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), mdsPwd, mdsId);
        String keyId=authInfo.getPid()+"."+mdsId;
        IMessageSender client;
        try {
        	if (!sendMap.containsKey(keyId)) {
        		client = MsgSenderFactory.getClient(authDescriptor);
    			sendMap.put(keyId, client);
    		}
        	else{
        		client=sendMap.get(keyId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取缓存服务[" + mdsId + "]对应的客户端实例", e);
        }
        return client;
    }
    
    public static IMessageConsumer getConsumerClient(String mdsns, IMsgProcessorHandler msgProcessorHandler){
    	if (StringUtil.isBlank(mdsns)) {
            throw new SDKException("请输入缓存服务配置映射的常量标识");
        }
        String mdsId = ConfigTool.getMDSId(mdsns);
        String mdsPwd = ConfigTool.getServicePwd(mdsId);
        PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), mdsPwd, mdsId);
        
        IMessageConsumer client;
        try {
        	if (!recvMap.containsKey(mdsId)) {
        		client = MsgConsumerFactory.getClient(authDescriptor, msgProcessorHandler);
        		recvMap.put(mdsId, client);
    		}
        	else{
        		client=recvMap.get(mdsId);
        	}
        } catch (Exception e) {
            throw new SDKException("无法获取缓存服务[" + mdsId + "]对应的客户端实例", e);
        }
        return client;
    }

}
