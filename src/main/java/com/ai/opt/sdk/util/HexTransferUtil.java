package com.ai.opt.sdk.util;

import java.util.Arrays;

import com.ai.opt.base.exception.SystemException;

/**
 * HexTransferUtil
 * Date: 2017年2月10日 <br>
 * Copyright (c) 2017 asiainfo.com <br>
 * 
 * @author
 */
public final class HexTransferUtil {

    private HexTransferUtil() {

    }

    // 定义三十六进制数组
    final static String[] RESOURCES = new String[] { "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    // , "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
    // "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    final static char[] CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    // 结束字符
    public final static String ENDSTR = "z";

    /**
     * 创建固定长度SEQ
     * @param len
     * @return
     * @author
     */
    public static String createSeq(int len) {
        StringBuilder newSeq = new StringBuilder();
        for (int i = 0; i < len; i++) {
            newSeq.append("0");
        }
        return newSeq.toString();
    }

    /**
     *  获取最大SEQ
     * @param seq
     * @return
     * @author
     */
    public static String getMaxSeq(String seq) {
        StringBuilder maxSeq = new StringBuilder();
        for (int i = 0; i < seq.length(); i++) {
            maxSeq.append(ENDSTR);
        }
        return maxSeq.toString();
    }

    /**
     * 获取下一个SEQ的值
     * @param currSeq
     * @return
     * @author
     */
    public static String getNextSeq(String currSeq) {
        String nextSeq = "";
        int seqLength = currSeq.length();
        for (int i = seqLength - 1; i >= 0; i--) {
            String currPlaceSourceValue = currSeq.substring(i, i + 1);
            String currPlaceDestValue = "";
            int currPlace = Arrays.binarySearch(RESOURCES, currPlaceSourceValue);

            int afertCount = seqLength - 1 - i;
            String subCurrPlaceSourceValue = currSeq.substring(seqLength - afertCount, seqLength);
            int cnt = 0;
            for (int j = subCurrPlaceSourceValue.length(); j > 0; j--) {
                if (ENDSTR.equals(subCurrPlaceSourceValue.substring(j - 1, j))) {
                    cnt++;
                }
            }
            if (afertCount == cnt) {
                if (ENDSTR.equals(currPlaceSourceValue)) {
                    currPlaceDestValue = "0";
                } else {
                    currPlaceDestValue = RESOURCES[currPlace + 1];
                }
            } else {
                currPlaceDestValue = currPlaceSourceValue;
            }
            nextSeq = currPlaceDestValue + nextSeq;
        }
        return nextSeq;
    }

    /**
     *  十进制转二进制
     * @param decimal
     * @param intLenght
     * @return
     * @throws SystemException
     * @author
     */
    public static String decimalToBinary(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转二进制时传入参数为空");
        }
        return Long.toBinaryString(Long.valueOf(decimal));
    }

    /**
     * 十进制转八进制
     * @param decimal
     * @param intLenght
     * @return
     * @throws SystemException
     * @author
     */
    public static String decimalToOctal(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转八进制时传入参数为空");
        }
        return Long.toOctalString(Long.valueOf(decimal));
    }

    /**
     * 十进制转十六进制
     * @param decimal
     * @param intLenght
     * @return
     * @throws SystemException
     * @author
     */
    public static String decimalToHex(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转十六进制时传入参数为空");
        }
        return Long.toHexString(Long.valueOf(decimal));
    }

    /**
     * 十进制转三十六进制
     * @param decimal
     * @param intLenght
     * @return
     * @throws SystemException
     * @author
     */
    public static String decimalTo36(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转三十六进制时传入参数为空");
        }
        if (Double.valueOf(decimal) >= Math.pow(36, intLenght)) {
            throw new SystemException("SEQ已达最大值");
        }
        String ts = "";

        // 建立数组，用于存储36进制的字符
        char[] chs = new char[intLenght];
        long lnBvalue = Long.valueOf(decimal);
        for (int i = 0; i < intLenght; i++) {
            long lng_Mod;
            char char_Mod;
            lng_Mod = lnBvalue % 36;
            char_Mod = CHARS[Integer.valueOf(String.valueOf(lng_Mod)).intValue()];
            chs[i] = char_Mod;
            if (lnBvalue / 36 == 0) {
                ts = String.valueOf(char_Mod);
                while (ts.length() < intLenght) {
                    ts = "0000000" + ts;
                }
                return ts.substring(ts.length() - intLenght);
            }
            if (lnBvalue / 36 < 36) {
                char_Mod = CHARS[Integer.valueOf(String.valueOf(lnBvalue / 36)).intValue()];
                chs[i + 1] = char_Mod;
                for (int j = 0; j < i + 2; j++) {
                    ts = String.valueOf(chs[j]) + ts;
                }
                while (ts.length() < intLenght) {
                    ts = "0000000" + ts;
                }
                return ts.substring(ts.length() - intLenght);
            } else {
                lnBvalue = lnBvalue / 36;
            }
        }
        while (ts.length() < intLenght) {
            ts = "0000000" + ts;
        }
        return ts.substring(ts.length() - intLenght);
    }

    /**
     * Dec_2_36
     * @param decimal
     * @param intLenght
     * @return
     * @throws SystemException
     * @author
     */
    public static String Dec_2_36(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转三十六进制时传入参数为空");
        }
        if (Double.valueOf(decimal) >= Math.pow(36, intLenght)) {
            throw new SystemException("SEQ已达最大值");
        }
        int intDecimal = Integer.valueOf(decimal);
        int mod = intDecimal % 36;
        int div = intDecimal / 36;

        String result = "" + CHARS[mod];

        while (div > 0) {
            mod = div % 36;
            div = div / 36;
            result = CHARS[mod] + result;
        }
        while (result.length() < intLenght) {
            result = "0000000" + result;
        }
        return result.substring(result.length() - intLenght);
    }

}
