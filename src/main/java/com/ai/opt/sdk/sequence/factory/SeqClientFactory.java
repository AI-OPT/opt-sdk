package com.ai.opt.sdk.sequence.factory;

import com.ai.opt.sdk.exception.OptSDKException;
import com.ai.opt.sdk.helper.OptConfHelper;
import com.ai.opt.sdk.sequence.client.ISeqClient;
import com.ai.opt.sdk.sequence.client.impl.DBSSeqClientImpl;
import com.ai.opt.sdk.sequence.client.impl.NormalSeqClientImpl;

public final class SeqClientFactory {

    private SeqClientFactory() {

    }

    // 普通方式提供SEQ获取
    private static final String NORMAL_SEQ_TYPE = "normal_seq";

    // IPAAS的DBS提供SEQ获取
    private static final String IPAAS_DBS_SEQ_TYPE = "ipaas_dbs_seq";

    private static ISeqClient sequenceClient;

    public static ISeqClient getSeqClient() {
        if (sequenceClient == null) {
            String seqType = OptConfHelper.getInstance().getSequenceType();
            if (NORMAL_SEQ_TYPE.equals(seqType)) {
                sequenceClient = new NormalSeqClientImpl();
            } else if (IPAAS_DBS_SEQ_TYPE.equals(seqType)) {
                sequenceClient = new DBSSeqClientImpl();
            } else {
                throw new OptSDKException("not supported seq type[" + seqType + "]");
            }
        }
        return sequenceClient;
    }
}
