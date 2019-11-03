package com.mzw.demo;

import com.mzw.bean.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StudentDemo {


    public static void main(String[] args) {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationcontext.xml");
        Student student = (Student) ac.getBean("student1");
        System.out.println(student);
    }
}
