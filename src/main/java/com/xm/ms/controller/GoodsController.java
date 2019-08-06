package com.xm.ms.controller;

import com.xm.ms.domain.MiaoshaUser;
import com.xm.ms.redis.GoodsKey;
import com.xm.ms.redis.RedisService;
import com.xm.ms.result.Result;
import com.xm.ms.service.GoodsService;
import com.xm.ms.service.MiaoshaUserService;
import com.xm.ms.vo.GoodsVo;
import com.xm.ms.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring4.context.SpringWebContext;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-01 - 11:01
 * description:
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

   /* @RequestMapping("/to_list")
    String toList(HttpServletResponse response,Model model,
                   @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                   @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken) {
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser user = miaoshaUserService.getByToken(response,token);
        model.addAttribute("user",user);
        return "goods_list";
    }*/

    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user) {
        model.addAttribute("user",user);
        //查询商品列表
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);
//        return "goods_list";
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //手动渲染
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "", html);//页面缓存（所有用户访问同一页面）,缓存时间为60秒
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletResponse response, HttpServletRequest request, Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user",user);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startAt = goods.getStartDate().getTime();//转换成毫秒
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();//获取当前时间

        int miaoshaStatus = 0;
        int remainSeconds = 0;

        if(now < startAt) {//秒杀还没开始，倒计时
            miaoshaStatus = 0;
            remainSeconds = (int) ((startAt - now)/1000);
        } else if(now > endAt) { //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行时
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

//        return "goods_detail";
        SpringWebContext ctx = new SpringWebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap(), applicationContext);
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);//URL缓存(不同goodsId访问页面不同),缓存时间为60秒
        }
        return html;
    }


}
