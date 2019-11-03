package com.mzw.demo;

import com.mzw.bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDemo {



    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationcontext.xml");
        User user = (User) applicationContext.getBean("user1");
        System.out.println(user);
    }
}
