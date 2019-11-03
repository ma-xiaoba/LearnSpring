package com.mzw.bean;

import lombok.Data;

@Data
public class Account {
    private int id;
    private int cardId;
    private String name;
    private  double balance;
}
