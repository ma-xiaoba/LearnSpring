package com.mzw.service.impl;

import com.fasterxml.jackson.databind.deser.std.ThrowableDeserializer;
import com.mzw.bean.Account;
import com.mzw.dao.AccountDao;
import com.mzw.exception.AccountException;
import com.mzw.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;


    /**
     *1、查询转出方帐户是否存在cardId
     * 2、查看转出方的余额是否满足转帐要求
     * 3、查询转入方帐户是否存在
     * 4、转出方卡号减去相应金额
     * 5、转入方卡号增加相应金额
     *
     * @param cardId
     * @param targetCardId
     * @param balance
     * @return
     */

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int transfer(int cardId, int targetCardId, double balance) {
        Account account1 = accountDao.selectByCardId(cardId);
        if(account1==null){
            throw new AccountException(cardId+" : 账号不存在");
        }else if (account1.getBalance()<balance){
            throw new AccountException(cardId+": 账户余额不足");
        }
        Account account2 = accountDao.selectByCardId(targetCardId);
        if(account2==null){
            throw new AccountException(targetCardId+" : 账号不存在");
        }
        int num1 = accountDao.updateSubByCardId(cardId, balance);
        if(num1<1){
            throw new AccountException(cardId+" : 转账失败");
        }
        int num2 = accountDao.updateAddByCardId(targetCardId, balance);
        if (num2<1){
            throw new AccountException(targetCardId+" : 转账失败");
        }
        System.out.println(cardId+" ======》 "+targetCardId+"：转账成功");
        return 1;
    }


    /***
     * 给全体员工转账
     * @param cardId
     * @param targetCardIds
     * @param balance
     * @param accountService
     * @return
     * @throws AccountException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int transferA11(int cardId, int[] targetCardIds, double balance,AccountService accountService) throws AccountException {
        for (int i=0;i <targetCardIds.length;i++){
            accountService.transfer (cardId,targetCardIds[i],balance) ;
        }
        return 1;
    }
}
