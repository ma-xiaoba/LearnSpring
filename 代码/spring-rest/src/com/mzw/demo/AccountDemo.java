package com.mzw.demo;

import com.mzw.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AccountDemo {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring_core.xml");
        AccountService accountService = applicationContext.getBean(AccountService.class);
        int cardId = 10001;
        int targetCardId = 10002;
        int[] targetCardIds = {10002,10003,10004,10005};
        int balance = 10;
        try{
            accountService.transferA11(cardId,targetCardIds,balance,accountService);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }
}
