package com.ysxsoft.user.ui.activity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;

/**
 * Create By 胡
 * on 2019/12/8 0008
 */
@Route(path = "/main/CookerDataActivity")
public class CookerDataActivity extends BaseActivity {

    public static void start(){
        ARouter.getInstance().build(ARouterPath.getCookerDataActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cook_data_layout;
    }
}
