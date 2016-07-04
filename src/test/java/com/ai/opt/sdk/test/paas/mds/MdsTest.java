package com.ai.opt.sdk.test.paas.mds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.ai.opt.sdk.components.mds.MDSClientFactory;
import com.ai.paas.ipaas.mds.IMessageConsumer;
import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.IMessageSender;
import com.ai.paas.ipaas.mds.IMsgProcessorHandler;

public class MdsTest {

	@Test
	public void sendMsgTest(){
		String mdsns = "baas-bmc-topic";//
		IMessageSender msgSender = MDSClientFactory.getSenderClient(mdsns);
		//msgSender.getParititions() 方法废弃，以后不再调用
		for(int i=0;i<5;i++){
			//send方法的第二个参数为随机数，依据该随机数均匀往各个片区发送消息
			msgSender.send("[test-baas-bmc-topic-msg:"+i+"]This is a test message……", new Random(1000).nextLong());
			//System.out.println("sender---[test-baas-bmc-topic-msg:"+i+"]This is a test message……");
		}

	}
	@Test
	public void recvMsgTest(){
		String mdsns = "baas-bmc-topic";//
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
//		IMessageConsumer msgConsumer= MDSClientFactory.getConsumerClient(mdsns, msgProcessorHandler,"mds-consumer-mytopic1");
		IMessageConsumer msgConsumer= MDSClientFactory.getConsumerClient(mdsns, msgProcessorHandler,"mds-consumer-mytopic1111");
		msgConsumer.start();
		synchronized (MdsTest.class) {
			while (true) {
				try {
					MdsTest.class.wait();

				} catch (Exception e) {
					System.out.println("MDS 消费错误，具体信息为：" + e.getMessage());
				}
			}
		}
		
	}
}
