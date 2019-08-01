package com.xm.ms.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-31 - 9:13
 * description:
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassFormPass(String inputPass){
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String fromPassToDBPass(String fromPass, String salt){
        String str = "" + salt.charAt(0) + salt.charAt(2) + fromPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public  static String inputPassToDBPass(String input, String saltDB) {
        String formPass = inputPassFormPass(input);
        String dbPass = fromPassToDBPass(formPass, saltDB);
        return dbPass;
    }
    public static void main(String[] args) {
//        System.out.println(inputPassFormPass("123456"));//12123456c3
//        System.out.println(fromPassToDBPass(inputPassFormPass("123456"),"1a2b3c4d"));//
        System.out.println(inputPassToDBPass("123456","1a2b3c4d"));
    }

}
