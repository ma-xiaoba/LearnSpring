package com.mzw.dao.impl;

import com.mzw.bean.Account;
import com.mzw.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Account selectByCardId(int cardId) {
        String sql = "select * from account where cardid = ?";
        RowMapper<Account> rowMapper = new BeanPropertyRowMapper<>(Account.class);
        Account account = null;
        try {
            account = jdbcTemplate.queryForObject(sql, rowMapper, cardId);
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        return account;
    }

    @Transactional
    public int updateSubByCardId(int cardId, double balance) {
        String sql = "update account set balance = balance-? where cardid = ?";
        return jdbcTemplate.update(sql,balance,cardId);
    }

    @Transactional
    public int updateAddByCardId(int cardId, double balance) {
        String sql = "update account set balance = balance+? where cardid = ?";
        return jdbcTemplate.update(sql,balance,cardId);
    }
}
