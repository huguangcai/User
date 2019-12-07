package com.ysxsoft.common_base.pay.wx;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ysxsoft.common_base.utils.LogUtils;

/**
 * create by Sincerly on 2018/12/29 0029
 **/
public  class AbsWXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private static final String TAG = "AbsWXPayEntryActivity";
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, WxPayUtils.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        //0 成功，展示成功页面
        //-1 错误，可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
        //-2 用户取消，无需处理。发生场景：用户不支付了，点击取消，返回APP。
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            WxPaymentManager.getInstance().onResponseCode(resp.errCode);
            switch (resp.errCode){
                case -1:
                    WxPaymentManager.getInstance().onError();
                    break;
                case -2:
                    WxPaymentManager.getInstance().onCancel();
                    break;
                case 0:
                    WxPaymentManager.getInstance().onSuccess();
                    break;
            }
        }
        finish();
    }
}
