package com.ai.opt.sdk.components.sequence.util;

import com.ai.opt.sdk.components.sequence.factory.SeqClientFactory;
import com.ai.paas.ipaas.util.StringUtil;

/**
 * sequence工具类
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class SeqUtil {

    private SeqUtil() {

    }

    /**
     * 获取下一个Id
     * @param seqName
     * @return
     * @author
     */
    public static Long getNewId(String seqName) {
        return SeqClientFactory.getSeqClient().nextValue(seqName);
    }

    /**
     * 获取新的sequence
     * @param seqName
     * @param seqLen
     * @return
     * @author
     */
    public static String getNewId(String seqName, int seqLen) {
        Long newId = getNewId(seqName);
        String seqStr = StringUtil.toString(newId);
        while (seqStr.length() < seqLen) {
            seqStr = "0000000" + seqStr;
        }
        return seqStr.substring(seqStr.length() - seqLen);
    }

}
