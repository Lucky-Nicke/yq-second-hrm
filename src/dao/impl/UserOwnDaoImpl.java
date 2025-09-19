package dao.impl;

import DTO.CheckPwdInfo;
import DTO.RegisterInfo;
import DTO.UserInfo;
import dao.UserOwnDao;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.DruidUtils;

import java.sql.Connection;
import java.sql.SQLException;

import static utils.DruidUtils.qr;

public class UserOwnDaoImpl implements UserOwnDao {
    Connection conn = null;

    @Override
    public UserInfo login(String loginname, String password) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "select * from user_inf where loginname=? and PASSWORD=?";
            return qr.query(conn, sql, new BeanHandler<>(UserInfo.class), loginname, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public void changeStatus(String loginname, String option) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "update user_inf set status=? where loginname=?";
            switch (option) {
                case "login":
                    qr.update(conn, sql, 2, loginname);
                    break;
                case "logout":
                    qr.update(conn, sql, 1, loginname);
                    break;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public CheckPwdInfo checkPassword(String loginname, String oldPassword) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "select * from user_inf where loginname=? and PASSWORD=?";
            return qr.query(conn, sql, new BeanHandler<>(CheckPwdInfo.class), loginname, oldPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int changePassword(String loginname, String newPassword) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "update user_inf set PASSWORD=? where loginname=?";
            return qr.update(conn, sql, newPassword, loginname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public RegisterInfo checkLoginname(String loginname) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "select * from user_inf where loginname=?";
            return qr.query(conn, sql, new BeanHandler<>(RegisterInfo.class), loginname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }

    @Override
    public int registerNewUser(String registername,String password) {
        try {
            conn = DruidUtils.getConnection();
            String sql = "insert into user_inf (loginname, PASSWORD, username) VALUES (?, ?, '普通成员')";
            return qr.update(conn, sql, registername,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DruidUtils.closeConnection(conn);
        }
    }
}
