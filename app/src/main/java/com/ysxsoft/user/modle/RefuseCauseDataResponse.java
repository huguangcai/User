package com.ysxsoft.user.modle;

import java.util.List;

/**
 * Create By 胡
 * on 2019/12/25 0025
 */
public class RefuseCauseDataResponse {

    /**
     * success : true
     * message : 操作成功!
     * code : 200
     * result : {"orderNo":"66f5738d6bdae366fe48a28edf988a4d","reasons":[{"id":"44468d083dc32eded0179374b6426143","reason":"老板有钱,不想接单"}],"list":[{"number":"4","img":"/sys/common/view/files/20191203/1_1575355760825.jpg","price":"40","type":"2","title":"标准洗车"}]}
     * timestamp : 1577084119237
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
         * orderNo : 66f5738d6bdae366fe48a28edf988a4d
         * reasons : [{"id":"44468d083dc32eded0179374b6426143","reason":"老板有钱,不想接单"}]
         * list : [{"number":"4","img":"/sys/common/view/files/20191203/1_1575355760825.jpg","price":"40","type":"2","title":"标准洗车"}]
         */

        private String orderNo;
        private List<ReasonsBean> reasons;
        private List<ListBean> list;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public List<ReasonsBean> getReasons() {
            return reasons;
        }

        public void setReasons(List<ReasonsBean> reasons) {
            this.reasons = reasons;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ReasonsBean {
            /**
             * id : 44468d083dc32eded0179374b6426143
             * reason : 老板有钱,不想接单
             */

            private String id;
            private String reason;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }
        }

        public static class ListBean {
            /**
             * number : 4
             * img : /sys/common/view/files/20191203/1_1575355760825.jpg
             * price : 40
             * type : 2
             * title : 标准洗车
             */

            private String number;
            private String img;
            private String price;
            private String type;
            private String title;

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
