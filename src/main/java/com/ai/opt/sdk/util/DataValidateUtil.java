package com.ai.opt.sdk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataValidateUtil {

    private DataValidateUtil() {

    }

    /**
     * 检测邮箱地址是否合法
     * 
     * @param email
     * @return true合法 false不合法
     */
    public static boolean isEmail(String email) {
        if (StringUtil.isBlank(email)) {
            return false;
        }
        Pattern p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");// 复杂匹配
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断手机号是否合法
     * 
     * @param mobiles
     * @return true合法 false不合法
     * @author lijuan3
     */
    public static boolean isMobileNO(String mobiles) {
        if (StringUtil.isBlank(mobiles)) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 判断是否为固定电话
     * 
     * @param phone
     * @return
     * @author lijuan3
     */
    public static boolean isPhone(String phone) {
        if (StringUtil.isBlank(phone)) {
            return false;
        }
        Pattern p = Pattern.compile("\\d{3,4}-\\d{7,8}");
        Matcher m = p.matcher(phone);
        return m.matches();
    }

    /**
     * 判断是否为手机或者固定电话
     * 
     * @param mobileOrPhone
     * @return
     * @author lijuan3
     */
    public static boolean isMobileOrPhone(String mobileOrPhone) {
        if (StringUtil.isBlank(mobileOrPhone)) {
            return false;
        }
        return isMobileNO(mobileOrPhone) || isPhone(mobileOrPhone);
    }

    /**
     * 判断是否为邮编
     * 
     * @param postCode
     * @return
     * @author lijuan3
     */
    public static boolean isPostCode(String postCode) {
        if (StringUtil.isBlank(postCode)) {
            return false;
        }
        Pattern p = Pattern.compile("[1-9]\\d{5}(?!\\d)");
        Matcher m = p.matcher(postCode);
        return m.matches();
    }

}
