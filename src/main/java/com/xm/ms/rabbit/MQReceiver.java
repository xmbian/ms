package com.xm.ms.rabbit;

import com.xm.ms.domain.MiaoshaOrder;
import com.xm.ms.domain.MiaoshaUser;
import com.xm.ms.redis.RedisService;
import com.xm.ms.result.CodeMsg;
import com.xm.ms.result.Result;
import com.xm.ms.service.GoodsService;
import com.xm.ms.service.MiaoshaService;
import com.xm.ms.service.OrderService;
import com.xm.ms.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-07 - 21:16
 * description:
 */
@Service
public class MQReceiver {

    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
        MiaoshaMessage mm = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();
        //判断库存（数据库）
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {//TOTHINK 当多线程访问时，可能会有多个线程同时到达，并且判断库存大于0，此时这几个线程会进行如下操作，这时秒杀库存可能会出现负数。
            return;
        }

        //判断是否已经秒杀到了（不可重复秒杀）
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        if (order != null) {
            return;
        }
        //可以秒杀：1减库存 2下订单 3写入秒杀订单（原子操作）
        miaoshaService.miaosha(user, goods);
    }

/*
    @RabbitListener(queues=MQConfig.QUEUE)
    public void receive(String message) {
        log.info("receive message:"+message);
    }

    @RabbitListener(queues=MQConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        log.info(" topic  queue1 message:"+message);
    }

    @RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        log.info(" topic  queue2 message:"+message);
    }

    @RabbitListener(queues=MQConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        log.info(" header  queue message:"+new String(message));
    }
*/
}
