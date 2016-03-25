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
	
}
