package com.mzw.bean;

import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private int age;
    private List<Grade> grades;
    private List<String> infos;

    public Student(){
        System.out.println("Student 无参构造");
    }
    public String getName() {
        return name;
    }


    public List getGrades() {
        return grades;
    }

    public List<String> getInfos() {
        return infos;
    }

    public void setGrades(List grade) {
        this.grades = grade;
    }

    public void setInfos(List<String> infos) {
        this.infos = infos;
    }

    public Student(int age, String name){
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{ age = "+age+", name"+name+", grade = "+grades.toString()+", infos = "+infos.toString()+"}";
    }
}
