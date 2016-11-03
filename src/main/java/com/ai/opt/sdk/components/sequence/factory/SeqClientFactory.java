package com.ai.opt.sdk.components.sequence.factory;

import com.ai.opt.sdk.components.sequence.client.ISeqClient;
import com.ai.opt.sdk.components.sequence.client.impl.NormalSeqClientImpl;

public final class SeqClientFactory {

    private SeqClientFactory() {

    }

    private static ISeqClient sequenceClient;

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
