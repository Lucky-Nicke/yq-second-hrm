package service;

import entity.User;

public interface UserService {
    public void getAllUserInfo(String page,String limit);
    public boolean updateUserInfo(User user);
}
