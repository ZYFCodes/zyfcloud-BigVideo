package org.zyf.cloud.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：封装常用的String类
 *
 * @author yanfengzhang
 * @date 2019-11-12 09:22
 */
public class ZyfStringUtils extends org.apache.commons.lang3.StringUtils {
    public static int getParamAsInt(Object obj, int defaultValue) {
        String param = getParamAsString(obj);
        try {
            return Integer.parseInt(param);
        } catch (NumberFormatException ex) {
        }
        return defaultValue;
    }

    public static long getParamAsLong(Object obj, long defaultValue) {
        String param = getParamAsString(obj);
        try {
            return Long.parseLong(param);
        } catch (NumberFormatException ex) {
        }
        return defaultValue;
    }

    public static boolean getParamAsBoolean(Object obj, boolean defaultValue) {
        String value = getParamAsString(obj);
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        return defaultValue;
    }

    public static Date toDate(String obj) {
        return toDate(obj, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date toDate(String obj, String datePattern) {
        DateFormat formater = new SimpleDateFormat(datePattern);
        if (obj.indexOf("_") > -1) {
            obj = obj.replace('_', ':');
        }
        try {
            return formater.parse(obj);
        } catch (ParseException ex) {
        }
        return null;
    }

    public static Date getParamAsDate(String obj, String datePattern) {
        return toDate(obj, datePattern);
    }

    public static Date getParamAsDate(Object obj, String datePattern) {
        String param = getParamAsString(obj);
        return toDate(param, datePattern);
    }

    public static String getParamAsString(Object obj) {
        if (obj == null) {
            return "";
        }
        if ((obj instanceof String[])) {
            String[] param = (String[]) obj;
            return param.length == 0 ? "" : param[0];
        }
        return obj.toString();
    }

    public static String getParamAsString(String obj, String defaultstr) {
        return obj == null ? defaultstr : obj;
    }

    /**
     * 实现将原字符串中特殊字符对之间的内容进行指定字符串的替换
     * 思路举例：将原字符串dd/d{x=xx""""x}d(ll)dd替换为dd/d{yyy=y"""yyyy}d(ll)dd
     *
     * @param oldStr     原字符串
     * @param leftChar   匹配对特殊符号1
     * @param rightChar  匹配对特殊符号2
     * @param replaceStr 替换子字符串
     * @return string
     */
    public static String replaceSpecifiedContent(String oldStr, char leftChar, char rightChar, String replaceStr) {
        char[] chars = oldStr.toCharArray();
        boolean isAppend = true;
        StringBuilder stringBuffer = new StringBuilder();
        for (char currentChar : chars) {
            if (isAppend) {
                if (currentChar == leftChar) {
                    isAppend = false;
                    stringBuffer.append(currentChar).append(replaceStr);
                    continue;
                }
                stringBuffer.append(currentChar);
            } else {
                if (currentChar == rightChar) {
                    isAppend = true;
                    stringBuffer.append(currentChar);
                }
            }
        }
        return stringBuffer.toString();
    }

    /**
     * 统计出现次数
     *
     * @param string 目标字符串
     * @param sub    目标子字符串
     * @return int
     */
    public static int countMatched(String string, String sub) {
        //判空，为空直接返回0，表示不存在
        if (ZyfStringUtils.isEmpty(string)) {
            return 0;
        }

        int count = 0;
        int index;
        // 循环一次将出现的字符串索引向后移一位，计数器+1，并截取原字符串索引+1的字符串，
        // 如“abcdeeeee”第一次循环找到“e”索引4，substring(4+1)方法截取后得到新的字符串“eeee”，
        // 循环第二次找到“e”的索引0，substring(0+1)方法截取后得到新的字符串“eee”，，，以此类推
        while ((index = string.indexOf(sub)) > -1) {
            count++;
            string = string.substring(index + 1);
        }
        return count;
    }
}
