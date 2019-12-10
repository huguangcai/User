package com.ysxsoft.user;

import android.app.Activity;

/**
 * 路由路径配置
 */
public class ARouterPath {
    /**
     * 首页
     */
    public static String getMainActivity() {
        return "/main/MainActivity";
    }

    /**
     * 登录
     */
    public static String getLoginActivity() {
        return "/main/LoginActivity";
    }

    /**
     * 忘记密码
     */
    public static String getForgetPwdActivity() {
        return "/main/ForgetPwdActivity";
    }

    /**
     * 拒绝原因
     *
     * @return
     */
    public static String getRefuseCauseActivity() {
        return "/main/RefuseCauseActivity";
    }

    /**
     * 确认接单
     *
     * @return
     */
    public static String getIdentificationActivity() {
        return "/main/IdentificationActivity";
    }

    /**
     * 厨师资料
     *
     * @return
     */
    public static String getCookerDataActivity() {
        return "/main/CookerDataActivity";
    }

    /**
     * 待接单订单详情
     *
     * @return
     */
    public static String getWaitingListDetialActivity() {
        return "/main/WaitingListDetialActivity";
    }

    /**
     * 准备中订单详情
     *
     * @return
     */
    public static String getPrepareListDetialActivity() {
        return "/main/PrepareListDetialActivity";
    }

    /**
     * 服务中详情
     *
     * @return
     */
    public static String getServiceListDetialActivity() {
        return "/main/ServiceListDetialActivity";
    }

    /**
     * 已完成详情
     *
     * @return
     */
    public static String getCompletedListDetialActivity() {
        return "/main/CompletedListDetialActivity";
    }

    /**
     * 已完成拒绝详情
     *
     * @return
     */
    public static String getCompletedRefuseListDetialActivity() {
        return "/main/CompletedRefuseListDetialActivity";
    }

    /**
     * 厨师详情
     *
     * @return
     */
    public static String getCookerDetailActivity() {
        return "/main/CookerDetailActivity";
    }

    /**
     * 个人资料
     *
     * @return
     */
    public static String getPersonCenterActivity() {
        return "/main/PersonCenterActivity";
    }

    /**
     * 修改姓名
     *
     * @return
     */
    public static String getModiFyNameActivity() {
        return "/main/ModiFyNameActivity";
    }

    /**
     * 修改手机号 登录密码  支付密码
     *
     * @return
     */
    public static String getPhoneLoginPayPwdActivity() {
        return "/main/PhoneLoginPayPwdActivity";
    }

    /**
     * 关于平台
     *
     * @return
     */
    public static String getAboutPlatformActivity() {
        return "/main/AboutPlatformActivity";
    }

    /**
     * 反馈意见
     *
     * @return
     */
    public static String getFeedBackActivity() {
        return "/main/FeedBackActivity";
    }


}
