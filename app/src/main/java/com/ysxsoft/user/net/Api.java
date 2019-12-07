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


}
