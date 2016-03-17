package com.ai.opt.sdk.datasource;

import com.ai.opt.sdk.helper.OptConfHelper;
import com.zaxxer.hikari.HikariDataSource;

public class OptHikariDataSource extends HikariDataSource {

	public OptHikariDataSource(String dataSourceName) {
		//从paas中获取数据库配置信息，并初始化数据源
		super(OptConfHelper.getInstance().getDBConf(dataSourceName));
	}
	

}
