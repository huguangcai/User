package com.ysxsoft.user.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.utils.SharedPreferencesUtils;
import com.ysxsoft.common_base.view.custom.image.CircleImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import io.reactivex.functions.Consumer;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/10 0010
 */
@Route(path = "/main/PersonCenterActivity")
public class PersonCenterActivity extends BaseActivity {
    @BindView(R.id.backWithText)
    TextView backWithText;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.rightWithIcon)
    TextView rightWithIcon;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.bottomLineView)
    View bottomLineView;
    @BindView(R.id.statusBar)
    View statusBar;

    @BindView(R.id.ll_Head)
    LinearLayout ll_Head;
    @BindView(R.id.ll_Name)
    LinearLayout ll_Name;
    @BindView(R.id.ll_Phone)
    LinearLayout ll_Phone;
    @BindView(R.id.ll_LoginPwd)
    LinearLayout ll_LoginPwd;
    @BindView(R.id.ll_PayPwd)
    LinearLayout ll_PayPwd;
    @BindView(R.id.civHead)
    CircleImageView civHead;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvLoginOut)
    TextView tvLoginOut;

    private static final int RC_CHOOSE_PHOTO = 0x01;
    public static final int REQUEST_CODE_CROP = 0x02;
    private RxPermissions r;
    private BGAPhotoHelper mPhotoHelper;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getPersonCenterActivity()).navigation();
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();
        initPhotoHelper();
    }

    private void initTitle() {
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("个人资料");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_data_layout;
    }

    @OnClick({R.id.backLayout, R.id.ll_Head, R.id.ll_Name, R.id.ll_Phone, R.id.ll_LoginPwd, R.id.ll_PayPwd, R.id.tvLoginOut})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.ll_Head:
                choicePhotoWrapper();
                break;
            case R.id.ll_Name:
                ModiFyNameActivity.start(tvName.getText().toString());
                break;
            case R.id.ll_Phone:
                PhoneLoginPayPwdActivity.start("1");
                break;
            case R.id.ll_LoginPwd:
                PhoneLoginPayPwdActivity.start("2");
                break;
            case R.id.ll_PayPwd:
                PhoneLoginPayPwdActivity.start("3");
                break;
            case R.id.tvLoginOut:
                toLogin();
                break;
        }
    }

    @SuppressLint("CheckResult")
    private void choicePhotoWrapper() {
        r.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
                    File takePhotoDir = new File(AppConfig.PHOTO_PATH, "space");
                    File f = new File(AppConfig.PHOTO_PATH);
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    Intent photoPickerIntent = new BGAPhotoPickerActivity.IntentBuilder(mContext)
                            .cameraFileDir(takePhotoDir) // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话则不开启图库里的拍照功能
                            .maxChooseCount(1) // 图片选择张数的最大值
                            .selectedPhotos(null) // 当前已选中的图片路径集合
                            .pauseOnScroll(false) // 滚动列表时是否暂停加载图片
                            .build();
                    startActivityForResult(photoPickerIntent, RC_CHOOSE_PHOTO);
                }
            }
        });
    }

    @SuppressLint("CheckResult")
    private void initPhotoHelper() {
        r = new RxPermissions(this);
        if (r.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE) && r.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            File takePhotoDir = new File(AppConfig.PHOTO_CACHE_PATH);
            mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
        } else {
            r.requestEach(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}).subscribe(new Consumer<Permission>() {
                @Override
                public void accept(Permission permission) throws Exception {
                    if (permission.granted) {
                        File takePhotoDir = new File(AppConfig.PHOTO_CACHE_PATH);
                        mPhotoHelper = new BGAPhotoHelper(takePhotoDir);
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RC_CHOOSE_PHOTO:
                    if (data != null) {
                        List<String> selectedPhotos = BGAPhotoPickerActivity.getSelectedPhotos(data);
                        if (selectedPhotos != null && selectedPhotos.size() > 0) {
                            //拍照返回
                            try {
                                startActivityForResult(mPhotoHelper.getCropIntent(selectedPhotos.get(0), 200, 200), REQUEST_CODE_CROP);
                            } catch (Exception e) {
                                mPhotoHelper.deleteCameraFile();
                                mPhotoHelper.deleteCropFile();
                                BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_crop);
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case REQUEST_CODE_CROP:
                    String cropPath = mPhotoHelper.getCropFilePath();
                    String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(cropPath), AppConfig.PHOTO_PATH);
                    //裁剪后的
                    Glide.with(mContext).load(new File(path)).into(civHead);
                    editLogo(path);
                    break;
                default:
                    break;
            }
        }
    }

    private void editLogo(String avatar) {
        showLoadingDialog("正在修改");
        PostFormBuilder builder = OkHttpUtils.post()
                .url(Api.GET_EDIT_USER_HEAD)
                .addParams("uid", SharedPreferencesUtils.getUid(mContext));
        if (avatar != null) {
            File file = new File(avatar);
            builder.addFile("avatar", file.getName(), file);
        }
        builder.tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        hideLoadingDialog();
                        CommonResonse resp = JsonUtils.parseByGson(response, CommonResonse.class);
                        if (resp != null) {
//                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
//                                showToast("修改成功");
////                                getUserInfo();
//                            } else {
//                                showToast(resp.getMsg());
//                            }
                        } else {
                            showToast("修改失败");
                        }
                    }
                });

    }
}
