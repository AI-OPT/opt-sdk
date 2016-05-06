package com.ai.opt.sdk.components.sequence.datasource;

import com.zaxxer.hikari.HikariDataSource;

public class SeqDataSourceLoader {

    private HikariDataSource ds;

    public void init() {
        SeqDataSourceLoaderFactory.init(this);
    }

    public HikariDataSource getDs() {
        return ds;
    }

    public void setDs(HikariDataSource ds) {
        this.ds = ds;
    }

}
