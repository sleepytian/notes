package utils;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class DBUtils {

    // 链接数据库要用到的属性值以及bundle对象
    /*
    private static final ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.jdbc");
    private static final String driver = resourceBundle.getString("driver");
    private static final String url = resourceBundle.getString("url");
    private static final String username = resourceBundle.getString("username");
    private static final String password = resourceBundle.getString("password");
     */


    // druid数据库数据源对象.
    private static DataSource dataSource = null;

    static {

        // 传统方法注册驱动
        /*
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("注册driver驱动出错.");
            e.printStackTrace();
        }
        */


        InputStream resourceInputStream = DBUtils.class.getClassLoader().getResourceAsStream("resources/jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceInputStream);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("载入jdbc.properties失败.");
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("创建druid连接池数据源失败.");
        }
    }

    /**
     * 获取数据库连接对象
     *
     * @return 返回数据库连接对象
     */
    public static Connection getConnection() throws SQLException {
        // druid创建数据库链接
        return dataSource.getConnection();
        // 使用connector获取链接
        //return DriverManager.getConnection(url, username, password);
    }

    /**
     * @param connection connection
     * @param statement statement
     */
    public static void closeResources(Connection connection, Statement statement) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        closeResources(connection, statement);
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
