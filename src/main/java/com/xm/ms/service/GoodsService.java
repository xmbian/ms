package com.xm.ms.service;

import com.xm.ms.dao.GoodsDao;
import com.xm.ms.dao.MiaoshaUserDao;
import com.xm.ms.domain.MiaoshaUser;
import com.xm.ms.exception.GlobalException;
import com.xm.ms.redis.MiaoshaUserKey;
import com.xm.ms.redis.RedisService;
import com.xm.ms.result.CodeMsg;
import com.xm.ms.util.MD5Util;
import com.xm.ms.util.UUIDUtil;
import com.xm.ms.vo.GoodsVo;
import com.xm.ms.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-08-01 - 21:43
 * description:
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

}
