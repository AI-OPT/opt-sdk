package com.ai.opt.sdk.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * MD5编码
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class Md5Encoder {
	private static final Logger LOG = LoggerFactory.getLogger(Md5Encoder.class);
    private Md5Encoder() {
    }

    private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
            'B', 'C', 'D', 'E', 'F' };

    private static char[] encodeHex(byte[] data) {

        int l = data.length;

        char[] out = new char[l << 1];

        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS[0x0F & data[i]];
        }

        return out;
    }

    /**
     * 密码md5加密
     * @param rawPass 密码信息
     * @return
     * @author
     */
    public static String encodePassword(String rawPass) {

        MessageDigest messageDigest = getMessageDigest();

        byte[] digest = messageDigest.digest(rawPass.getBytes());

        return new String(encodeHex(digest));
    }

    /**
     * 获取信息
     * @return
     * @author
     */
    protected final static MessageDigest getMessageDigest()  {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        	LOG.error(e.getMessage(),e);
            throw new IllegalArgumentException("No such algorithm [" + "MD5" + "]");
        }
    }

    /**
     * 校验密码
     * @param encPass
     * @param rawPass
     * @return
     * @author
     */
    public static boolean isPasswordValid(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encodePassword(rawPass);
        return pass1.equals(pass2);
    }

}
