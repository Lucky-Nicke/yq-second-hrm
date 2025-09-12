package service.impl;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.User;
import service.UserService;

import java.util.List;

/**
 * 用户管理服务类
 */
public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();
    public static List<User> userList;
    public static int count;

    @Override
    public void getAllUserInfo(String page, String limit) {
        userList = userDao.getAllUserInfo(page, limit);
        count = userDao.getAllUserInfoCount();
    }

    @Override
    public boolean updateUserInfo(User user) {
        int updateSuccess = userDao.updateUserInfo(user);
        return updateSuccess == 1;
    }

    @Override
    public boolean delUserInfo(int delId) {
        int delSuccess = userDao.delUserInfo(delId);
        return delSuccess == 1;
    }

    @Override
    public boolean addUserInfo(User user) {
        int addSuccess = userDao.addUserInfo(user);
        return addSuccess == 1;
    }

    @Override
    public void searchUserInfo(String area, String loginname, String status) {
        switch (area) {
            case "status":
                userList = userDao.searchUserInfoByStauts(status);
                count = userDao.getSearchUserInfoByStautsCount(status);
                break;
            case "all":
                userList = userDao.searchUserInfoByName(loginname, status);
                count = userDao.getSearchUserInfoByNameCount(loginname, status);
                break;
        }
    }
}
