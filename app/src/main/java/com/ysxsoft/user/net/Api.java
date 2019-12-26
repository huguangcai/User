package com.ysxsoft.user.net;


import com.ysxsoft.user.config.AppConfig;

/**
 * 接口地址
 */
public interface Api {
    //图片上传
    public final String GET_UPDATAIMAGES = AppConfig.BASE_URL + "/front/common/uploadBatch";
    //发短信
    public final String GET_SENDMSG = AppConfig.BASE_URL + "/front/userClient/send";
    // 登录注册
    public final String GET_LOGIN = AppConfig.BASE_URL + "/front/serveClient/login";
    // 忘记密码  修改登录 / 支付密码
    public final String GET_FORGETPWD = AppConfig.BASE_URL + "/front/serveClient/updatePassword";
    // 待接单
    public final String GET_WAITING_LIST = AppConfig.BASE_URL + "";
    // 提交拒绝原因数据获取
    public final String GET_REFUSE_CAUSE_DATA = AppConfig.BASE_URL + "/front/serveClient/toRefundOrder";
    // 提交拒绝原因
    public final String GET_REFUSE_CAUSE = AppConfig.BASE_URL + "/front/serveClient/refundOrder";
    // 确认接单
    public final String GET_SHOP_IDENTIFICATION = AppConfig.BASE_URL + "/front/serveClient/takeOrder";
    // 准备中
    public final String GET_PREPARING = AppConfig.BASE_URL + "";
    // 服务中
    public final String GET_SERVICEING = AppConfig.BASE_URL + "";
    // 已完成
    public final String GET_COMPLETED = AppConfig.BASE_URL + "";
    // 厨师资料
    public final String GET_COOKER_DATA = AppConfig.BASE_URL + "";
    // 待接单详情
    public final String GET_WAITING_LIST_DETIAL = AppConfig.BASE_URL + "";
    // 准备中详情
    public final String GET_PREPARE_LIST_DETIAL = AppConfig.BASE_URL + "";
    // 服务中详情
    public final String GET_SERVICE_LIST_DETIAL = AppConfig.BASE_URL + "";
    // 已完成详情
    public final String GET_COMPLETE_LIST_DETIAL = AppConfig.BASE_URL + "";
    // 已完成拒绝详情
    public final String GET_COMPLETE_REFUSE_LIST_DETIAL = AppConfig.BASE_URL + "";
    // 厨师详情
    public final String GET_COOKER_DETIAL = AppConfig.BASE_URL + "";
    // 个人中心
    public final String GET_PERSON_DATA = AppConfig.BASE_URL + "/front/serveClient/serveCenter";
    // 修改头像
    public final String GET_EDIT_USER_HEAD = AppConfig.BASE_URL + "/front/serveClient/avater";
    // 修改名称
    public final String GET_EDIT_USER_NIKENAME = AppConfig.BASE_URL + "/front/serveClient/updateName";
    // 修改手机号
    public final String GET_PHONELOGINPAYPWD = AppConfig.BASE_URL + "/front/serveClient/updatePhone";
    // 关于平台
    public final String GET_ABOUTPLATFORM = AppConfig.BASE_URL + "";
    //反馈意见
    public final String GET_FEEDBACK = AppConfig.BASE_URL + "";
    //钱包
    public final String GET_WALLET = AppConfig.BASE_URL + "";
    //钱包明细
    public final String GET_WALLET_DETAIL = AppConfig.BASE_URL + "";
    //银行卡
    public final String GET_CARDCASE_LIST = AppConfig.BASE_URL + "";
    //添加银行卡
    public final String GET_ADDBANK = AppConfig.BASE_URL + "";
    //提现
    public final String GET_TXACTIVITY = AppConfig.BASE_URL + "";
    //评论列表
    public final String GET_EVALUATE_LIST = AppConfig.BASE_URL + "";
    //菜详情
    public final String GET_VEGETABLE_DETAIL = AppConfig.BASE_URL + "";
    //全部订单
    public final String GET_ALL_ORDER = AppConfig.BASE_URL + "";
    //商家服务
    public final String GET_MALL_SERVICE = AppConfig.BASE_URL + "";
    //商家资料
    public final String GET_MALL_DATA = AppConfig.BASE_URL + "";
    //选择员工
    public final String GET_SELECT_STAFF = AppConfig.BASE_URL + "/front/serveClient/staffList";
    //员工待接车
    public final String GET_WAIT_CAR = AppConfig.BASE_URL + "";
    //汽车待接单详情
    public final String GET_WAIT_CAR_LIST_DETAIL = AppConfig.BASE_URL + "/front/carOrder/waitOrder";
     //员工待接车详情
    public final String GET_WAIT_CAR_DETAIL = AppConfig.BASE_URL + "/front/carOrder/takingCar";
    //员工接车中
    public final String GET_WAITTING_CAR_DETAIL = AppConfig.BASE_URL + "/front/carOrder/takingCar";
    //员工工作中
    public final String GET_WORKING = AppConfig.BASE_URL + "";
    //员工送车中
    public final String GET_SONG_CAR = AppConfig.BASE_URL + "";
    //确认接车
    public final String GET_CHECK_TAKE_CAR = AppConfig.BASE_URL + "/front/serveClient/confirmTakeCar";
    //确认接车- 上传图片
    public final String GET_CHECK_TAKE_CAR_UPLOAD_PIC = AppConfig.BASE_URL + "/front/serveClient/allRightTakeCar";
    //确认接单
    public final String GET_CHECK_TAKE_ORDER = AppConfig.BASE_URL + "/front/serveClient/takeOrder";
    //确认到店
    public final String GET_CHECK_ARRIVE_SHOP = AppConfig.BASE_URL + "/front/serveClient/arriveShop";
    //出厂送车
    public final String GET_DELIVER_CAR = AppConfig.BASE_URL + "/front/serveClient/deliverCar";
    //送车 - 上传图片
    public final String GET_DELIVER_CAR_UPLOAD_PIC = AppConfig.BASE_URL + "/front/serveClient/allRightdeliverCar";
    //员工端已完成
    public final String GET_STAFF_COMPLETED_LIST = AppConfig.BASE_URL + "";
    //员工端已完成 详情
    public final String GET_STAFF_COMPLETED_DETAIL = AppConfig.BASE_URL + "";
    //员工端已完成拒绝 详情
    public final String GET_STAFF_COMPLETED_REFUSE_DETAIL = AppConfig.BASE_URL + "";
    //员工端已完成VIP 详情
    public final String GET_STAFF_VIP_COMPLETED_DETAIL = AppConfig.BASE_URL + "";
    //汽车老板端订单列表
    public final String GET_SHOP_ORDER_LIST = AppConfig.BASE_URL + "/front/serveClient/bossOrder";
    //汽车员工端订单列表
    public final String GET_STAFF_ORDER_LIST = AppConfig.BASE_URL + "/front/serveClient/staffOrder";


}
