package com.ysxsoft.user.modle;

import java.util.List;

/**
 * Create By 胡
 * on 2019/12/14 0014
 */
public class WaitCarDetialResponse {

    /**
     * success : true
     * message : 操作成功!
     * code : 200
     * result : {"orderList":[{"number":"4","img":"/sys/common/view/files/20191203/1_1575355760825.jpg","price":"40","name":"标准洗车","type":"2"},{"number":"3","img":"/sys/common/view/files/20191203/1.1.1_1575355992375.1.1.jpg","price":"20","name":"车载加湿器","format":"红色1号","type":"1"}],"fee":"143.00","cardList":["汽车维修vip"],"total":"203.0","carNumber":"豫M250aaa","useTime":"2019-12-10 12:00:00","remark":"我的订单不要钱","beginAddress":"河南省郑州市","beginLat":"33.795858","beginLng":"114.604802","endAddress":"河南省国家大学科技园东区16号楼C座国家大学科技园东区","endLng":"113.604801","endLat":"34.795858","distance":"144.2306","shopId":"f389da0ce2528195246d7dc0a77f1a28","shopname":"王博伟的汽车店","mobile":"0136-1354874","shopAvater":"/sys/common/view/files/20191204/1_1575432012089.jpg","userId":"e24d90cf1cd4db2cb69da13ebb8994ab","username":"二向箔呵呵","phone":"17607147805","avater":"/sys/common/view/files/20191204/1_1575432012089.jpg","orderId":"66f5738d6bdae366fe48a28edf988a4d","payType":null,"createTime":"2019-12-13 16:20:17","staffId":"f389da0ce2528195246d7dc0a77f1a28","staffName":"王博伟的汽车店","staffPhone":"123456789","staffAvater":"/user.jpg","takeCarTime":"2019-12-13 17:00:00","takeOrderTime":"2019-12-13 16:57:14"}
     * timestamp : 1576226374613
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
         * orderList : [{"number":"4","img":"/sys/common/view/files/20191203/1_1575355760825.jpg","price":"40","name":"标准洗车","type":"2"},{"number":"3","img":"/sys/common/view/files/20191203/1.1.1_1575355992375.1.1.jpg","price":"20","name":"车载加湿器","format":"红色1号","type":"1"}]
         * fee : 143.00
         * cardList : ["汽车维修vip"]
         * total : 203.0
         * carNumber : 豫M250aaa
         * useTime : 2019-12-10 12:00:00
         * remark : 我的订单不要钱
         * beginAddress : 河南省郑州市
         * beginLat : 33.795858
         * beginLng : 114.604802
         * endAddress : 河南省国家大学科技园东区16号楼C座国家大学科技园东区
         * endLng : 113.604801
         * endLat : 34.795858
         * distance : 144.2306
         * shopId : f389da0ce2528195246d7dc0a77f1a28
         * shopname : 王博伟的汽车店
         * mobile : 0136-1354874
         * shopAvater : /sys/common/view/files/20191204/1_1575432012089.jpg
         * userId : e24d90cf1cd4db2cb69da13ebb8994ab
         * username : 二向箔呵呵
         * phone : 17607147805
         * avater : /sys/common/view/files/20191204/1_1575432012089.jpg
         * orderId : 66f5738d6bdae366fe48a28edf988a4d
         * payType : null
         * createTime : 2019-12-13 16:20:17
         * staffId : f389da0ce2528195246d7dc0a77f1a28
         * staffName : 王博伟的汽车店
         * staffPhone : 123456789
         * staffAvater : /user.jpg
         * takeCarTime : 2019-12-13 17:00:00
         * takeOrderTime : 2019-12-13 16:57:14
         */

        private String fee;
        private String total;
        private String carNumber;
        private String useTime;
        private String remark;
        private String beginAddress;
        private String beginLat;
        private String beginLng;
        private String endAddress;
        private String endLng;
        private String endLat;
        private String distance;
        private String shopId;
        private String shopname;
        private String mobile;
        private String shopAvater;
        private String userId;
        private String username;
        private String phone;
        private String avater;
        private String orderId;
        private int payType;
        private String createTime;
        private String staffId;
        private String staffName;
        private String staffPhone;
        private String staffAvater;
        private String takeCarTime;
        private String takeOrderTime;
        private List<OrderListBean> orderList;
        private List<String> cardList;

        public String getFee() {
            return fee;
        }

        public void setFee(String fee) {
            this.fee = fee;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getUseTime() {
            return useTime;
        }

        public void setUseTime(String useTime) {
            this.useTime = useTime;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
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

        public String getEndAddress() {
            return endAddress;
        }

        public void setEndAddress(String endAddress) {
            this.endAddress = endAddress;
        }

        public String getEndLng() {
            return endLng;
        }

        public void setEndLng(String endLng) {
            this.endLng = endLng;
        }

        public String getEndLat() {
            return endLat;
        }

        public void setEndLat(String endLat) {
            this.endLat = endLat;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getShopAvater() {
            return shopAvater;
        }

        public void setShopAvater(String shopAvater) {
            this.shopAvater = shopAvater;
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

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getStaffId() {
            return staffId;
        }

        public void setStaffId(String staffId) {
            this.staffId = staffId;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public String getStaffPhone() {
            return staffPhone;
        }

        public void setStaffPhone(String staffPhone) {
            this.staffPhone = staffPhone;
        }

        public String getStaffAvater() {
            return staffAvater;
        }

        public void setStaffAvater(String staffAvater) {
            this.staffAvater = staffAvater;
        }

        public String getTakeCarTime() {
            return takeCarTime;
        }

        public void setTakeCarTime(String takeCarTime) {
            this.takeCarTime = takeCarTime;
        }

        public String getTakeOrderTime() {
            return takeOrderTime;
        }

        public void setTakeOrderTime(String takeOrderTime) {
            this.takeOrderTime = takeOrderTime;
        }

        public List<OrderListBean> getOrderList() {
            return orderList;
        }

        public void setOrderList(List<OrderListBean> orderList) {
            this.orderList = orderList;
        }

        public List<String> getCardList() {
            return cardList;
        }

        public void setCardList(List<String> cardList) {
            this.cardList = cardList;
        }

        public static class OrderListBean {
            /**
             * number : 4
             * img : /sys/common/view/files/20191203/1_1575355760825.jpg
             * price : 40
             * name : 标准洗车
             * type : 2
             * format : 红色1号
             */

            private String number;
            private String img;
            private String price;
            private String name;
            private String type;
            private String format;

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

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
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
