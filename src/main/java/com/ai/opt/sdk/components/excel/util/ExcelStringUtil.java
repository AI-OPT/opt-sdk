package com.ai.opt.sdk.components.excel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Excel字符串工具类
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public class ExcelStringUtil {

	// Delim style
	public static final String DELIM_DEFAULT = ".";

	private ExcelStringUtil() {
		// Cannot be instantiated
	}

	/**
	 * 将指定对象转换成字符串
	 * 
	 * @param obj
	 *            指定对象
	 * @return 转换后的字符串
	 */
	public static String toString(Object obj) {
		StringBuffer buffer = new StringBuffer();
		if (obj != null) {
			buffer.append(obj);
		}
		return buffer.toString();
	}

	/**
	 * 判断指定字符串是否等于null或空字符串
	 * 
	 * @param str
	 *            指定字符串
	 * @return 如果等于null或空字符串则返回true，否则返回false
	 */
	public static boolean isBlank(String str) {
		return str == null || "".equals(str.trim());
	}

	/**
	 * 判断指定字符串是否不等于null和空字符串
	 * 
	 * @param str
	 *            指定字符串
	 * @return 如果不等于null和空字符串则返回true，否则返回false
	 */
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 根据默认分隔符获取字符串前缀
	 * 
	 * @param str
	 *            指定字符串
	 * @return 返回前缀字符串
	 */
	public static String getPrefix(String str) {
		return getPrefix(str, DELIM_DEFAULT);
	}

	/**
	 * 根据指定分隔符获取字符串前缀
	 * 
	 * @param str
	 *            指定字符串
	 * @param delim
	 *            指定分隔符
	 * @return 返回字符串前缀
	 */
	public static String getPrefix(String str, String delim) {
		String prefix = "";
		if (isNotBlank(str) && isNotBlank(delim)) {
			int pos = str.indexOf(delim);
			if (pos > 0) {
				prefix = str.substring(0, pos);
			}
		}
		return prefix;
	}

	/**
	 * 根据默认分隔符获取字符串后缀
	 * 
	 * @param str
	 *            指定字符串
	 * @return 返回字符串后缀
	 */
	public static String getSuffix(String str) {
		return getSuffix(str, DELIM_DEFAULT);
	}

	/**
	 * 根据指定分隔符获取字符串后缀
	 * 
	 * @param str
	 *            指定字符串
	 * @param delim
	 *            指定分隔符
	 * @return 返回字符串后缀
	 */
	public static String getSuffix(String str, String delim) {
		String suffix = "";
		if (isNotBlank(str) && isNotBlank(delim)) {
			int pos = str.lastIndexOf(delim);
			if (pos > 0) {
				suffix = str.substring(pos + 1);
			}
		}
		return suffix;
	}

	/**
	 * 根据指定字符串和重复次数生成新字符串
	 * 
	 * @param str
	 *            指定字符串
	 * @param repeatCount
	 *            重复次数
	 * @return 返回生成的新字符串
	 */
	public static String newString(String str, int repeatCount) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < repeatCount; i++) {
			buf.append(str);
		}
		return buf.toString();
	}

	/**
	 * 隐藏字符串指定位置的字符
	 * 
	 * @param str
	 *            指定字符串
	 * @param index
	 *            起始位置
	 * @param length
	 *            字符长度
	 * @return 返回隐藏字符后的字符串
	 */
	public static String hideChars(String str, int index, int length) {
		return hideChars(str, index, length, true);
	}

	/**
	 * 隐藏字符串指定位置的字符
	 * 
	 * @param str
	 *            指定字符串
	 * @param start
	 *            起始位置
	 * @param end
	 *            结束位置
	 * @param confusion
	 *            是否混淆隐藏的字符个数
	 * @return 返回隐藏字符后的字符串
	 */
	public static String hideChars(String str, int start, int end,
			boolean confusion) {
		StringBuffer buf = new StringBuffer();
		if (isNotBlank(str)) {
			int startIndex = Math.min(start, end);
			int endIndex = Math.max(start, end);
			// 如果起始位置超出索引范围则默认置为0
			if (startIndex < 0 || startIndex > str.length()) {
				startIndex = 0;
			}
			// 如果结束位置超出索引范围则默认置为字符串长度
			if (endIndex < 0 || endIndex > str.length()) {
				endIndex = str.length();
			}
			String temp = newString("*", confusion ? 4 : endIndex - startIndex);
			buf.append(str).replace(startIndex, endIndex, temp);

		}
		return buf.toString();
	}

	/**
	 * 将指定字符串转换成大写
	 * 
	 * @param str
	 *            指定字符串
	 * @return 返回转换后的大写字符串
	 */
	public static String toLowerCase(String str) {
		StringBuffer buffer = new StringBuffer(str);
		for (int i = 0; i < buffer.length(); i++) {
			char c = buffer.charAt(i);
			buffer.setCharAt(i, Character.toLowerCase(c));
		}
		return buffer.toString();
	}

	/**
	 * 将指定字符串转换成大写
	 * 
	 * @param str
	 *            指定字符串
	 * @return 返回转换后的大写字符串
	 */
	public static String toUpperCase(String str) {
		StringBuffer buffer = new StringBuffer(str);
		for (int i = 0; i < buffer.length(); i++) {
			char c = buffer.charAt(i);
			buffer.setCharAt(i, Character.toUpperCase(c));
		}
		return buffer.toString();
	}

	/**
	 * 将指定字符串转换成驼峰命名方式
	 * 
	 * @param str
	 *            指定字符串
	 * @return 返回驼峰命名方式
	 */
	public static String toCalmelCase(String str) {
		StringBuffer buffer = new StringBuffer(str);
		if (buffer.length() > 0) {
			// 将首字母转换成小写
			char c = buffer.charAt(0);
			buffer.setCharAt(0, Character.toLowerCase(c));
			Pattern p = Pattern.compile("_\\w");
			Matcher m = p.matcher(buffer.toString());
			while (m.find()) {
				String temp = m.group(); // 匹配的字符串
				int index = buffer.indexOf(temp); // 匹配的位置
				// 去除匹配字符串中的下划线，并将剩余字符转换成大写
				buffer.replace(index, index + temp.length(),
						temp.replace("_", "").toUpperCase());
			}
		}
		return buffer.toString();
	}

	/**
	 * 将指定字符串转换成匈牙利命名方式
	 * 
	 * @param str
	 *            指定字符串
	 * @return 转换后的匈牙利命名方式
	 */
	public static String toHungarianCase(String str) {
		StringBuffer buffer = new StringBuffer(str);
		if (buffer.length() > 0) {
			Pattern p = Pattern.compile("[A-Z]");
			Matcher m = p.matcher(buffer.toString());
			while (m.find()) {
				String temp = m.group(); // 匹配的字符串
				int index = buffer.indexOf(temp); // 匹配的位置
				// 在匹配的字符串前添加下划线，并将其余字符转换成大写
				buffer.replace(index, index + temp.length(), (index > 0
						? "_"
						: "") + temp.toLowerCase());
			}
		}
		return buffer.toString();
	}

	/**
	 * 将指定字符串首字母转换成大写字母
	 * 
	 * @param str
	 *            指定字符串
	 * @return 返回首字母大写的字符串
	 */
	public static String firstCharUpperCase(String str) {
		StringBuffer buffer = new StringBuffer(str);
		if (buffer.length() > 0) {
			char c = buffer.charAt(0);
			buffer.setCharAt(0, Character.toUpperCase(c));
		}
		return buffer.toString();
	}

	/**
	 * 将指定数组转换成字符串
	 * 
	 * @param objs
	 *            指定数组
	 * @return 返回转换后的字符串
	 */
	public static String array2String(Object[] objs) {
		StringBuffer buffer = new StringBuffer();
		if (objs != null) {
			for (int i = 0; i < objs.length; i++) {
				buffer.append(objs[i]).append(",");
			}
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}

	public static void main(String[] args) {
		String str = "log.text.txt";
		System.out.println(getPrefix(str));
		System.out.println(getSuffix(str));
		System.out.println(hideChars(str, 2, str.length() - 1));
		System.out.println(toString(null));
		System.out.println(toCalmelCase("rate_limit_exceeded"));
		System.out.println(toHungarianCase("rateLimitExceeded"));
		System.out.println(firstCharUpperCase(str));
		System.out.println(new StringBuffer().append(""));
		System.out.println(array2String(new String[]{"a", "b", "c"}));
	}

}
