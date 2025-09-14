package dao;

import DTO.CheckPwdInfo;
import DTO.RegisterInfo;
import DTO.UserInfo;

public interface UserOwnDao {
    UserInfo login(String loginname, String password);
    void changeStatus(String loginname,String option);
    CheckPwdInfo checkPassword(String loginname, String oldPassword);
    int changePassword(String loginname, String newPassword);
    RegisterInfo checkLoginname(String loginname);
    int registerNewUser(String registername,String password);

}
