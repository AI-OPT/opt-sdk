package com.ai.opt.sdk.test.paas.lock;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ai.opt.sdk.components.lock.AbstractMutexLock;
import com.ai.opt.sdk.components.lock.RedisMutexLockFactory;
import com.ai.opt.sdk.components.lock.ZKMutexLockFactory;
import com.ai.paas.ipaas.PaasException;

public class RedisLockTestTread extends Thread{
	private Logger logger=LoggerFactory.getLogger(getClass());
	private final String REDISKEY="redislock.readfile";
    public RedisLockTestTread(){
    }
    @Override
    public void run() {
        AbstractMutexLock lock=null;
        boolean lockflag=false;
        try{
        	lock=RedisMutexLockFactory.getRedisMutexLock(REDISKEY);
        	//lock.acquire();//争锁，无限等待
        	lockflag=lock.acquire(10, TimeUnit.SECONDS);//争锁，超时时间10秒。
        	if(lockflag){
        		//获取到分布式锁，执行任务
        		logger.info("SUCESS线程【"+Thread.currentThread().getName()+"】获取到分布式锁，执行任务");
        		Thread.sleep(100000000);
        	}
        	else{
        		//未获取到分布式锁，不执行任务
        		logger.info("FAILURE线程【"+Thread.currentThread().getName()+"】未获取到分布式锁，不执行任务");
        	}
        } catch (PaasException e) {
        	logger.error("线程【"+Thread.currentThread().getName()+"】获取分布式锁出错："+e.getMessage(),e);
			e.printStackTrace();
		}
        catch(Exception e)
        {
        	logger.error("线程【"+Thread.currentThread().getName()+"】运行出错："+e.getMessage(),e);
			e.printStackTrace();

        }
        finally{
        	if(lock!=null&&lockflag){
        		try {
					lock.release();
					logger.error("线程【"+Thread.currentThread().getName()+"】释放分布式锁OK");
				} catch (Exception e) {
					logger.error("线程【"+Thread.currentThread().getName()+"】释放分布式锁出错："+e.getMessage(),e);
					e.printStackTrace();
				}
        	}
        }
        //System.out.println("【"+Thread.currentThread().getName()+"】");
    } 
}
