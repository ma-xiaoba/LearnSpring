package com.mzw.dao;

import com.mzw.bean.User;

public interface UserDao {
    public User selectById(int id);
}
