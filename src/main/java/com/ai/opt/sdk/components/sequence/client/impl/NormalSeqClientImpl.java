package com.ai.opt.sdk.components.sequence.client.impl;

import com.ai.opt.sdk.components.sequence.client.ISeqClient;
import com.ai.opt.sdk.components.sequence.service.ISequenceService;
import com.ai.opt.sdk.components.sequence.service.impl.SequenceServiceImpl;

public class NormalSeqClientImpl implements ISeqClient {

    private ISequenceService sequenceService;

    public NormalSeqClientImpl() {
        this.sequenceService = new SequenceServiceImpl();
    }

    @Override
    public Long nextValue(String sequenceName) {
        return sequenceService.nextValue(sequenceName);
    }

}
