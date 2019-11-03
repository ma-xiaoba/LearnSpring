package com.mzw.service.impl;

import com.mzw.bean.User;
import com.mzw.dao.UserDao;
import com.mzw.dao.impl.UserDaoImpl;
import com.mzw.service.UserService;
import com.mzw.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    UserDao userDao = new UserDaoImpl();

    @Override
    public int insert(User user) {
       return userDao.insert(user);
    }

    @Override
    public int update(User user) {
        return userDao.update(user);
    }

    @Override
    public int deleteById(int id) {
        return userDao.deleteById(id);
    }

    @Override
    public User selectById(int id) {
        return userDao.selectById(id);
    }

    @Override
    public int[] insertBatch(List<User> userList) {
        return userDao.insertBatch(userList);
    }

    @Override
    public List<User> selectList() {
        return userDao.selectList();
    }

    @Override
    public int count() {
        return userDao.count();
    }

    @Override
    public PageUtil selectByPage(int pagenum, int pagesize) {
        int count = count();
        PageUtil pageUtil =new PageUtil(pagenum,pagesize,count);
        pageUtil.setList(userDao.selectByPage(pageUtil.getOffsize(),pageUtil.getPageSize()));
        return pageUtil;
    }
}
