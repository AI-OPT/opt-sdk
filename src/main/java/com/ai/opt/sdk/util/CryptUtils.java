package com.ai.opt.sdk.util;

import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.log4j.Logger;
import org.springframework.util.DigestUtils;

public class CryptUtils {
	private static final Logger LOG = Logger.getLogger(CryptUtils.class);
	private static String CRYPT_KEY = "asiainfo";
	private static String regString = "[a-f0-9A-F]{16,}";

	// 加密
	private static Cipher ecip;
	// 解密
	private static Cipher dcip;

	static {
		try {
			String KEY = DigestUtils.md5DigestAsHex(CRYPT_KEY.getBytes()).toUpperCase();
			KEY = KEY.substring(0, 8);
			byte[] bytes = KEY.getBytes();
			DESKeySpec ks = new DESKeySpec(bytes);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
			SecretKey sk = skf.generateSecret(ks);
			IvParameterSpec iv2 = new IvParameterSpec(bytes);

			ecip = Cipher.getInstance("DES/CBC/PKCS5Padding");
			ecip.init(Cipher.ENCRYPT_MODE, sk, iv2);

			dcip = Cipher.getInstance("DES/CBC/PKCS5Padding");
			dcip.init(Cipher.DECRYPT_MODE, sk, iv2);
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}

	public static String encrypt(String content) {
		try {
			byte[] bytes = ecip.doFinal(content.getBytes("ascii"));
			return byte2hex(bytes);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	public static String decrypt(String content) {
		try {
			if (Pattern.matches(regString, content)) {
				byte[] bytes = hex2byte(content);
				bytes = dcip.doFinal(bytes);
				return new String(bytes, "ascii");
			} else {
				return content;
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 字节码转换成16进制字符串
	 * 
	 * @param byte[]
	 *            b 输入要转换的字节码
	 * @return String 返回转换后的16进制字符串
	 */
	private static String byte2hex(byte[] bytes) {
		StringBuilder hs = new StringBuilder();
		for (byte b : bytes)
			hs.append(String.format("%1$02X", b));
		return hs.toString();
	}

	private static byte[] hex2byte(String content) {
		int l = content.length() >> 1;
		byte[] result = new byte[l];
		for (int i = 0; i < l; i++) {
			int j = i << 1;
			String s = content.substring(j, j + 2);
			result[i] = Integer.valueOf(s, 16).byteValue();
		}
		return result;
	}


	// test
	public static void main(String[] args) throws Exception {
		String password = "123qwe";
		String en = encrypt(password);
		System.out.println(en);
		System.out.println(decrypt(en));
	}
}