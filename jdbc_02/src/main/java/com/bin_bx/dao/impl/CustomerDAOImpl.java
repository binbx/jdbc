package com.bin_bx.dao.impl;

import com.bin_bx.bean.Customer;
import com.bin_bx.dao.BaseDAO;
import com.bin_bx.dao.CustomerDAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Description: //TODO
 * @ClassName: CustomerDAOImpl
 * @Author: bxie
 * @Date: 2022/2/26 17:55
 * @Version:
 **/
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {

    /**
     * @Description 将cust对象添加到数据库中
     * @param conn
     * @param cust
     */
    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "insert into customers(name,email,birth) values(?,?,?);";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth());
    }
    /**
     * @Description 针对指定的id，删除表中的一条记录
     * @param conn
     * @param id
     */
    @Override
    public void deleteById(Connection conn,int id) {
        String sql = "delete from customers where id = ?";
        update(conn,sql,id);
    }
    /**
     * @Description 针对内存中的cust对象，去修改数据表中指定的记录
     * @param conn
     * @param cust
     */
    @Override
    public void update(Connection conn, Customer cust) {
        String sql = "update customers set name = ?,email = ?,birth = ? where id = ?";
        update(conn, sql,cust.getName(),cust.getEmail(),cust.getBirth(),cust.getId());
    }
    /**
     * @Description 针对指定的id查询得到对应的Customer对象
     * @param conn
     * @param id
     * @return
     */
    @Override
    public Customer getCustomerById(Connection conn,int id){
        String sql = "select id,name,email,birth from customers where id = ?";
        Customer customer = getInstance(conn,Customer.class, sql,id);
        return customer;
    }
    /**
     * @Description 查询表中的所有记录构成的集合
     * @param conn
     * @return
     */
    @Override
    public List<Customer> getAll(Connection conn){
        String sql = "select id,name,email,birth from customers";
        List<Customer> list = getForList(conn, Customer.class, sql);
        return list;
    }
    /**
     * @Description 返回数据表中的数据的条目数
     * @param conn
     * @return
     */
    @Override
    public Long getCount(Connection conn){
        String sql = "select count(*) from customers";
        return getValue(conn, sql);
    }

    /**
     * @Description 返回数据表中最大的生日
     * @param conn
     * @return
     */
    @Override
    public Date getMaxBirth(Connection conn){
        String sql = "select max(birth) from customers";
        return getValue(conn, sql);
    }
}
