package com.ai.opt.sdk.components.lock;

import java.util.concurrent.TimeUnit;

/**
 * AbstractMutexLock
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public abstract class AbstractMutexLock {
	  /**
	   *  争锁
	   * @throws Exception
	   * @ApiDocMethod
	   * @ApiCode 
	   * @RestRelativeURL
	   */
	  public abstract void acquire() throws Exception;
	  /**
	   * 争锁
	   * @param time 时间
	   * @param unit 时间单位
	   * @return
	   * @throws Exception
	   * @ApiDocMethod
	   * @ApiCode 
	   * @RestRelativeURL
	   */
	  public abstract boolean acquire(long time, TimeUnit unit) throws Exception;
	  /**
	   * 释放锁
	   * @throws Exception
	   * @author caofz
	   * @ApiDocMethod
	   * @ApiCode 
	   * @RestRelativeURL
	   */
	  public abstract void release() throws Exception;
	  public abstract boolean isAcquiredInThisProcess();
}
