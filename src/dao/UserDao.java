package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUserInfo(String page,String limit);
    List<User> searchUserInfoByName(String loginname,String status,String page, String limit);
    List<User> searchUserInfoByStauts(String status,String page, String limit);
    int getSearchUserInfoByNameCount(String loginname,String status);
    int getSearchUserInfoByStautsCount(String status);
    int getAllUserInfoCount();
    int updateUserInfo(User user);
    int delUserInfo(int delId);
    int addUserInfo(User user);
}
