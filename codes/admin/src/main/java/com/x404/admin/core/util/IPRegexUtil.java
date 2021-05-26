package com.x404.admin.core.util;

/**
 * IP规则正则处理工具类 * * @author OneCoder * @date 2014年10月23日 下午1:23:09
 */
public class IPRegexUtil {

    /**
     * 校验所给IP是否满足给定规则
     *
     * @param ipStr     待校验IP
     * @param ipPattern IP匹配规则。支持 * 匹配所有和 - 匹配范围。用分号分隔 &lt;br&gt;
     *                  例如：10.34.163.*;10.34.162.1 -128
     * @return
     * @author li_hongzhe @nhn.com
     * @date 2014年10月23日 下午1:38:37
     */
    public static boolean validateIP(String ipStr, String ipPattern) {
        if (ipStr == null || ipPattern == null) {
            return false;
        }
        String[] patternList = ipPattern.split(";");
        for (String pattern : patternList) {
            if (passValidate(ipStr, pattern)) {
                return true;
            }
        }
        return false;
    }

    private static boolean passValidate(String ipStr, String pattern) {
        if (pattern.equals(ipStr)) {
            return true;
        }
        String[] ipStrArr = ipStr.split("\\.");
        String[] patternArr = pattern.split("\\.");
        if (ipStrArr.length != 4 || patternArr.length != 4) {
            return false;
        }
        int end = ipStrArr.length;
        if (patternArr[3].contains("-")) {
            end = 3;
            String[] rangeArr = patternArr[3].split("-");
            int from = Integer.valueOf(rangeArr[0]).intValue();
            int to = Integer.valueOf(rangeArr[1]).intValue();
            int value = Integer.valueOf(ipStrArr[3]).intValue();
            if (value < from || value > to) {
                return false;
            }
        }
        for (int i = 0; i < end; i++) {
            if (patternArr[i].equals("*")) {
                continue;
            }
            if (!patternArr[i].equalsIgnoreCase(ipStrArr[i])) {
                return false;
            }
        }
        return true;
    }

}