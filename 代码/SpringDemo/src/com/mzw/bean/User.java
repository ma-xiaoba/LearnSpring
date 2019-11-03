package com.mzw.bean;

public class User {
    private int id ;
    private String name;
    private String remark;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void init(){
        System.out.println("User->init");
    }

    public User(){
        System.out.println("User创建");
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRemark() {
        return remark;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "{id = "+id+", name = "+name+", role = "+role.toString()+", remark = "+remark+"}";
    }
}
