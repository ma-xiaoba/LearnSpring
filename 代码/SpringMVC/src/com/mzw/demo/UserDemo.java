package com.mzw.demo;


import com.mzw.bean.User;
import com.mzw.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDemo {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationcontext.xml");
        UserService userService = (UserService) applicationContext.getBean("userServiceImpl");

        System.out.println("IOC 容器中的实例");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            System.out.println(name);
        }

        User user = new User();
        user.setName("lisi");
        user.setAge(23);
        user.setRemark("study English");
        //System.out.println(userService.insert(user));
       // System.out.println(userService.selectList().toString());
    }
}
