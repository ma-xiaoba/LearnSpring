package com.mzw.demo;

import com.mzw.dao.UserDao;
import com.mzw.service.UserService;
import com.mzw.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDemo {


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
        UserService userService = (UserServiceImpl)applicationContext.getBean("userServiceImpl");
        System.out.println(userService.selectById(1));
    }
}
