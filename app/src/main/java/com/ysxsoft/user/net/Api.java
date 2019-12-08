package com.ysxsoft.user.net;


import com.ysxsoft.user.config.AppConfig;

/**
 * 接口地址
 */
public interface Api {
    //图片上传
    public final String GET_UPDATAIMAGES = AppConfig.BASE_URL + "api/login/upload_image";
    //发短信
    public final String GET_SENDMSG = AppConfig.BASE_URL + "ront/userClient/send";
    // 登录注册
    public final String GET_LOGIN = AppConfig.BASE_URL + "";
    // 忘记密码
    public final String GET_FORGETPWD = AppConfig.BASE_URL + "";
    // 待接单
    public final String GET_WAITING_LIST = AppConfig.BASE_URL + "";
    // 拒绝原因
    public final String GET_REFUSE_CAUSE = AppConfig.BASE_URL + "";
    // 确认接单
    public final String GET_IDENTIFICATION = AppConfig.BASE_URL + "";
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


}
