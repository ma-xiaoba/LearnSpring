package com.mzw.service.impl;

import com.mzw.bean.User;
import com.mzw.dao.UserDao;
import com.mzw.dao.impl.UserDaoImpl;
import com.mzw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String name, String password) {
        return userDao.login(name,password);
    }

    @Override
    public User selectById(int id) {
        return userDao.selectById(id);
    }

    @Override
    public int updateById(User user) {
        return userDao.updateById(user);
    }

    public void testException(){

           User user = null;
           user.getName();

    }
}
