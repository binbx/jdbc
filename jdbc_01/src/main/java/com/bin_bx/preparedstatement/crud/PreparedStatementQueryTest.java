package com.bin_bx.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import com.bin_bx.bean.Customer;
import com.bin_bx.bean.Order;
import com.bin_bx.preparedstatement.util.JDBCUtils;
import org.junit.Test;

/**
 * @Description 使用PreparedStatement实现针对于不同表的通用的查询操作
 * @author bxie
 * @version
 * @date 上午11:32:55
 */
public class PreparedStatementQueryTest {

	/**
	 * 测试 getForList
	 */
	@Test
	public void testGetForList(){
		
		String sql = "select id,name,email from customers where id < ?";
		List<Customer> list = getForList(Customer.class,sql,12);
		list.forEach(System.out::println);
		
		String sql1 = "select order_id orderId,order_name orderName from `order`";
		List<Order> orderList = getForList(Order.class, sql1);
		orderList.forEach(System.out::println);
	}

	/**
	 * @Description 针对于不同的表的通用的查询操作，返回表中的多条记录
	 * @param clazz
	 * @param sql
	 * @param args
	 * @param <T>
	 * @return
	 */
	public <T> List<T> getForList(Class<T> clazz,String sql, Object... args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取数据库连接
			conn = JDBCUtils.getConnection();
			//2.预编译sql语句，返回PreparedStatement的实例
			ps = conn.prepareStatement(sql);
			//3.填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			//4.执行,并返回结果集
			rs = ps.executeQuery();
			// 获取结果集的元数据 :ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			// 通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();
			//创建集合对象
			ArrayList<T> list = new ArrayList<T>();
			//处理结果集
			while (rs.next()) {
				T t = clazz.newInstance();
				// 处理结果集一行数据中的每一个列:给t对象指定的属性赋值
				for (int i = 0; i < columnCount; i++) {
					// 获取列值
					Object columValue = rs.getObject(i + 1);

					// 获取每个列的列名
					// String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);

					// 给t对象指定的columnName属性，赋值为columValue：通过反射
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columValue);
				}
				list.add(t);
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}



	/**
	 * 测试getInstance
	 */
	@Test
	public void testGetInstance(){
		String sql = "select id,name,email from customers where id = ?";
		Customer customer = getInstance(Customer.class,sql,12);
		System.out.println(customer);
		
		String sql1 = "select order_id orderId,order_name orderName from `order` where order_id = ?";
		Order order = getInstance(Order.class, sql1, 1);
		System.out.println(order);
	}

	/**
	 * @Description 针对于不同的表的通用的查询操作，返回表中的一条记录
	 * @author bxie
	 * @date 上午11:42:23
	 * @param clazz
	 * @param sql
	 * @param args
	 * @return
	 */
	public <T> T getInstance(Class<T> clazz,String sql, Object... args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取数据库连接
			conn = JDBCUtils.getConnection();
			//2.预编译sql语句，返回PreparedStatement的实例
			ps = conn.prepareStatement(sql);
			//3.填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}

			//4.执行,并返回结果集
			rs = ps.executeQuery();
			// 获取结果集的元数据 :ResultSetMetaData
			ResultSetMetaData rsmd = rs.getMetaData();
			// 通过ResultSetMetaData获取结果集中的列数
			int columnCount = rsmd.getColumnCount();
			//处理结果集
			if (rs.next()) {
				T t = clazz.newInstance();
				// 处理结果集一行数据中的每一个列
				for (int i = 0; i < columnCount; i++) {
					// 获取列值
					Object columValue = rs.getObject(i + 1);

					// 获取每个列的列名
					// String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);

					// 给t对象指定的columnName属性，赋值为columValue：通过反射
					Field field = clazz.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(t, columValue);
				}
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}

}
