package com.mzw.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mzw.bean.User;
import com.mzw.service.UserService;
import com.mzw.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceDemo {
    public static void main(String[] args) throws JsonProcessingException {
        /***
         * 对象转JSON
         * JSON转对象
         * objectMapper.writeValueAsString() 对象转jackson字符串
         */

        //读取配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_core.xml");

        ObjectMapper objectMapper = new ObjectMapper();
        UserService userService = (UserService)applicationContext.getBean("userServiceImpl");
        User user = userService.selectById(14);
        String s = objectMapper.writeValueAsString(user);
        System.out.println(s);

    }
}
