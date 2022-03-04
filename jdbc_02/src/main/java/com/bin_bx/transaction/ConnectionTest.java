package com.bin_bx.util.transaction;

import com.bin_bx.util.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;

/**
 * @Description: 测试连接是否成功
 * @Author: bxie
 * @ClassName: ConnectionTest
 * @Version:
 * @Date: 2022/2/26 16:16
 **/
public class ConnectionTest {

    //测试连接是否成功
    @Test
    public void testGetConnection() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn);
    }
}
