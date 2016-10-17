package com.ai.opt.sdk.components.lock.zklock;


import java.util.concurrent.TimeUnit;

import org.apache.curator.framework.recipes.locks.InterProcessLock;

import com.ai.opt.sdk.components.lock.AbstractMutexLock;
/**
 * 基于ZK实现的分布式锁
 *
 * Date: 2016年10月2日 <br>
 * Copyright (c) 2016 guchuanlong@126.com <br>
 * @author gucl
 */
public class ZKMutexLock extends AbstractMutexLock
{
  private InterProcessLock processLock;

  public ZKMutexLock(InterProcessLock processLock)
  {
    this.processLock = processLock;
  }

  @Override
  public void acquire() throws Exception {
    this.processLock.acquire();
  }
  
  @Override
  public boolean acquire(long time, TimeUnit unit) throws Exception {
    return this.processLock.acquire(time, unit);
  }

  @Override
  public void release() throws Exception {
    this.processLock.release();
  }

  @Override
  public boolean isAcquiredInThisProcess() {
    return this.processLock.isAcquiredInThisProcess();
  }
}