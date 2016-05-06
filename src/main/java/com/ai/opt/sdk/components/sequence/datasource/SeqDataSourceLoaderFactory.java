package com.ai.opt.sdk.components.sequence.datasource;

import com.ai.opt.sdk.exception.SDKException;

public final class SeqDataSourceLoaderFactory {
    
    private SeqDataSourceLoaderFactory(){
        
    }

    private static SeqDataSourceLoader dsLoader;

    public static void init(SeqDataSourceLoader loader) {
        dsLoader = loader;
    }

    public static SeqDataSourceLoader getSeqDsLoader() {
        if (dsLoader == null) {
            throw new SDKException("未初始化SEQ数据源");
        }
        return dsLoader;
    }

}
