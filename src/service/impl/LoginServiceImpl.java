package service.impl;

import DTO.CheckPwdInfo;
import DTO.RegisterInfo;
import DTO.UserInfo;
import dao.UserOwnDao;
import dao.impl.UserOwnDaoImpl;
import service.LoginService;

public class LoginServiceImpl implements LoginService {
    UserOwnDao userOwnDao = new UserOwnDaoImpl();
    public static UserInfo userInfoList;
    public static CheckPwdInfo checkPwdInfo;
    public static RegisterInfo registerInfo;

    @Override
    public boolean isLogin(String loginname, String password) {
        userInfoList = userOwnDao.login(loginname, password);
        return userInfoList != null;
    }

    @Override
    public void loggedIn(String loginname) {
        userOwnDao.changeStatus(loginname, "login");
    }

    @Override
    public void loggedOut(String loginname) {
        userOwnDao.changeStatus(loginname, "logout");
    }

    @Override
    public boolean checkPassword(String loginname, String oldPassword) {
        checkPwdInfo = userOwnDao.checkPassword(loginname, oldPassword);
        return checkPwdInfo != null;
    }

    @Override
    public void changePassword(String loginname, String newPassword) {
        userOwnDao.changePassword(loginname, newPassword);
    }

    @Override
    public boolean checkLoginname(String loginname) {
        registerInfo = userOwnDao.checkLoginname(loginname);
        System.out.println(registerInfo);
        return registerInfo != null;
    }

    @Override
    public boolean registerNewUser(String registername, String password) {
        return userOwnDao.registerNewUser(registername, password) == 1;
    }


}
