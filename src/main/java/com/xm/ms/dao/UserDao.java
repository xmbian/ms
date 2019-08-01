package com.xm.ms.dao;


import com.xm.ms.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-30 - 18:41
 * description:
 */
@Mapper
public interface UserDao {
    @Select("select * from user where id = #{id}")
    User getById(@Param("id") int id);

    @Insert("insert into user(id,name) values(#{id}, #{name})")
    int insert(User user);
}
