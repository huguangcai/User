package com.ysxsoft.user.modle;

import java.util.List;

/**
 * Create By 胡
 * on 2019/12/25 0025
 */
public class UpdataImagesResonse {

    /**
     * success : true
     * message : 操作成功
     * code : 200
     * result : {"path":["/sys/common/view/files/20191212/th_1576130962939.jpg","/sys/common/view/files/20191212/true_1576130962947.png"]}
     * timestamp : 1576130962939
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
        private List<String> path;

        public List<String> getPath() {
            return path;
        }

        public void setPath(List<String> path) {
            this.path = path;
        }
    }
}
