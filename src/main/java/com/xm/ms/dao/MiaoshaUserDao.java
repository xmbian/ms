package com.xm.ms.dao;

import com.xm.ms.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-31 - 10:33
 * description:
 */
@Mapper
public interface MiaoshaUserDao {

    @Select("select * from miaosha_user where id =#{id}")
    MiaoshaUser getById(long id);
}
