package com.xm.ms.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-31 - 10:30
 * description:
 */
@Setter
@Getter
public class MiaoshaUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
