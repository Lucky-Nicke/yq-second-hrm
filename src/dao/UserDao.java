package dao;

import entity.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUserInfo(String page,String limit);
    public int getAllUserInfoCount();
}
