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
	  public abstract void acquire() throws Exception;
	  public abstract boolean acquire(long time, TimeUnit unit) throws Exception;
	  public abstract void release() throws Exception;
	  public abstract boolean isAcquiredInThisProcess();
}
