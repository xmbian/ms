package com.xm.ms.service;

import com.xm.ms.dao.MiaoshaUserDao;
import com.xm.ms.domain.MiaoshaUser;
import com.xm.ms.exception.GlobalException;
import com.xm.ms.redis.MiaoshaUserKey;
import com.xm.ms.redis.RedisService;
import com.xm.ms.result.CodeMsg;
import com.xm.ms.util.MD5Util;
import com.xm.ms.util.UUIDUtil;
import com.xm.ms.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-31 - 10:36
 * description:
 */
@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
     //判断手机号是否存在
        MiaoshaUser user = getById(Long.parseLong(mobile));//TOTHINK Long.valueOf()与Long.parseLong()的区别
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.fromPassToDBPass(formPass, dbSalt);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token,token,user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }
}
