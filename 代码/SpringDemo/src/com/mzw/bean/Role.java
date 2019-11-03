package com.mzw.bean;

public class Role {
    private String rname;

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    @Override
    public String toString() {
        return "{rname = "+rname+"}";
    }
}
