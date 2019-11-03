package com.mzw.web.controller;

import com.mzw.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @ResponseBody
    @RequestMapping(value = "transfer",produces = {"application/text;charset=UTF-8"})
    public String transfer(@RequestParam(value = "cardId")int cardId,@RequestParam(value = "targetCardId")int targetCardId,@RequestParam(value = "balance")int balance){
        String msg = cardId+" ======》 "+targetCardId+"转账"+balance+"元";
        try {
            accountService.transfer(cardId, targetCardId, balance);
        }catch (Exception e) {
            msg = e.getMessage();
            System.out.println("转账成功");
        }
        return msg;
    }
}
