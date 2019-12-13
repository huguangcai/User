package com.ysxsoft.user.modle;

/**
 * Create By 胡
 * on 2019/12/13 0013
 */
public class LoginResonse {

    /**
     * success : true
     * message : 操作成功
     * code : 200
     * result : {"identity":"staff","id":"001d57dba4ecd572e03c14faae9709d3"}
     * timestamp : 1576116389569
     */

    private boolean success;
    private String message;
    private String code;
    private ResultBean result;
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class ResultBean {
        /**
         * identity : staff
         * id : 001d57dba4ecd572e03c14faae9709d3
         */

        private String identity;
        private String id;

        public String getIdentity() {
            return identity;
        }

        public void setIdentity(String identity) {
            this.identity = identity;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
