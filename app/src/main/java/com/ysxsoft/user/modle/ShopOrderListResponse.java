package com.ysxsoft.user.modle;

import java.util.List;

/**
 * Create By 胡
 * on 2019/12/19 0019
 */
public class ShopOrderListResponse {

    /**
     * success : true
     * message : 操作成功!
     * code : 200
     * result : {"list":[{"takeCarTime":"2019-12-12 12:00:00","znumber":"3","total":"183.0","distance":"144.2306","orderId":"4db3360c810c59ab6fca334ff9588bcd","shopName":"王博伟的汽车店","useTime":"2019-12-12 12:00:00","orderStatus":"2","productList":[{"number":"1","img":"/sys/common/view/files/20191203/1_1575355760825.jpg","price":"40","name":"标准洗车"},{"number":"2","img":"/sys/common/view/files/20191203/1.1.1_1575355992375.1.1.jpg","price":"20","name":"车载加湿器"}]}]}
     * timestamp : 1576118578266
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
             * takeCarTime : 2019-12-12 12:00:00
             * znumber : 3
             * total : 183.0
             * distance : 144.2306
             * orderId : 4db3360c810c59ab6fca334ff9588bcd
             * shopName : 王博伟的汽车店
             * useTime : 2019-12-12 12:00:00
             * orderStatus : 2
             * productList : [{"number":"1","img":"/sys/common/view/files/20191203/1_1575355760825.jpg","price":"40","name":"标准洗车"},{"number":"2","img":"/sys/common/view/files/20191203/1.1.1_1575355992375.1.1.jpg","price":"20","name":"车载加湿器"}]
             */

            private String takeCarTime;
            private String znumber;
            private String total;
            private String distance;
            private String orderId;
            private String shopName;
            private String useTime;
            private String orderStatus;
            private List<ProductListBean> productList;

            public String getTakeCarTime() {
                return takeCarTime;
            }

            public void setTakeCarTime(String takeCarTime) {
                this.takeCarTime = takeCarTime;
            }

            public String getZnumber() {
                return znumber;
            }

            public void setZnumber(String znumber) {
                this.znumber = znumber;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getShopName() {
                return shopName;
            }

            public void setShopName(String shopName) {
                this.shopName = shopName;
            }

            public String getUseTime() {
                return useTime;
            }

            public void setUseTime(String useTime) {
                this.useTime = useTime;
            }

            public String getOrderStatus() {
                return orderStatus;
            }

            public void setOrderStatus(String orderStatus) {
                this.orderStatus = orderStatus;
            }

            public List<ProductListBean> getProductList() {
                return productList;
            }

            public void setProductList(List<ProductListBean> productList) {
                this.productList = productList;
            }

            public static class ProductListBean {
                /**
                 * number : 1
                 * img : /sys/common/view/files/20191203/1_1575355760825.jpg
                 * price : 40
                 * name : 标准洗车
                 */

                private String number;
                private String img;
                private String price;
                private String name;

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
            }
        }
    }
}
