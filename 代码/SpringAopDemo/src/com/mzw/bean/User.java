package com.mzw.bean;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String password;
    private String realName;

    public void  setId(int id){
        this.id = id;
    }
}
