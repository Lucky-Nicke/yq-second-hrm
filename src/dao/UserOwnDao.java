package dao;

import entity.User;

public interface UserOwnDao {
    User login(String loginname, String password);
}
