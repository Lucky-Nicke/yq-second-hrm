package utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 阿里连接池工具类
 */
public class DruidUtils {
    private static final DruidDataSource dataSource;
    public static QueryRunner qr = new QueryRunner();
    private static Connection conn;

    /**
     * 初始化数据库
     */
    static {
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/hrm?useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8");
        dataSource.setUsername("root");
        dataSource.setPassword("Zjh20040301");

        dataSource.setInitialSize(5);
        dataSource.setMaxActive(20);
        dataSource.setMinIdle(3);
        dataSource.setMaxWait(3000);
        System.out.println("数据库连接成功！");
    }

    /**
     * 连接数据库
     */
    public static Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 结束资源（返回连接池，并非关闭资源）
     * @param connection 传递conn对象
     */
    public static void closeConnection(Connection connection){
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
