package com.ysxsoft.common_base.config;

/**
 * create by Sincerly on 2019/1/3 0003
 **/
public interface BaseConfig {
    static String PACKAGE_NAME="com.ysxsoft.prediction";

    ///////////////////////////////////////////////////////////////////////////
    // 极光相关配置
    ///////////////////////////////////////////////////////////////////////////
    static boolean JPUSH_ENABLED=true;
    static String JPUSH_PKGNAME=PACKAGE_NAME;
    static String JPUSH_KEY="a1aa44620d5ff95ed3d484cd";
    static String JPUSH_CHANNEL="developer-default";

    ///////////////////////////////////////////////////////////////////////////
    // umeng相关配置
    ///////////////////////////////////////////////////////////////////////////
    static String UMENG_APP_KEY="5c6cc1a3f1f5561f68000b5b";
    static String UMENG_SECRET_KEY="5bc6fbd5f1f55698170000c5";
    static String WX_APP_ID="wx297392ff114ab087";
    static String WX_APP_KEY="209ecd8c47631f80d3455d4e26040d1b";
    static String QQ_APP_ID="1109513443";//tips: 请修改xml配置
    static String QQ_APP_KEY="KRGbTmpCJ9dzo3al";
    static String SINA_APP_ID="470508322";
    static String SINA_APP_KEY="ee5af70cf1ec2f9c1fb8ca6732eba5d1";
    static String SINA_REDIRECT_URL="http://zhyuce.sanzhima.cn/";//2.0授权地址

    static int IM_HX_FRIEND_ITEM_NROMAL=0x01;//环信聊天列表多类型 好友普通
    static int IM_HX_FRIEND_ITEM_LETTER=0x02;//环信聊天列表多类型 好友首字母英文

    static boolean DEBUG=false;
}
