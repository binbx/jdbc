package com.bin_bx.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.bin_bx.bean.Order;
import com.bin_bx.preparedstatement.util.JDBCUtils;
import org.junit.Test;

/**
 * 
 * @Description 针对于Order表的通用的查询操作
 * @author bxie
 * @version 
 * @date 上午10:43:58
 *
 */
public class OrderForQuery {
	/**
	 * 针对于表的字段名与类的属性名不相同的情况：
	 * 1. 必须声明sql时，使用类的属性名来命名字段的别名
	 * 2. 使用ResultSetMetaData时，需要使用getColumnLabel()来替换getColumnName(),
	 *    获取列的别名。
	 *  说明：如果sql中没有给字段其别名，getColumnLabel()获取的就是列名
	 */
	@Test
	public void testOrderForQuery(){
		String sql = "select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id = ?";
		Order order = orderForQuery(sql,1);
		System.out.println(order);
	}
	
	/**
	 * 
	 * @Description 通用的针对于order表的查询操作
	 * @author bxie
	 * @date 上午10:51:12
	 * @return
	 * @throws Exception 
	 */
	public Order orderForQuery(String sql,Object...args){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取数据库的连接
			conn = JDBCUtils.getConnection();
			//2.预编译sql语句，返回PreparedStatement的实例
			ps = conn.prepareStatement(sql);
			//填充占位符
			for(int i = 0;i < args.length;i++){
				ps.setObject(i + 1, args[i]);
			}
			
			//执行，获取结果集
			rs = ps.executeQuery();
			//获取结果集的元数据
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取列数
			int columnCount = rsmd.getColumnCount();
			if(rs.next()){
				Order order = new Order();
				for(int i = 0;i < columnCount;i++){
					//获取每个列的列值:通过ResultSet
					Object columnValue = rs.getObject(i + 1);
					//通过ResultSetMetaData
					//获取列的列名：getColumnName() --不推荐使用
					//获取列的别名：getColumnLabel()
//					String columnName = rsmd.getColumnName(i + 1);
					String columnLabel = rsmd.getColumnLabel(i + 1);
					
					//通过反射，将对象指定名columnName的属性赋值为指定的值columnValue
					Field field = Order.class.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(order, columnValue);
				}
				
				return order;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
	
	//查询
	@Test
	public void testQuery1(){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//1.获取数据库的连接
			conn = JDBCUtils.getConnection();
			//2.预编译sql语句，返回PreparedStatement的实例
			String sql = "select order_id,order_name,order_date from `order` where order_id = ?";
			ps = conn.prepareStatement(sql);
			//3.填充占位符
			ps.setObject(1, 1);

			//执行,并返回结果集
			rs = ps.executeQuery();
			//处理结果集
			if(rs.next()){
				//获取当前这条数据的各个字段值
				int id = (int) rs.getObject(1);
				String name = (String) rs.getObject(2);
				Date date = (Date) rs.getObject(3);

				//将数据封装为一个对象（推荐）
				Order order = new Order(id, name, date);
				System.out.println(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//释放资源
			JDBCUtils.closeResource(conn, ps, rs);
		}
	}
	
}
