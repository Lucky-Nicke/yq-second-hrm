package DTO;

public class CheckPwdInfo {
    private String loginname;
    private String oldPassword;
    private String newPassword;

    public CheckPwdInfo(String loginname, String oldPassword, String newPassword) {
        this.loginname = loginname;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public CheckPwdInfo() {
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
