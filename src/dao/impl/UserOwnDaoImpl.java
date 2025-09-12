package dao.impl;

import dao.UserOwnDao;
import entity.User;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.DruidUtils;

import java.sql.Connection;
import java.sql.SQLException;

import static utils.DruidUtils.qr;

public class UserOwnDaoImpl implements UserOwnDao {
    Connection conn = null;

    @Override
    public User login(String loginname, String password) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "select * from user_inf where loginname=? and PASSWORD=?";
            return qr.query(conn,sql,new BeanHandler<>(User.class),loginname,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }
}
