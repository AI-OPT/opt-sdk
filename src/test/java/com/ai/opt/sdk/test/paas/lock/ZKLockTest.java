package com.ai.opt.sdk.test.paas.lock;

import org.junit.Test;

public class ZKLockTest {

	@Test
	public void testZKLock(){
		for(int i=0;i<10;i++){
			ZKLockTestTread t=new ZKLockTestTread();
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
