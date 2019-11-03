package com.mzw.bean;

public class Grade {
    private int gid;
    private String gname;


    public void init(){
        if(this.gid>150 || this.gid<0){
            this.gid = 20;
        };
        System.out.println("3.初始化方法");
    }

    public void destroy(){
        System.out.println("4.销毁Grade");
    }

    public Grade(){
        System.out.println("1.无参构造方法");
    }

    public void info(){
        System.out.println("使用此方法");
    }

    public int getGid() {
        return gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setGname(String gname) {
        this.gname = gname;
        System.out.println("2.进行set注入的方式赋值");
    }

    @Override
    public String toString() {
        return "{gid = "+gid+", gname = "+gname+"}";
    }
}
