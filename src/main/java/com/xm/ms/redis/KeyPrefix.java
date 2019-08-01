package com.xm.ms.redis;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-30 - 22:43
 * description:
 */
public interface KeyPrefix {

    int expireSeconds();

    String getPrefix();

}
