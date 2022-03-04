package com.bin_bx.connection;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * @Description: 测试Druid的数据库连接池技术
 * @ClassName: DruidTest
 * @Author: bxie
 * @Date: 2022/2/26 22:26
 * @Version:
 **/
public class DruidTest {

    @Test
    public void getConnection() throws Exception{

        Properties pros = new Properties();

        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

        pros.load(is);
        DataSource source = DruidDataSourceFactory.createDataSource(pros);

        Connection conn = source.getConnection();
        System.out.println(conn);

    }
}
