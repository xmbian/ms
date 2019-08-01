package com.xm.ms.util;

import java.util.UUID;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-01 - 10:44
 * description:
 */
public class UUIDUtil {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-","");//将原生uuid中的-去掉
    }

}
