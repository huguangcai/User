package com.ysxsoft.user.modle;

import java.util.List;

/**
 * Create By 胡
 * on 2019/12/13 0013
 */
public class SelectStaffResponse {

    /**
     * success : true
     * message : 操作成功!
     * code : 200
     * result : {"list":[{"phone":"123456789","avater":"/user.jpg","name":"王博伟的汽车店","id":"f389da0ce2528195246d7dc0a77f1a28","type":"shop"},{"phone":"17607147805","avater":"/sys/common/view/files/20191212/Sketchpad_1576117615992.png","name":"李四四","id":"001d57dba4ecd572e03c14faae9709d3","type":"staff"}]}
     * timestamp : 1576133311262
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * phone : 123456789
             * avater : /user.jpg
             * name : 王博伟的汽车店
             * id : f389da0ce2528195246d7dc0a77f1a28
             * type : shop
             */

            private String phone;
            private String avater;
            private String name;
            private String id;
            private String type;

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

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
