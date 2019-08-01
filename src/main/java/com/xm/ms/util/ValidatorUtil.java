package com.xm.ms.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-31 - 10:20
 * description:
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern =Pattern.compile("1\\d{10}");

    public static boolean isMobile(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(str);
        return matcher.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("12345678900"));
        System.out.println(isMobile("1234567890"));
    }
}
