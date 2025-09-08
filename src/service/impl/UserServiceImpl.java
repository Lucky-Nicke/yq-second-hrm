package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    public static List<User> userList;
    public static int count;

    @Override
    public void getAllUserInfo(String page,String limit) {
        userList = userDao.getAllUserInfo(page,limit);
        count = userDao.getAllUserInfoCount();
    }


}
