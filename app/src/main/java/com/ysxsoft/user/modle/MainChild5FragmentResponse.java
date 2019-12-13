package com.ysxsoft.user.modle;

/**
 * Create By 胡
 * on 2019/12/10 0010
 */
public class MainChild5FragmentResponse {

    /**
     * success : true
     * message : 操作成功
     * code : 200
     * result : {"phone":"17607147805","avater":"/sys/common/view/files/20191212/Sketchpad_1576117615992.png","name":"李四四"}
     * timestamp : 1576117774385
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
         * phone : 17607147805
         * avater : /sys/common/view/files/20191212/Sketchpad_1576117615992.png
         * name : 李四四
         */

        private String phone;
        private String avater;
        private String name;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
