package com.ai.opt.sdk.sequence.normal.datasource;

import com.ai.opt.sdk.exception.OptSDKException;

public final class SeqDataSourceLoaderFactory {
    
    private SeqDataSourceLoaderFactory(){
        
    }

    private static SeqDataSourceLoader dsLoader;

    public static void init(SeqDataSourceLoader loader) {
        dsLoader = loader;
    }

    public static SeqDataSourceLoader getSeqDsLoader() {
        if (dsLoader == null) {
            throw new OptSDKException("未初始化SEQ数据源");
        }
        return dsLoader;
    }

}
