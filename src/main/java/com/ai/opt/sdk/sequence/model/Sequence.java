package com.ai.opt.sdk.sequence.model;

public class Sequence {

    private String sequenceName;

    private SequenceCache sequence;

    private Long value;

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    public SequenceCache getSequence() {
        return sequence;
    }

    public void setSequence(SequenceCache sequence) {
        this.sequence = sequence;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

}
