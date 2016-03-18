package com.ai.opt.sdk.util;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CertValidateUtil {
	private static final Logger LOG = LoggerFactory.getLogger(CertValidateUtil.class);
	private CertValidateUtil(){}
    // 位权值数组
    private static final byte[] WI = new byte[17];

    // 身份证前部分字符数
    private static final byte FPART = 6;

    // 身份证算法求模关键值
    private static final byte FMOD = 11;

    // 旧身份证长度
    private static final byte OLDIDLEN = 15;

    // 新身份证长度
    private static final byte NEWIDLEN = 18;

    // 新身份证年份标志
    private static final String YEARFLAG = "19";

    // 校验码串
    private static final String CHECKCODE = "10X98765432";

    // 最小的行政区划码
    private static final int MINCODE = 150000;

    // 最大的行政区划码
    private static final int MAXCODE = 700000;

    // 旧身份证号码
    // private String oldIDCard="";
    // 新身份证号码
    // private String newIDCard="";
    // 地区及编码
    // private String Area[][2] =

    private static void setWiBuffer() {
        for (int i = 0; i < WI.length; i++) {

            int k = (int) Math.pow(2, (WI.length - i));

            WI[i] = (byte) (k % FMOD);

        }
    }

    // 获取新身份证的最后一位:检验位
    private static String getCheckFlag(String idCard) {

        int sum = 0;

        // 进行加权求和
        for (int i = 0; i < 17; i++) {

            sum += Integer.parseInt(idCard.substring(i, i + 1)) * WI[i];

        }

        // 取模运算，得到模值
        byte iCode = (byte) (sum % FMOD);

        return CHECKCODE.substring(iCode, iCode + 1);

    }

    // 判断串长度的合法性
    private static boolean checkLength(String idCard) {
        return (idCard.length() == OLDIDLEN) || (idCard.length() == NEWIDLEN);

    }

    // 获取时间串
    private static String getIDDate(String idCard, boolean newIDFlag) {
        String dateStr = "";
        if (newIDFlag) {
            dateStr = idCard.substring(FPART, FPART + 8);
        } else {
            dateStr = YEARFLAG + idCard.substring(FPART, FPART + 6);
        }
        return dateStr;

    }

    // 判断时间合法性
    private static boolean checkDate(final String dateSource) {

        String dateStr = dateSource.substring(0, 4) + "-" + dateSource.substring(4, 6) + "-"
                + dateSource.substring(6, 8);

        DateFormat df = DateFormat.getDateInstance();

        df.setLenient(false);

        try {
            Date date = df.parse(dateStr);
            return (date != null);
        } catch (ParseException e) {
        	LOG.error(e.getMessage(),e);
            return false;
        }

    }

    // 旧身份证转换成新身份证号码
    public static String getNewIDCard(final String oldIDCard) {
        // 初始化方法
        CertValidateUtil.setWiBuffer();
        if (!checkIDCard(oldIDCard)) {
            return oldIDCard;
        }
        String newIDCard = oldIDCard.substring(0, FPART);
        newIDCard += YEARFLAG;
        newIDCard += oldIDCard.substring(FPART, oldIDCard.length());
        String ch = getCheckFlag(newIDCard);
        newIDCard += ch;
        return newIDCard;
    }

    // 新身份证转换成旧身份证号码
    public static String getOldIDCard(final String newIDCard) {
        // 初始化方法
        CertValidateUtil.setWiBuffer();
        if (!checkIDCard(newIDCard)) {
            return newIDCard;
        }
        String oldIDCard = newIDCard.substring(0, FPART)
                + newIDCard.substring(FPART + YEARFLAG.length(), newIDCard.length() - 1);
        return oldIDCard;
    }

    // 判断身份证号码的合法性
    public static boolean checkIDCard(String idCard) {
        // 初始化方法
        CertValidateUtil.setWiBuffer();
        if (!checkLength(idCard)) {
            return false;
        }
        boolean isNew = true;
        String idDate = getIDDate(idCard, isNew);
        if (!checkDate(idDate)) {
            return false;
        }

        if (isNew) {
            String checkFlag = getCheckFlag(idCard);
            String theFlag = idCard.substring(idCard.length() - 1, idCard.length());
            if (!checkFlag.equals(theFlag)) {
                return false;
            }
        }
        return true;
    }

    // 获取一个随机的"伪"身份证号码
    public static String getRandomIDCard(final boolean idNewID) {
        // 初始化方法
        CertValidateUtil.setWiBuffer();
        //Random ran = new Random();
        SecureRandom ran=new SecureRandom();
        String idCard = getAddressCode(ran) + getRandomDate(ran, idNewID) + getIDOrder(ran);
        if (idNewID) {
            String ch = getCheckFlag(idCard);
            idCard += ch;
        }
        return idCard;
    }

    // 产生随机的地区编码
    private static String getAddressCode(Random ran) {
        if (ran == null) {
            return "";
        } else {
            int addrCode = MINCODE + ran.nextInt(MAXCODE - MINCODE);
            return Integer.toString(addrCode);
        }

    }

    // 产生随机的出生日期
    private static String getRandomDate(Random ran, boolean idNewID) {
        if (ran == null) {
            return "";
        }
        int year = 0;
        if (idNewID) {
            year = 1900 + ran.nextInt(2007 - 1900);
        } else {
            year = 1 + ran.nextInt(99);
        }
        int month = 1 + ran.nextInt(12);
        int day = 0;
        if (month == 2) {
            day = 1 + ran.nextInt(28);
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
                || month == 10 || month == 12) {
            day = 1 + ran.nextInt(31);
        } else {
            day = 1 + ran.nextInt(30);
        }
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);
        return Integer.toString(year) + nf.format(month) + nf.format(day);
    }

    // 产生随机的序列号
    private static String getIDOrder(Random ran) {
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(3);
        nf.setMinimumIntegerDigits(3);
        if (ran == null) {
            return "";
        } else {
            int order = 1 + ran.nextInt(999);
            return nf.format(order);
        }
    }

}
