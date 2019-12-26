package com.ysxsoft.user.modle;

import java.util.List;

/**
 * Create By 胡
 * on 2019/12/26 0026
 */
public class WaitCarListDetialResponse {

    /**
     * code : 200
     * message : 操作成功!
     * result : {"avater":"/sys/common/view/files/20191204/1_1575432012089.jpg","beginAddress":"河南省郑州市","beginLat":"33.795858","beginLng":"114.604802","carNumber":"豫M250aaa","cardList":["汽车维修vip"],"createTime":"2019-12-13 16:20:17","distance":"144.2306","endAddress":"河南省国家大学科技园东区16号楼C座国家大学科技园东区","endLat":"34.795858","endLng":"113.604801","fee":"143.00","mobile":"0136-1354874","orderId":"66f5738d6bdae366fe48a28edf988a4d","orderList":[{"img":"/sys/common/view/files/20191203/1_1575355760825.jpg","name":"标准洗车","number":"4","price":"40","type":"2"},{"format":"红色1号","img":"/sys/common/view/files/20191203/1.1.1_1575355992375.1.1.jpg","name":"车载加湿器","number":"3","price":"20","type":"1"}],"phone":"17607147805","remark":"我的订单不要钱","shopAvater":"/sys/common/view/files/20191204/1_1575432012089.jpg","shopId":"f389da0ce2528195246d7dc0a77f1a28","shopname":"王博伟的汽车店","total":"203.0","useTime":"2019-12-10 12:00:00","userId":"e24d90cf1cd4db2cb69da13ebb8994ab","username":"二向箔呵呵"}
     * success : true
     * timestamp : 1576226374613
     */

    private String code;
    private String message;
    private ResultBean result;
    private boolean success;
    private long timestamp;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class ResultBean {
        /**
         * avater : /sys/common/view/files/20191204/1_1575432012089.jpg
         * beginAddress : 河南省郑州市
         * beginLat : 33.795858
         * beginLng : 114.604802
         * carNumber : 豫M250aaa
         * cardList : ["汽车维修vip"]
         * createTime : 2019-12-13 16:20:17
         * distance : 144.2306
         * endAddress : 河南省国家大学科技园东区16号楼C座国家大学科技园东区
         * endLat : 34.795858
         * endLng : 113.604801
         * fee : 143.00
         * mobile : 0136-1354874
         * orderId : 66f5738d6bdae366fe48a28edf988a4d
         * orderList : [{"img":"/sys/common/view/files/20191203/1_1575355760825.jpg","name":"标准洗车","number":"4","price":"40","type":"2"},{"format":"红色1号","img":"/sys/common/view/files/20191203/1.1.1_1575355992375.1.1.jpg","name":"车载加湿器","number":"3","price":"20","type":"1"}]
         * phone : 17607147805
         * remark : 我的订单不要钱
         * shopAvater : /sys/common/view/files/20191204/1_1575432012089.jpg
         * shopId : f389da0ce2528195246d7dc0a77f1a28
         * shopname : 王博伟的汽车店
         * total : 203.0
         * useTime : 2019-12-10 12:00:00
         * userId : e24d90cf1cd4db2cb69da13ebb8994ab
         * username : 二向箔呵呵
         */

        private String avater;
        private String beginAddress;
        private String beginLat;
        private String beginLng;
        private String carNumber;
        private String createTime;
        private String distance;
        private String endAddress;
        private String endLat;
        private String endLng;
        private String fee;
        private String mobile;
        private String orderId;
        private String phone;
        private String remark;
        private String shopAvater;
        private String shopId;
        private String shopname;
        private String total;
        private String useTime;
        private String userId;
        private String username;
        private int payType;

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        private List<String> cardList;
        private List<OrderListBean> orderList;

        public String getAvater() {
            return avater;
        }

        public void setAvater(String avater) {
            this.avater = avater;
        }

        public String getBeginAddress() {
            return beginAddress;
        }

        public void setBeginAddress(String beginAddress) {
            this.beginAddress = beginAddress;
        }

        public String getBeginLat() {
            return beginLat;
        }

        public void setBeginLat(String beginLat) {
            this.beginLat = beginLat;
        }

        public String getBeginLng() {
            return beginLng;
        }

        public void setBeginLng(String beginLng) {
            this.beginLng = beginLng;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getEndAddress() {
            return endAddress;
        }

        public void setEndAddress(String endAddress) {
            this.endAddress = endAddress;
        }

        public String getEndLat() {
            return endLat;
        }

        public void setEndLat(String endLat) {
            this.endLat = endLat;
        }

        public String getEndLng() {
            return endLng;
        }

        public void setEndLng(String endLng) {
            this.endLng = endLng;
        }

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getShopAvater() {
            return shopAvater;
        }

        public void setShopAvater(String shopAvater) {
            this.shopAvater = shopAvater;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopname() {
            return shopname;
        }

        public void setShopname(String shopname) {
            this.shopname = shopname;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public List<String> getCardList() {
            return cardList;
        }

        public void setCardList(List<String> cardList) {
            this.cardList = cardList;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public static class OrderListBean {
            /**
             * img : /sys/common/view/files/20191203/1_1575355760825.jpg
             * name : 标准洗车
             * number : 4
             * price : 40
             * type : 2
             * format : 红色1号
             */

            private String img;
            private String name;
            private String number;
            private String price;
            private String type;
            private String format;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
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

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }
        }
    }
}
