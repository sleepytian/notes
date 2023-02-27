package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class old_DBUtils {

    private static String url = null;
    private static String driver = null;
    private static String username = null;
    private static String password = null;

    static {
        // 静态代码块负责注册驱动
        InputStream resourceAsStream = old_DBUtils.class.getClassLoader().getResourceAsStream("resources/jdbc.properties");
        Properties properties = new Properties();
        try {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            System.out.println("获取properties失败.");
            e.printStackTrace();
        }

        driver = properties.getProperty("driver");
        username = properties.getProperty("username");
        password = properties.getProperty("password");
        url = properties.getProperty("url");

        // 注册驱动
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("注册驱动失败.");
            e.printStackTrace();
        }
    }

    /**
     * 用于获取数据库链接的函数
     * @return 返回一个connection对象
     * @throws Exception 抛出异常
     */
    public Connection getConnection () throws Exception {
        // 获取数据库链接
        return DriverManager.getConnection(url, username, password);
    }

    public boolean insert() {

        return false;
    }


}
