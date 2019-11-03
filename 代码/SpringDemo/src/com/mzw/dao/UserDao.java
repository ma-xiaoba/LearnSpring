package com.mzw.dao;

import com.mzw.bean.User;

public class UserDao {

    public UserDao(){
        System.out.println("UserDao创建");
    }

    public User selectById(int id){
        User user = new User();
        user.setId(1);
        user.setName("zhanghsan");
        user.setRemark("我爱学习");
        return  user;
    }
}
