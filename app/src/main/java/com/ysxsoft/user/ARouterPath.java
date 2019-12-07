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
     * @return
     */
    public static String getRefuseCauseActivity() {
        return "/main/RefuseCauseActivity";
    }

     /**
     * 确认接单
     * @return
     */
    public static String getIdentificationActivity() {
        return "/main/IdentificationActivity";
    }



}
