package dao.impl;

import dao.UserDao;
import entity.User;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DruidUtils;

import java.sql.SQLException;
import java.util.List;

import static utils.DruidUtils.*;

public class UserDaoImpl implements UserDao {
    List<User> userList = null;

    @Override
    public List<User> getAllUserInfo() {
        try {
            String sql = "select * from user_inf";
            userList = qr.query(getConnection(),sql,new BeanListHandler<>(User.class));
            return userList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DruidUtils.closeConnection(getConnection());
        }
    }
}
