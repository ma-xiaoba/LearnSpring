package com.mzw.service;

public interface AccountService {
    public int transfer(int cardId,int targetCardId,double balance);
    public int transferA11(int cardId, int[] targetCardIds, double balance,AccountService accountService);
}
