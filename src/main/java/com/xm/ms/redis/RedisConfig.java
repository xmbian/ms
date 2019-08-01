package com.xm.ms.redis;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-30 - 20:53
 * description:
 */
@Component
@ConfigurationProperties(prefix = "redis")//获取application.properties中前缀为redis的配置
@Setter
@Getter
public class RedisConfig {
    private String host;
    private int port;
    private int timeout;//秒
    private String password;
    private int poolMaxTotal;
    private int poolMaxIdle;
    private int poolMaxWait;//秒
    private int database;
}
