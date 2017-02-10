package com.ai.opt.sdk.components.sequence.factory;

import com.ai.opt.sdk.components.sequence.client.ISeqClient;
import com.ai.opt.sdk.components.sequence.client.impl.NormalSeqClientImpl;

/**
 * SeqClientFactory
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class SeqClientFactory {

    private SeqClientFactory() {

    }

    private static ISeqClient sequenceClient;

    /**
     * 获取客户端
     * @return
     * @author
     */
    public static ISeqClient getSeqClient() {
        if (sequenceClient == null) {
        	synchronized(SeqClientFactory.class){
        		if(sequenceClient == null){
        			sequenceClient = new NormalSeqClientImpl();
        		}        		
        	}
        }
        return sequenceClient;
    }
}
