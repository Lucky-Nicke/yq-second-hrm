package DTO;

public class RegisterInfo {
    private String loginname;
    private String password;

    public RegisterInfo(String loginname, String password) {
        this.loginname = loginname;
        this.password = password;
    }

    public RegisterInfo() {
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
