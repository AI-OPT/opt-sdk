package com.ai.opt.sdk.test.paas.mds;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ai.opt.sdk.appserver.DubboServiceStart;
import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;
import com.ai.paas.ipaas.mds.MsgConsumerFactory;
import com.ai.paas.ipaas.mds.MsgSenderFactory;
import com.ai.paas.ipaas.uac.vo.AuthDescriptor;

public class MdsRawTest {

	@Test
	public void sendMsgTest(){
		String mdsId = "MDS001";//服务申请分配的服务申请号
		String mdsPwd = "123456";//PaaS平台用户校验地址
		String topic="BCA976731EF24B899B143755A3AF5794_MDS001_1743120261";
		PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
        AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
                authInfo.getPid(), mdsPwd, mdsId);
		IMessageSender msgSender = MsgSenderFactory.getClient(authDescriptor, topic);
		for(int i=0;i<5;i++){
			int part=i%2;
			msgSender.send("[msg:"+i+"]This is a test message……", part);//第二个参数为分区键，如果不分区，传入0
			
		}

	}
	@Test
	public void recvMsgTest(){
		String mdsId = "MDS001";//服务申请分配的服务申请号
		String mdsPwd = "123456";//PaaS平台用户校验地址
		String topic="BCA976731EF24B899B143755A3AF5794_MDS001_1743120261";
		PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
		AuthDescriptor authDescriptor = new AuthDescriptor(authInfo.getAuthUrl(),
				authInfo.getPid(), mdsPwd, mdsId);
		IMsgProcessorHandler msgProcessorHandler=new IMsgProcessorHandler() {
			
			@Override
			public IMessageProcessor[] createInstances(int paramInt) {
				// TODO Auto-generated method stub
				
				List<IMessageProcessor> processors = new ArrayList<>();
				IMessageProcessor processor = null;
				for (int i = 0; i < paramInt; i++) {
					processor = new MessageProcessorImpl();
					processors.add(processor);
				}
				return processors.toArray(new IMessageProcessor[processors.size()]);

			}
		};
		IMessageConsumer msgConsumer= MsgConsumerFactory.getClient(authDescriptor, topic, msgProcessorHandler);
		msgConsumer.start();
		synchronized (MdsRawTest.class) {
			while (true) {
				try {
					MdsRawTest.class.wait();

				} catch (Exception e) {
					System.out.println("MDS 消费错误，具体信息为：" + e.getMessage());
				}
			}
		}
		
	}
}
