package DTO;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;

public class UserInfo {
    private Integer ID;
    private String loginname;
    private int STATUS;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Timestamp createdate;
    private String username;

    public UserInfo(Integer ID, String loginname, int STATUS, Timestamp createdate, String username) {
        this.ID = ID;
        this.loginname = loginname;
        this.STATUS = STATUS;
        this.createdate = createdate;
        this.username = username;
    }

    public UserInfo() {
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
