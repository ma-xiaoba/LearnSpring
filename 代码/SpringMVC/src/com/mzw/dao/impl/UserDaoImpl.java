package com.mzw.dao.impl;


import com.mzw.bean.User;
import com.mzw.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public int insert(User user) {
        String sql = "insert into user(name,age,remark) values(?,?,?)";
        int num = jdbcTemplate.update(sql,user.getName(),user.getAge(),user.getRemark());
        return num;
    }

    @Override
    public int update(User user) {
        String sql = "update user set name = ?,age = ?,remark=? where id = ?";
        int num = jdbcTemplate.update(sql,user.getName(),user.getAge(),user.getRemark(),user.getId());
        return num;
    }

    @Override
    public int deleteById(int id) {
        String sql = "delete from user where id = ?";
        int num = jdbcTemplate.update(sql,id);
        return num;
    }



    @Override
    public User selectById(int id) {
        RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
        String sql = "select id,name,age,remark from user where id = ?";
        try{
            return jdbcTemplate.queryForObject(sql, userRowMapper, id);
        }catch(EmptyResultDataAccessException em){
            System.err.println(em.getMessage());
        }
        return null;
    }


    /***
     * 批量处理
     */
    @Override
    public int[] insertBatch(List<User> userList) {
        String sql = "update user set name = ?,age = ?,remark=? where id = ?";
        List<Object[]> objects = new ArrayList<>();
        for(User user:userList){
            Object[] objects1 = new Object[3];
            objects1[0] = user.getName();
            objects1[1] = user.getAge();
            objects1[2] = user.getRemark();
            objects.add(objects1);
        }
        return jdbcTemplate.batchUpdate(sql,objects);
    }

    @Override
    public List<User> selectList() {
        RowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);
        String sql = "select id,name,age,remark from user";

        try{
            return jdbcTemplate.query(sql, userRowMapper);
        }catch(EmptyResultDataAccessException em){
            System.err.println(em.getMessage());
        }
        return null;
    }

    @Override
    public int count(){
        String sql = "select count(*) from user";
        return jdbcTemplate.queryForObject(sql,Integer.class);
    }

}
