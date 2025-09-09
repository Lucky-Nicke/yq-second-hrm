package dao.impl;

import dao.UserDao;
import entity.User;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.DruidUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static utils.DruidUtils.getConnection;
import static utils.DruidUtils.qr;

/**
 * 用户表数据库相关
 */
public class UserDaoImpl implements UserDao {
    Connection conn;

    @Override
    public List<User> getAllUserInfo(String page, String limit) {
        try {
            conn = getConnection();
            int pageNum = page == null || page.isEmpty() ? 1 : Integer.parseInt(page);
            int pageSize = limit == null || limit.isEmpty() ? 10 : Integer.parseInt(limit);

            int startIndex = (pageNum - 1) * pageSize;
            String sql = "select * from user_inf limit ?,?";
            return qr.query(conn, sql, new BeanListHandler<>(User.class), startIndex, pageSize);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int getAllUserInfoCount() {
        try {
            conn = getConnection();
            String sql = "select count(*) from user_inf";
            Long count = qr.query(conn, sql, new ScalarHandler<>());
            return count.intValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int updateUserInfo(User user) {
        try {
            conn = getConnection();
            String sql = "update user_inf set loginname=?, PASSWORD=?, STATUS=?, username=? where id=?";
            return qr.update(conn, sql, user.getLoginname(), user.getPASSWORD(), user.getSTATUS(), user.getUsername(), user.getID());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int delUserInfo(int delId) {
        try {
            conn = getConnection();
            String sql = "delete from user_inf where id=?";
            return qr.update(conn,sql,delId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int addUserInfo(User user) {
        try {
            conn = getConnection();
            String sql = "insert into user_inf (loginname,PASSWORD,username) values (?,?,?)";
            return qr.update(conn,sql,user.getLoginname(),user.getPASSWORD(),user.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }
}
