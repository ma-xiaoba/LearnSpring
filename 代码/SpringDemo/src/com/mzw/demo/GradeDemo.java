package com.mzw.demo;

import com.mzw.bean.Grade;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GradeDemo {
    public static void main(String[] args) {
        //IOC容器的创建
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:applicationcontext-scope.xml");

        Grade grade = (Grade)applicationContext.getBean("grade");
        grade.info();
        System.out.println(grade);
        applicationContext.close();
    }
}
