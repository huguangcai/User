package com.ysxsoft.common_base.pay.wx;

import android.content.Context;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ysxsoft.common_base.config.BaseConfig;

public class WxPayUtils {
    public static String WX_APPID = BaseConfig.WX_APP_ID;    //1.请配置微信WX_APPID
    public static WxPayUtils instance;

    public static WxPayUtils getInstance() {
        if (instance == null) {
            synchronized (WxPayUtils.class) {
                instance = new WxPayUtils();
            }
        }
        return instance;
    }

    public IWXAPI reg(Context context) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, WX_APPID);
        return api;
    }

    public void pay(IWXAPI iwxapi, WxPayReuqestParams d) {
        PayReq request = new PayReq();
        request.appId = d.getAppid();
        request.partnerId = d.getPartnerid(); //微信支付分配的商户号
        request.prepayId = d.getPrepayid(); //微信返回的支付交易会话ID
        request.nonceStr = d.getNoncestr(); // 随机字符串
        request.timeStamp = "" + d.getTimestamp(); // 北京时间时间戳
        request.packageValue = d.getPackageX();
        request.sign = d.getSign(); //服务端生成的签名
        iwxapi.sendReq(request);
    }

    public static class WxPayReuqestParams {
        private String prepayid;
        private String appid;
        private String partnerid;
        private String packageX;
        private String noncestr;
        private long timestamp;
        private String sign;

        public WxPayReuqestParams() {
        }

        public String getPrepayid() {
            return prepayid == null ? "" : prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getAppid() {
            return appid == null ? "" : appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPartnerid() {
            return partnerid == null ? "" : partnerid;
        }

        public void setPartnerid(String partnerid) {
            this.partnerid = partnerid;
        }

        public String getPackageX() {
            return packageX == null ? "" : packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getNoncestr() {
            return noncestr == null ? "" : noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getSign() {
            return sign == null ? "" : sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
    }
}
///////////////////////////////////////////////////////////////////////////
// 用法
///////////////////////////////////////////////////////////////////////////

//<activity
//            android:name=".wxapi.WXPayEntryActivity"
//                    android:exported="true"
//                    android:launchMode="singleTop"></activity>

//    IWXAPI iwxapi;
//    iwxapi=WxPayUtils.getInstance().reg(this);
//    //微信支付
//    WxPayUtils.WxPayReuqestParams payReuqestParams = new WxPayUtils.WxPayReuqestParams();
//    payReuqestParams.setAppid(data.getAppid());
//    payReuqestParams.setNoncestr(data.getNoncestr());
//    payReuqestParams.setPackageX(data.getPackageX());
//    payReuqestParams.setPartnerid(data.getPartnerid());
//    payReuqestParams.setPrepayid(data.getPrepayid());
//    payReuqestParams.setSign(data.getSign());
//    payReuqestParams.setTimestamp(data.getTimestamp());
//    payReuqestParams.setAppid(data.getAppid());
//    WxPayUtils.getInstance().pay(iwxapi, payReuqestParams);