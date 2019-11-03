package com.mzw.dao;

import com.mzw.bean.User;

public interface UserDao {
    public User login(String name,String password);
    public User selectById(int id);
    public int updateById(User user);
}
