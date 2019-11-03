package com.mzw.util;

import lombok.Data;

import java.util.List;

@Data
public class PageUtil {
    private int pageSize;   //当前页
    private int pageNum;    //页大小 row：返回的最大记录数
    private int offsize;    //偏移量
    private int count;      //总记录数
    private int countPage;  //总页数
    private List<?> list;      //分页查询的结果集信息

    public PageUtil(){}
    public PageUtil(int pageNum,int pageSize,int count){
        //当前页的判断操作
        if(pageNum<1){
            this.pageNum = 1;
        } else{
            this.pageNum = pageNum;
        }
        //页大小
        if(pageSize<3){
            this.pageSize=3;
        }else{
            this.pageSize=pageSize;
        }
        //偏移量offsize
        this.offsize = (this.pageNum-1)*this.pageSize;
        this.count = count;
        this.countPage = this.count%this.pageSize==0?this.count/this.pageSize:this.count/this.pageSize+1;
    }
}
