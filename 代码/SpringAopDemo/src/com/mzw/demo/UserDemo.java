package com.mzw.demo;

import com.mzw.bean.User;
import com.mzw.service.UserService;
import com.mzw.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDemo {
    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");
       // User user = userService.login("admin", "qwe");
        userService.selectById(1);
       //userService.testException();
    }
}
