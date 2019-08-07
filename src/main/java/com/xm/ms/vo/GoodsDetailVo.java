package com.xm.ms.vo;

import com.xm.ms.domain.MiaoshaUser;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-06 - 23:39
 * description:
 */
@Setter
@Getter
public class GoodsDetailVo {
    private int miaoshaStatus;
    private int remainSeconds;
    private GoodsVo goods;
    private MiaoshaUser user;
}
