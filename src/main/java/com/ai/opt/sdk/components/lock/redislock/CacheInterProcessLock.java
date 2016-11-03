package com.ai.opt.sdk.components.lock.redislock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.paas.ipaas.PaasRuntimeException;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;
import com.google.common.collect.Maps;

public class CacheInterProcessLock {

		private static final Logger LOGGER = LoggerFactory.getLogger(CacheInterProcessLock.class);

		private static final int DEFAULT_SINGLE_EXPIRE_TIME = 3;
		
		private static final int DEFAULT_BATCH_EXPIRE_TIME = 6;

		private final ICacheClient cacheClient;
		
		private final ConcurrentMap<Thread, LockData> threadData = Maps.newConcurrentMap();
		
		private static class LockData
	    {
	        final Thread owningThread;
	        final String lockPath;
	        final AtomicInteger lockCount = new AtomicInteger(1);

	        private LockData(Thread owningThread, String lockPath)
	        {
	            this.owningThread = owningThread;
	            this.lockPath = lockPath;
	        }
	    }
		/**
		 * 构造
		 * @author gucl
		 */
		public CacheInterProcessLock(ICacheClient cacheClient) {
			this.cacheClient = cacheClient;
		}

		/**
		 * 获取锁  如果锁可用   立即返回true，  否则返回false
		 * @author gucl
		 * @param key
		 * @return
		 */
		public boolean tryLock(String key) {
			return tryLock(key, 0L, null);
		}

		/**
		 * 锁在给定的等待时间内空闲，则获取锁成功 返回true， 否则返回false
		 * @author gucl
		 * @param key
		 * @param timeout
		 * @param unit
		 * @return
		 */
		public boolean tryLock(String key, long timeout, TimeUnit unit) {
			Thread currentThread = Thread.currentThread();
			LockData lockData = threadData.get(currentThread);
	        if ( lockData != null )
	        {
	            // re-entering
	            lockData.lockCount.incrementAndGet();
	            return true;
	        }
			try {
				long nano = System.nanoTime();
				do {
					LOGGER.debug("try lock key: " + key);
					Long i = cacheClient.setnx(key, key);
					if (i == 1) { 
						cacheClient.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
						LOGGER.debug("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
						LockData newLockData = new LockData(currentThread, key);
				        threadData.put(currentThread, newLockData);
						return Boolean.TRUE;
					} else { // 存在锁
						if (LOGGER.isDebugEnabled()) {
							String desc = cacheClient.get(key);
							LOGGER.debug("key: " + key + " locked by another business：" + desc);
						}
					}
					if (timeout == 0) {
						break;
					}
					Thread.sleep(300);
				} while ((System.nanoTime() - nano) < unit.toNanos(timeout));
				return Boolean.FALSE;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				
			}
			return Boolean.FALSE;
		}

		/**
		 * 如果锁空闲立即返回   获取失败 一直等待
		 * @author gucl
		 * @param key
		 */
		public void lock(String key) {
			Thread currentThread = Thread.currentThread();
			LockData lockData = threadData.get(currentThread);
	        if ( lockData != null )
	        {
	            // re-entering
	            lockData.lockCount.incrementAndGet();
	            return;
	        }
			try {
				do {
					LOGGER.debug("lock key: " + key);
					Long i = cacheClient.setnx(key, key);
					if (i == 1) { 
						cacheClient.expire(key, DEFAULT_SINGLE_EXPIRE_TIME);
						LOGGER.debug("get lock, key: " + key + " , expire in " + DEFAULT_SINGLE_EXPIRE_TIME + " seconds.");
						LockData newLockData = new LockData(currentThread, key);
				        threadData.put(currentThread, newLockData);
						return;
					} else {
						if (LOGGER.isDebugEnabled()) {
							String desc = cacheClient.get(key);
							LOGGER.debug("key: " + key + " locked by another business：" + desc);
						}
					}
					Thread.sleep(300); 
				} while (true);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				
			}
		}

		/**
		 * 释放锁
		 * @author gucl
		 * @param key
		 */
		public void unLock(String key) {
	        Thread currentThread = Thread.currentThread();
	        LockData lockData = threadData.get(currentThread);
	        if ( lockData == null )
	        {
	            throw new PaasRuntimeException("You do not own the lock: " + key);
	        }

	        int newLockCount = lockData.lockCount.decrementAndGet();
	        if ( newLockCount > 0 )
	        {
	            return;
	        }
	        if ( newLockCount < 0 )
	        {
	            throw new PaasRuntimeException("Lock count has gone negative for lock: " + key);
	        }
	        
			List<String> list = new ArrayList<String>();
			list.add(key);
			unLock(list);
		}

		
		/**
		 * 批量释放锁
		 * @author gucl
		 * @param keyList
		 */
		public void unLock(List<String> keyList) {
			List<String> keys = new CopyOnWriteArrayList<String>();
			for (String key : keyList) {
				keys.add(key);
			}
			try {
				cacheClient.del(keys.toArray(new String[0]));
				LOGGER.debug("release lock, keys :" + keys);
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
			} finally {
				
			}
		}	
		
		public boolean isAcquiredInThisProcess() {
			 return (threadData.size() > 0);
		}
		public boolean isOwnedByCurrentThread()
	    {
	        LockData lockData = threadData.get(Thread.currentThread());
	        return (lockData != null) && (lockData.lockCount.get() > 0);
	    }
}