package com.ysxsoft.user.modle;

/**
 * Create By 胡
 * on 2019/12/13 0013
 */
public class ModifyAvatarResonse {

    /**
     * success : true
     * message : 操作成功
     * code : 200
     * result : {"avater":"/sys/common/view/files/20191212/Sketchpad_1576117615992.png"}
     * timestamp : 1576117615992
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
         * avater : /sys/common/view/files/20191212/Sketchpad_1576117615992.png
         */

        private String avater;

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }
    }
}
