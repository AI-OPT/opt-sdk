package com.ai.opt.sdk.test.paas.lock;

import org.junit.Test;

public class RedisLockTest {

	@Test
	public void testRedisLock(){
		for(int i=0;i<10;i++){
			RedisLockTestTread t=new RedisLockTestTread();
			t.start();
		}
		while(true){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
