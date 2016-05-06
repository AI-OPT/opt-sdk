package com.ai.opt.sdk.test.paas.mds;

import com.ai.paas.ipaas.mds.IMessageProcessor;
import com.ai.paas.ipaas.mds.vo.MessageAndMetadata;

public class MessageProcessorImpl implements IMessageProcessor{

	@Override
	public void process(MessageAndMetadata message) throws Exception {
		// TODO Auto-generated method stub
		if (null != message) {
			System.out.println("------Topic:" + message.getTopic() + ",key:"
					+ new String(message.getKey(), "UTF-8") + ",content:"
					+ new String(message.getMessage(), "UTF-8"));
		}

	}

}
