package com.github.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>正则校验</p>
 *
 * @author <a href="mailto:7066450@qq.com">panxi</a>
 * @version 1.0.0
 * @since 1.0
 */
public class RegexValidateUtil {
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static final String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";

    public static final String REGEX_INTEGER = "^\\d*$";

    public static final String REGEX_DECIMAL = "^[0-9]+(\\\\.[0-9]{1,\" + point + \"})?$";

    public static boolean validateByRegex(String regex, String str){
        return match(regex, str);
    }


    public static boolean isDecimal(String str, int point, int num){
        String regex = "^[0-9]{1," + num + "}(\\.[0-9]{1," + point + "})?$";
        return match(regex, str);
    }


    /**
     * @param regex 正则表达式字符串
     * @param str   要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
