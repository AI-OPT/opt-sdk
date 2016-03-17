package com.ai.opt.sdk.sequence.factory;

import com.ai.opt.sdk.sequence.client.ISeqClient;
import com.ai.opt.sdk.sequence.client.impl.NormalSeqClientImpl;

public final class SeqClientFactory {

    private SeqClientFactory() {

    }

    private static ISeqClient sequenceClient;

    public static ISeqClient getSeqClient() {
        if (sequenceClient == null) {
                sequenceClient = new NormalSeqClientImpl();
        }
        return sequenceClient;
    }
}
