package com.ai.opt.sdk.components.sequence.datasource;

import com.ai.opt.sdk.exception.SDKException;

/**
 * 数据源加载器工厂
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
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
