package com.xm.ms.service;

import com.xm.ms.dao.UserDao;
import com.xm.ms.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xmbian
 * @version 1.0
 * Email: 1486573@qq.com
 * date: 2019-07-30 - 18:49
 * description:
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id) {
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User u1 = new User();
        u1.setId(2);
        u1.setName("222");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("111");
        userDao.insert(u2);

        return true;
    }
}
