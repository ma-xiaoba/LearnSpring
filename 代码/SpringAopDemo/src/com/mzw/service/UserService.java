package com.mzw.service;

import com.mzw.bean.User;

public interface UserService {
    public User login(String name, String password);
    public User selectById(int id);
    public int updateById(User user);
    public void testException();
}
