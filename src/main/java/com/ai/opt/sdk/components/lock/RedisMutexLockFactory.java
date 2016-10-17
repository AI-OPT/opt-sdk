package com.ai.opt.sdk.components.lock;

import com.ai.opt.sdk.components.lock.redislock.CacheInterProcessLock;
import com.ai.opt.sdk.components.lock.redislock.RedisLockConstants;
import com.ai.opt.sdk.components.lock.redislock.RedisMutexLock;
import com.ai.opt.sdk.components.mcs.MCSClientFactory;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.opt.sdk.util.StringUtil;
import com.ai.paas.ipaas.PaasException;
import com.ai.paas.ipaas.mcs.interfaces.ICacheClient;

public class RedisMutexLockFactory {
	
	public static AbstractMutexLock getRedisMutexLock(String namespace,String redisKey) throws PaasException{
		if(StringUtil.isBlank(namespace)){
			throw new SDKException("分布式锁对应的缓存命名空间不能为空");
        }
		try {
			ICacheClient cacheClient=MCSClientFactory.getCacheClient(namespace);
			CacheInterProcessLock cacheInterProcessLock=new CacheInterProcessLock(cacheClient);
			return new RedisMutexLock(redisKey, cacheInterProcessLock);
		} catch (Exception e) {
			throw new SDKException("获取分布式锁失败", e);
		}
	}
	
	public static AbstractMutexLock getRedisMutexLock(String redisKey) throws PaasException{
		return getRedisMutexLock(RedisLockConstants.REDIS_LOCK_NAMESPACE,redisKey);
	}
}
