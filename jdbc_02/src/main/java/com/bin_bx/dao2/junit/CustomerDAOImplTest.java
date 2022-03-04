package com.bin_bx.dao2.junit;

import com.bin_bx.bean.Customer;
import com.bin_bx.dao2.impl.CustomerDAOImpl;
import com.bin_bx.util.JDBCUtils;
import com.bin_bx.util.JDBCUtils2;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Description: //TODO
 * @ClassName: CustomerDAOImplTest
 * @Author: bxie
 * @Date: 2022/2/26 19:47
 * @Version:
 **/
public class CustomerDAOImplTest {

    private CustomerDAOImpl dao = new CustomerDAOImpl();

    /**
     * @Description 将cust对象添加到数据库中
     */
    @Test
    public void testInsert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Customer cust = new Customer(1, "于小飞", "xiaofei@126.com",new Date(43534646435L));
            dao.insert(conn, cust);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResource(conn, null);
        }
    }

    /**
     * @Description 针对指定的id，删除表中的一条记录
     */
    @Test
    public void testDeleteById() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            dao.deleteById(conn,13);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    /**
     * @Description 针对内存中的cust对象，去修改数据表中指定的记录
     */
    @Test
    public void testUpdate() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1976-11-01");

            Customer cust = new Customer(18,"贝多芬","beiduofen@126.com",new Date(date.getTime()));
            dao.update(conn,cust);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    /**
     * @Description 针对指定的id查询得到对应的Customer对象
     */
    @Test
    public void testGetCustomerById() {
        Connection conn = null;
        try {
            conn = JDBCUtils2.getConnection3();  //JDBCUtils.getConnection
            Customer cust = dao.getCustomerById(conn, 22);
            System.out.println(cust);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    /**
     * @Description 查询表中的所有记录构成的集合
     */
    @Test
    public void testGetAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Customer> list = dao.getAll(conn);
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    /**
     * @Description 返回数据表中的数据的条目数
     */
    @Test
    public void testGetCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Long count = dao.getCount(conn);
            System.out.println("表中的记录数为：" + count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }

    /**
     * @Description 返回数据表中最大的生日
     */
    @Test
    public void testGetMaxBirth() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Date maxBirth = dao.getMaxBirth(conn);
            System.out.println("最大的生日为：" + maxBirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,null);
        }
    }
}
