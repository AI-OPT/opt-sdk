package com.ai.opt.sdk.components.mds.base;

/**
 * MDS消费者
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public abstract class AbstractMdsConsumer {
	/**
	 * 启动消费者
	 * @throws Exception
	 * @ApiDocMethod
	 * @ApiCode 
	 * @RestRelativeURL
	 */
	public abstract void startMdsConsumer() throws Exception;
}
