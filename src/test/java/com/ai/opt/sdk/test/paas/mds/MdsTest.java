package com.ai.opt.sdk.test.paas.mds;

import java.util.ArrayList;
import java.util.List;

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
		int partNum=msgSender.getParititions();
		System.out.println("partNum="+partNum);
		for(int i=0;i<5;i++){
			int part=0;
			//sdkmode 下目前获取不到分区数，故做此处理
			if(partNum>0){
				part=i%partNum;
			}
			msgSender.send("[test-baas-bmc-topic-msg:"+i+"]This is a test message……", part);//第二个参数为分区键，如果不分区，传入0
			System.out.println("sender---[test-baas-bmc-topic-msg:"+i+"]This is a test message……");
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
