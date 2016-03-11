package com.ai.opt.sdk.sequence.client.impl;

import com.ai.opt.sdk.sequence.client.ISeqClient;
import com.ai.opt.sdk.sequence.normal.service.ISequenceService;
import com.ai.opt.sdk.sequence.normal.service.impl.SequenceServiceImpl;

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
