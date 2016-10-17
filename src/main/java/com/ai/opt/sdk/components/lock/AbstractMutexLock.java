package com.ai.opt.sdk.components.lock;

import java.util.concurrent.TimeUnit;

public abstract class AbstractMutexLock {
	  public abstract void acquire() throws Exception;
	  public abstract boolean acquire(long time, TimeUnit unit) throws Exception;
	  public abstract void release() throws Exception;
	  public abstract boolean isAcquiredInThisProcess();
}
