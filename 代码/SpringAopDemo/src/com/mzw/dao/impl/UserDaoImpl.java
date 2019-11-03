package com.mzw.dao.impl;

import com.mzw.bean.User;
import com.mzw.dao.UserDao;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public User login(String name, String password) {

        User user = new User();
        user.setId(1);
        user.setName(name);
        user.setPassword(password);
        user.setRealName("张三");
        return user;
    }

    @Override
    public User selectById(int id) {
        User user = new User();
        user.setId(id);
        user.setName("user");
        user.setPassword("123456");
        user.setRealName("张三");
        return user;
    }

    @Override
    public int updateById(User user) {
        return 1;
    }
}
