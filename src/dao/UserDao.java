package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUserInfo(String page,String limit);
    int getAllUserInfoCount();
    int updateUserInfo(User user);
    int delUserInfo(int delId);
    int addUserInfo(User user);
}
