package com.ai.opt.sdk.components.sequence.datasource;

import javax.sql.DataSource;

/**
 * 数据源加载器
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class SeqDataSourceLoader {

    private DataSource ds;

    public void init() {
        SeqDataSourceLoaderFactory.init(this);
    }

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

}
