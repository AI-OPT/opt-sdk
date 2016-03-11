package com.ai.opt.sdk.util;

import java.math.BigDecimal;
import java.util.Arrays;

import com.ai.opt.base.exception.SystemException;

public class UserDefindedSeqUtil {

    private UserDefindedSeqUtil() {

    }

    // 定义三十六进制数组
    protected final static String[] resources = new String[] { "0", "1", "2", "3", "4", "5", "6",
            "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
            "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"
    // , "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
    // "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    protected final static char[] chars = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
            'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

    // 结束字符
    public final static String endStr = "z";

    // 创建固定长度SEQ
    public static String createSeq(int len) {
        StringBuffer newSeq = new StringBuffer();
        for (int i = 0; i < len; i++) {
            newSeq.append("0");
        }
        return newSeq.toString();
    }

    // 获取最大SEQ
    public static String getMaxSeq(String seq) {
        StringBuffer maxSeq = new StringBuffer();
        for (int i = 0; i < seq.length(); i++) {
            maxSeq.append(endStr);
        }
        return maxSeq.toString();
    }

    // 获取下一个SEQ的值
    public static String getNextSeq(String currSeq) {
        String nextSeq = "";
        int seqLength = currSeq.length();
        for (int i = seqLength - 1; i >= 0; i--) {
            String currPlaceSourceValue = currSeq.substring(i, i + 1);
            String currPlaceDestValue = "";
            int currPlace = Arrays.binarySearch(resources, currPlaceSourceValue);

            int afertCount = seqLength - 1 - i;
            String subCurrPlaceSourceValue = currSeq.substring(seqLength - afertCount, seqLength);
            int cnt = 0;
            for (int j = subCurrPlaceSourceValue.length(); j > 0; j--) {
                if (endStr.equals(subCurrPlaceSourceValue.substring(j - 1, j))) {
                    cnt++;
                }
            }
            if (afertCount == cnt) {
                if (endStr.equals(currPlaceSourceValue)) {
                    currPlaceDestValue = "0";
                } else {
                    currPlaceDestValue = resources[currPlace + 1];
                }
            } else {
                currPlaceDestValue = currPlaceSourceValue;
            }
            nextSeq = currPlaceDestValue + nextSeq;
        }
        return nextSeq;
    }

    // 十进制转二进制
    public static String decimalToBinary(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转二进制时传入参数为空");
        }
        String _transform = "";
        _transform = Long.toBinaryString(Long.valueOf(decimal));
        return _transform;
    }

    // 十进制转八进制
    public static String decimalToOctal(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转八进制时传入参数为空");
        }
        String _transform = "";
        _transform = Long.toOctalString(Long.valueOf(decimal));
        return _transform;
    }

    // 十进制转十六进制
    public static String decimalToHex(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转十六进制时传入参数为空");
        }
        String _transform = "";
        _transform = Long.toHexString(Long.valueOf(decimal));
        return _transform;
    }

    // 十进制转三十六进制
    public static String decimalTo36(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转三十六进制时传入参数为空");
        }
        System.out.println("-------------------[" + BigDecimal.valueOf(Math.pow(36, intLenght))
                + "]------------");
        if (Double.valueOf(decimal) >= Math.pow(36, intLenght)) {
            throw new SystemException("SEQ已达最大值");
        }
        String _transform = "";

        // 建立数组，用于存储36进制的字符
        char[] chs = new char[intLenght];
        long lnBvalue = Long.valueOf(decimal);
        for (int i = 0; i < intLenght; i++) {
            long lng_Mod;
            char char_Mod;
            lng_Mod = lnBvalue % 36;
            char_Mod = chars[Integer.valueOf(String.valueOf(lng_Mod)).intValue()];
            chs[i] = char_Mod;
            if (lnBvalue / 36 == 0) {
                _transform = String.valueOf(char_Mod);
                while (_transform.length() < intLenght) {
                    _transform = "0000000" + _transform;
                }
                return _transform.substring(_transform.length() - intLenght);
            }
            if (lnBvalue / 36 < 36) {
                char_Mod = chars[Integer.valueOf(String.valueOf(lnBvalue / 36)).intValue()];
                chs[i + 1] = char_Mod;
                for (int j = 0; j < i + 2; j++) {
                    _transform = String.valueOf(chs[j]) + _transform;
                }
                while (_transform.length() < intLenght) {
                    _transform = "0000000" + _transform;
                }
                return _transform.substring(_transform.length() - intLenght);
            } else {
                lnBvalue = lnBvalue / 36;
            }
        }
        // System.out.println(_transform);
        while (_transform.length() < intLenght) {
            _transform = "0000000" + _transform;
        }
        return _transform.substring(_transform.length() - intLenght);
    }

    public static String Dec_2_36(String decimal, int intLenght) throws SystemException {
        if (StringUtil.isBlank(decimal)) {
            throw new SystemException("SEQ十进制转三十六进制时传入参数为空");
        }
        // System.out.println("-------------------["+BigDecimal.valueOf(Math.pow(36,
        // intLenght))+"]------------");
        if (Double.valueOf(decimal) >= Math.pow(36, intLenght)) {
            throw new SystemException("SEQ已达最大值");
        }
        int intDecimal = Integer.valueOf(decimal);
        int mod = intDecimal % 36;
        int div = intDecimal / 36;

        String result = "" + chars[mod];

        while (div > 0) {
            mod = div % 36;
            div = div / 36;
            result = chars[mod] + result;
        }
        while (result.length() < intLenght) {
            result = "0000000" + result;
        }
        return result.substring(result.length() - intLenght);
    }
}
