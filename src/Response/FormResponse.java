package Response;

import com.alibaba.fastjson.annotation.JSONField;

public class FormResponse {
    @JSONField(ordinal = 1)
    private String code;
    @JSONField(ordinal = 2)
    private String msg;
    @JSONField(ordinal = 3)
    private Integer count;
    @JSONField(ordinal = 4)
    private Object data;

    public FormResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public FormResponse(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public FormResponse(String code, String msg, Integer count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
