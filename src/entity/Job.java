package entity;

public class Job {
    private int id;
    private String NAME;
    private String REMARK;

    public Job() {
    }

    public Job(int id, String NAME, String REMARK) {
        this.id = id;
        this.NAME = NAME;
        this.REMARK = REMARK;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getREMARK() {
        return REMARK;
    }

    public void setREMARK(String REMARK) {
        this.REMARK = REMARK;
    }
}
