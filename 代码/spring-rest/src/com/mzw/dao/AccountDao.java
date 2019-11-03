package com.mzw.dao;

import com.mzw.bean.Account;

public interface AccountDao {
    /**
     * 转账功能
     * 1. 根据卡号ID查询账户信息
     * 2. 判断余额是否足够
     * 3. 转账人账余额减少
     * 4. 收款人账户余额增加
     */
    /**
     * 根据卡号信息查询余额
     * @param cardId
     * @return
     */
    public Account selectByCardId(int cardId);

    public int updateSubByCardId(int cardId,double balance);

    public  int updateAddByCardId(int cardId,double balance);
}
