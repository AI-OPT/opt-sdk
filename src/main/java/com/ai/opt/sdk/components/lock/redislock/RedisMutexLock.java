package com.ai.opt.sdk.components.lock.redislock;


import java.util.concurrent.TimeUnit;

import com.ai.opt.sdk.components.lock.AbstractMutexLock;
/**
 * 基于Redis实现的分布式锁
 *
 * Date: 2016年10月2日 <br>
 * Copyright (c) 2016 guchuanlong@126.com <br>
 * @author gucl
 */
public class RedisMutexLock extends AbstractMutexLock
{
  private String lockKey;
  private CacheInterProcessLock processLock;

  public RedisMutexLock(String lockKey,CacheInterProcessLock processLock)
  {
	  this.lockKey=lockKey;
	  this.processLock = processLock;
  }

  @Override
  public void acquire() throws Exception {
    this.processLock.lock(lockKey);
  }

  @Override
  public boolean acquire(long time, TimeUnit unit) throws Exception {
    return this.processLock.tryLock(lockKey, time, unit);
  }

  @Override
  public void release() throws Exception {
    this.processLock.unLock(lockKey);
  }

  @Override
  public boolean isAcquiredInThisProcess() {
    return this.processLock.isAcquiredInThisProcess();
  }
}