package com.ai.opt.sdk.util;

/**
 * 生成指定位数的随机数
 *
 * Date: 2016年3月25日 <br>
 * Copyright (c) 2016 asiainfo.com <br>
 * @author gucl
 */
public class RandomUtil {
	public static String randomNum(int length){
		String str = "";
		str += (int)(Math.random()*10);
		for(int i = 0; i < length-1; i++){
			str += (int)(Math.random()*10);
		}
		return str;
	}
	
	
	public static final String randomString(int length) {
		char[] charArry = new char[length];
		int i = 0;
		while (i < length) {
			int f = (int) (Math.random() * 3);
			if (f == 0)
				charArry[i] = (char) ('A' + Math.random() * 26);
			else if (f == 1)
				charArry[i] = (char) ('a' + Math.random() * 26);
			else
				charArry[i] = (char) ('0' + Math.random() * 10);
			i++;
		}
		return new String(charArry);
	}
	
}
