package com.ai.opt.sdk.components.lock;

import com.ai.opt.sdk.components.base.ComponentConfigLoader;
import com.ai.opt.sdk.components.lock.zklock.ZKMutexLock;
import com.ai.opt.sdk.components.mo.PaasConf;
import com.ai.opt.sdk.exception.SDKException;
import com.ai.paas.ipaas.PaasException;
import com.ai.paas.ipaas.ccs.zookeeper.ZKClient;
import com.ai.paas.ipaas.ccs.zookeeper.impl.ZKPoolFactory;

public class ZKMutexLockFactory {

	public static AbstractMutexLock getZKMutexLock(String zkAddr,int zkTimeoutMillSecs,String zkLockNodePath) throws PaasException{
		ZKClient zkClient = null;
		try {
			zkClient = ZKPoolFactory.getZKPool(zkAddr,zkTimeoutMillSecs).getZkClient(zkAddr);
			return new ZKMutexLock(zkClient.getInterProcessLock(zkLockNodePath));
		} catch (Exception e) {
			throw new SDKException("获取分布式锁失败", e);
		}		
	}
	public static AbstractMutexLock getZKMutexLock(String zkAddr,String zkLockNodePath) throws PaasException{
		//zk连接超时时间默认为60000毫秒，即1分钟。
		return getZKMutexLock(zkAddr,60000,zkLockNodePath);		
	}
	
	public static AbstractMutexLock getZKMutexLock(String zkLockNodePath) throws PaasException{
		PaasConf authInfo = ComponentConfigLoader.getInstance().getPaasAuthInfo();
		return getZKMutexLock(authInfo.getCcsZkAddress(),zkLockNodePath);		
	}	
	
}
