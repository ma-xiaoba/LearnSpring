package com.mzw.dao.impl;

import com.mzw.bean.User;
import com.mzw.dao.UserDao;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl implements UserDao {
    public User selectById(int id){
        User user = new User();
        user.setName("张三");
        user.setId(1);
        user.setPassword("password");
        return user;
    }
}
