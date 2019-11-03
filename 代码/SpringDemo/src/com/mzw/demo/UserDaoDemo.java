package com.mzw.demo;

import com.mzw.dao.UserDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class UserDaoDemo {
    public static void main(String[] args) {

        /**
         * 传统方式
         */
        UserDao userDao = new UserDao();
        System.out.println(userDao);
        UserDao userDao2 = new UserDao();
        System.out.println(userDao2);

        /**
         * Spring方式IOC（控制反转）
         * 由Spring IOC容器 对对象进行实例创建和管理
         */

        //IOC容器的创建
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("classpath:applicationcontext.xml");//类路径上下文，可以从jar包读取温江
                //new FileSystemXmlApplicationContext("src/applicationcontext.xml");//也可以添加绝对路径

        //在容器中取出Bean对象的创建、
        UserDao userDao4 = (UserDao) ctx.getBean("userDao");
        System.out.println(userDao4);
        UserDao userDao1 = (UserDao) ctx.getBean("u1");
        System.out.println(userDao1);
        UserDao userDao5 = (UserDao) ctx.getBean(UserDao.class);
        System.out.println(userDao5);

        //查看IOC容器中所有Bean
        System.out.println("当前IOC容器中所有Bean的名称");
        String [] beans = ctx.getBeanDefinitionNames();
        for(String str :beans){
            System.out.println(str);
        }
    }
}
