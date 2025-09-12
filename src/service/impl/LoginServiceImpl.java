package service.impl;

import dao.UserOwnDao;
import dao.impl.UserOwnDaoImpl;
import entity.User;
import service.LoginService;

public class LoginServiceImpl implements LoginService {
    UserOwnDao userOwnDao = new UserOwnDaoImpl();
    public static User userList;

    @Override
    public boolean isLogin(String loginname, String password) {
        userList = userOwnDao.login(loginname, password);
        return userList != null;
    }
}
