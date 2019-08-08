package com.xm.ms.controller;

import com.xm.ms.domain.User;
import com.xm.ms.rabbit.MQSender;
import com.xm.ms.redis.RedisService;
import com.xm.ms.redis.UserKey;
import com.xm.ms.result.CodeMsg;
import com.xm.ms.result.Result;
import com.xm.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-30 - 17:33
 * description:
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.success("hello,ms");
//        return new Result(0,"success","hello");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVER_ERROR);
//        return new Result(0,"success","hello");
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name","bxm");
        return "hello";
    }

    @Autowired
    UserService userService;

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet() {
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx() {
        userService.tx();
        return Result.success(true);
    }

    @Autowired
    RedisService redisService;

  /*  @RequestMapping("/redis/get")
    @ResponseBody
    public Result<Long> redisGet() {
        Long v1 = redisService.get("key1", Long.class);
        return Result.success(v1);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet() {
        redisService.set("key2", "123L");
        String str = redisService.get("key2", String.class);
        return Result.success(str);
    }*/

    @RequestMapping("/redis/pget")
    @ResponseBody
    public Result<User> redisPGet() {
        User user= redisService.get(UserKey.getById,""+1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/pset")
    @ResponseBody
    public Result<Boolean> redisPSet() {
        User user = new User();
        user.setId(1);
        user.setName("1111");
        boolean b = redisService.set(UserKey.getById, "" + 1, user);
        return Result.success(b);
    }


    @Autowired
    MQSender sender;

    @RequestMapping("/mq/header")
    @ResponseBody
    public Result<String> header() {
		sender.sendHeader("hello,...");
        return Result.success("Hello，world");
    }

	@RequestMapping("/mq/fanout")
    @ResponseBody
    public Result<String> fanout() {
		sender.sendFanout("hello,...");
        return Result.success("Hello，world");
    }

	@RequestMapping("/mq/topic")
    @ResponseBody
    public Result<String> topic() {
		sender.sendTopic("hello,...");
        return Result.success("Hello，world");
    }

	@RequestMapping("/mq")
    @ResponseBody
    public Result<String> mq() {
		sender.send("hello,...");
        return Result.success("Hello，world");
    }
}
