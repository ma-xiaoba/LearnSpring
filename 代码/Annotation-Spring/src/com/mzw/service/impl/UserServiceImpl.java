package com.mzw.service.impl;
import com.mzw.bean.User;
import com.mzw.dao.UserDao;
import com.mzw.dao.impl.UserDaoImpl;
import com.mzw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired(required = false)
    @Qualifier(value = "UserDaoImpl")
    private UserDao userDao;


    @Override
    public User selectById(int id) {
        if (userDao == null)
            return null;
        User user = userDao.selectById(1);
        return user;
    }
}
