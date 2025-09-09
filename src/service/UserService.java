package service;

import entity.User;

public interface UserService {
    void getAllUserInfo(String page,String limit);
    boolean updateUserInfo(User user);
    boolean delUserInfo(int delId);
    boolean addUserInfo(User user);
}
