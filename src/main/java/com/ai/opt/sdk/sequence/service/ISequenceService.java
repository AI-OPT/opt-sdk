package com.ai.opt.sdk.sequence.service;

public interface ISequenceService {

    Long nextValue(String sequenceName);

    void modifySequence(String sequenceName, long nextVal);

}
