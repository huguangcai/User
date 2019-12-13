package com.ysxsoft.user.modle;

/**
 * Create By 胡
 * on 2019/12/13 0013
 */
public class SendMsgResonse {

    /**
     * success : true
     * message : 发送成功
     * code : 200
     * result : null
     * timestamp : 1575430801246
     */

    private boolean success;
    private String message;
    private String code;
    private Object result;
    private long timestamp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
