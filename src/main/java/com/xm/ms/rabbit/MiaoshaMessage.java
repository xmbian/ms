package com.xm.ms.rabbit;

import com.xm.ms.domain.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-08 - 10:12
 * description:
 */
@Getter
@Setter
public class MiaoshaMessage {
    private MiaoshaUser user;
    private long goodsId;
}
