package com.xm.ms.service;

import com.xm.ms.dao.GoodsDao;
import com.xm.ms.domain.Goods;
import com.xm.ms.domain.MiaoshaOrder;
import com.xm.ms.domain.MiaoshaUser;
import com.xm.ms.domain.OrderInfo;
import com.xm.ms.redis.MiaoshaKey;
import com.xm.ms.redis.RedisService;
import com.xm.ms.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-01 - 23:58
 * description:
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisService redisService;

    /**
     * 1减库存 2下订单 3写入秒杀订单（原子操作）
     */
    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods) {
        //
        boolean success = goodsService.reduceStock(goods);
        if (success){
            //操作order_info miaosha_order两张表
            return orderService.createOrder(user, goods);
        } else {
            setGoodsOver(goods.getId());
            return null;
        }
    }

    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);
        if (order != null) {//秒杀成功
            return order.getOrderId();
        } else {
            boolean isOver = getGoodsOver(goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
    }
}
