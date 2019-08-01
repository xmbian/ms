package com.xm.ms.vo;

import com.xm.ms.domain.Goods;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-01 - 21:46
 * description:
 */
@Getter
@Setter
public class GoodsVo extends Goods{
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
