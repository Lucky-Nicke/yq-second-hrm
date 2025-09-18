package entity;

import java.sql.Timestamp;
import java.util.Date;

public class Employee {
    private int ID;
    private int DEPT_ID;
    private int JOB_ID;
    private String NAME;
    private String CARD_ID;
    private String ADDRESS;
    private String POST_CODE;
    private String TEL;
    private String PHONE;
    private String QQ_NUM;
    private String EMAIL;
    private int SEX;
    private String PARTY;
    private Timestamp BIRTHDAY;
    private String RACE;
    private String EDUCATION;
    private String SPECIALITY;
    private String HOBBY;
    private String REMARK;
    private Date CREATE_DATE;

    public Employee() {
    }

    public Employee(int ID, int DEPT_ID, int JOB_ID, String NAME, String CARD_ID, String ADDRESS, String POST_CODE, String TEL, String PHONE, String QQ_NUM, String EMAIL, int SEX, String PARTY, Timestamp BIRTHDAY, String RACE, String EDUCATION, String SPECIALITY, String HOBBY, String REMARK, Date CREATE_DATE) {
        this.ID = ID;
        this.DEPT_ID = DEPT_ID;
        this.JOB_ID = JOB_ID;
        this.NAME = NAME;
        this.CARD_ID = CARD_ID;
        this.ADDRESS = ADDRESS;
        this.POST_CODE = POST_CODE;
        this.TEL = TEL;
        this.PHONE = PHONE;
        this.QQ_NUM = QQ_NUM;
        this.EMAIL = EMAIL;
        this.SEX = SEX;
        this.PARTY = PARTY;
        this.BIRTHDAY = BIRTHDAY;
        this.RACE = RACE;
        this.EDUCATION = EDUCATION;
        this.SPECIALITY = SPECIALITY;
        this.HOBBY = HOBBY;
        this.REMARK = REMARK;
        this.CREATE_DATE = CREATE_DATE;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDEPT_ID() {
        return DEPT_ID;
    }

    public void setDEPT_ID(int DEPT_ID) {
        this.DEPT_ID = DEPT_ID;
    }

    public int getJOB_ID() {
        return JOB_ID;
    }

    public void setJOB_ID(int JOB_ID) {
        this.JOB_ID = JOB_ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getCARD_ID() {
        return CARD_ID;
    }

    public void setCARD_ID(String CARD_ID) {
        this.CARD_ID = CARD_ID;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getPOST_CODE() {
        return POST_CODE;
    }

    public void setPOST_CODE(String POST_CODE) {
        this.POST_CODE = POST_CODE;
    }

    public String getTEL() {
        return TEL;
    }

    public void setTEL(String TEL) {
        this.TEL = TEL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }

    public String getQQ_NUM() {
        return QQ_NUM;
    }

    public void setQQ_NUM(String QQ_NUM) {
        this.QQ_NUM = QQ_NUM;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public int getSEX() {
        return SEX;
    }

    public void setSEX(int SEX) {
        this.SEX = SEX;
    }

    public String getPARTY() {
        return PARTY;
    }

    public void setPARTY(String PARTY) {
        this.PARTY = PARTY;
    }

    public Timestamp getBIRTHDAY() {
        return BIRTHDAY;
    }

    public void setBIRTHDAY(Timestamp BIRTHDAY) {
        this.BIRTHDAY = BIRTHDAY;
    }

    public String getRACE() {
        return RACE;
    }

    public void setRACE(String RACE) {
        this.RACE = RACE;
    }

    public String getEDUCATION() {
        return EDUCATION;
    }

    public void setEDUCATION(String EDUCATION) {
        this.EDUCATION = EDUCATION;
    }

    public String getSPECIALITY() {
        return SPECIALITY;
    }

    public void setSPECIALITY(String SPECIALITY) {
        this.SPECIALITY = SPECIALITY;
    }

    public String getHOBBY() {
        return HOBBY;
    }

    public void setHOBBY(String HOBBY) {
        this.HOBBY = HOBBY;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }

    public Date getCREATE_DATE() {
        return CREATE_DATE;
    }

    public void setCREATE_DATE(Date CREATE_DATE) {
        this.CREATE_DATE = CREATE_DATE;
    }
}
