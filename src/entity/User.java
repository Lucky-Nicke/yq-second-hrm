package entity;

import java.sql.Timestamp;

public class User {
    private Integer ID;
    private String loginname;
    private String PASSWORD;
    private int STATUS;
    private Timestamp createdate;
    private String username;

    public User(Integer ID, String loginname, String PASSWORD, int STATUS, Timestamp createdate, String username) {
        this.ID = ID;
        this.loginname = loginname;
        this.PASSWORD = PASSWORD;
        this.STATUS = STATUS;
        this.createdate = createdate;
        this.username = username;
    }

    public User() {
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
