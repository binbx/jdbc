package com.bin_bx.dao2;

import com.bin_bx.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Description: 此接口用于规范针对于customers表的常用操作
 * @Author: bxie
 * @ClassName: CustomerDAO
 * @Version:
 * @Date: 2022/2/26 17:25
 **/
public interface CustomerDAO {

    /**
     * @Description 将cust对象添加到数据库中
     * @param conn
     * @param cust
     */
    void insert(Connection conn, Customer cust);
    /**
     * @Description 针对指定的id，删除表中的一条记录
     * @param conn
     * @param id
     */
    void deleteById(Connection conn,int id);
    /**
     * @Description 针对内存中的cust对象，去修改数据表中指定的记录
     * @param conn
     * @param cust
     */
    void update(Connection conn,Customer cust);
    /**
     * @Description 针对指定的id查询得到对应的Customer对象
     * @param conn
     * @param id
     * @return
     */
    Customer getCustomerById(Connection conn,int id);
    /**
     * @Description 查询表中的所有记录构成的集合
     * @param conn
     * @return
     */
    List<Customer> getAll(Connection conn);
    /**
     * @Description 返回数据表中的数据的条目数
     * @param conn
     * @return
     */
    Long getCount(Connection conn);

    /**
     * @Description 返回数据表中最大的生日
     * @param conn
     * @return
     */
    Date getMaxBirth(Connection conn);
}
