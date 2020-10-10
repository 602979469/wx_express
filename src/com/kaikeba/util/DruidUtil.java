package com.kaikeba.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 德鲁伊跑龙套
 *
 * @author Faker
 * @date 2020/09/30
 */
public class DruidUtil {

    /**
     * 数据源
     */
    private static DataSource dataSource;
    static{
        try {
            Properties ppt = new Properties();
            InputStream stream = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            ppt.load(stream);
            dataSource = DruidDataSourceFactory.createDataSource(ppt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据源
     *
     * @return {@link DataSource}
     */
    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 获取连接
     * 从连接池中取出一个连接给用户
     *
     * @return {@link Connection}
     */
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    /**
     * 关闭
     *
     * @param conn  康涅狄格州
     * @param state 状态
     * @param rs    rs
     */
    public static void close(Connection conn, Statement state, ResultSet rs){
        try {
            if(rs!=null) {
                rs.close();
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        try {
            if(state!=null) {
                state.close();
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        try {
            if(conn!=null) {
                conn.close();
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }
}
