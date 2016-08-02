package com.ai.opt.sdk.components.sequence.datasource;

import javax.sql.DataSource;

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
