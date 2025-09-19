package service;

public interface LoginService {
    boolean isLogin(String loginname,String password);
    void loggedIn(String loginname);
    void loggedOut(String loginname);
    boolean checkPassword(String loginname,String oldPassword);
    void changePassword(String loginname,String newPassword);
    boolean checkLoginname(String loginname);
    boolean registerNewUser(String registername,String password);
}
