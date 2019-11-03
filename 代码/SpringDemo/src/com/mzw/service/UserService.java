package com.mzw.service;

import com.mzw.bean.User;
import com.mzw.dao.UserDao;

public class UserService {
    UserDao userDao = new UserDao();

    public UserService(){
        System.out.println("UserService创建");
    }

    public User selectById(int id){
        return userDao.selectById(id);
    }
}
