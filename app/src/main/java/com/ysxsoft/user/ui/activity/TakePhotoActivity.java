package com.ysxsoft.user.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.google.gson.JsonArray;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysxsoft.common_base.base.BaseActivity;
import com.ysxsoft.common_base.net.HttpResponse;
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.utils.JsonUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.config.AppConfig;
import com.ysxsoft.user.modle.CommonResonse;
import com.ysxsoft.user.modle.UpdataImagesResonse;
import com.ysxsoft.user.net.Api;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;

/**
 * Create By 胡
 * on 2019/12/14 0014
 */
@Route(path = "/main/TakePhotoActivity")
public class TakePhotoActivity extends BaseActivity {
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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvOk)
    TextView tvOk;
    private RBaseAdapter<LocalMedia> adapter;
    @Autowired
    String orderId;
    private List<LocalMedia> localMedia;

    public static void start(String orderId) {
        ARouter.getInstance().build(ARouterPath.getTakePhotoActivity()).withString("orderId", orderId).navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo_layout;
    }

    @Override
    public void doWork() {
        super.doWork();
        ARouter.getInstance().inject(this);
        initTitle();
        initList(new ArrayList<LocalMedia>());
    }

    private void initList(ArrayList<LocalMedia> list) {
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            list.add(String.valueOf(i));
//        }

        list.add(list.size(), new LocalMedia());

        adapter = new RBaseAdapter<LocalMedia>(mContext, R.layout.item_take_photo_layout, list) {
            @Override
            protected void fillItem(RViewHolder holder, LocalMedia item, int position) {
                RoundImageView iv = holder.getView(R.id.iv);
                ImageView ivClose = holder.getView(R.id.ivClose);
                if (list.size() > 9) {
                    if (position > 8) {
                        iv.setVisibility(View.GONE);
                        ivClose.setVisibility(View.GONE);
                    } else {
                        Glide.with(mContext).load(item.getPath()).into(iv);
                        ivClose.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (position == list.size() - 1) {
                        iv.setBackgroundResource(R.mipmap.icon_upload_img);
                        ivClose.setVisibility(View.INVISIBLE);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @SuppressLint("CheckResult")
                            @Override
                            public void onClick(View v) {
                                new RxPermissions(TakePhotoActivity.this).request(Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        if (aBoolean) {
                                            //申请的权限全部允许
                                            openGallery();
                                        } else {
                                            //只要有一个权限被拒绝，就会执行
                                            showToast("未授权权限，部分功能不能使用");
                                        }
                                    }
                                });
                            }
                        });
                    } else {
                        Glide.with(mContext).load(item.getPath()).into(iv);
                        ivClose.setVisibility(View.VISIBLE);
                    }
                }
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            protected int getViewType(LocalMedia item, int position) {
                return 0;
            }
        };
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(adapter);
    }

    private void openGallery() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofAll())
                .maxSelectNum(9)// 最大图片选择数量
                .selectionMode(PictureConfig.MULTIPLE)
                .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                .enableCrop(false)// 是否裁剪
                .compress(true)// 是否压缩
//                .compressSavePath(getSDCardPath())//压缩图片保存地址
                .synOrAsy(true)//同步true或异步false 压缩 默认同步
                .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                .withAspectRatio(1, 1)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                .minimumCompressSize(100)// 小于100kb的图片不压缩
                .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
    }

    public static String getSDCardPath() {
        String SDPATH = AppConfig.PHOTO_PATH;
        File file = new File(SDPATH);
        if (file.mkdirs()) {
            return SDPATH;
        }
        return SDPATH;
    }

    private void initTitle() {
        bottomLineView.setVisibility(View.GONE);
        bg.setBackgroundColor(getResources().getColor(R.color.theme_color));
        backLayout.setVisibility(View.VISIBLE);
        back.setImageResource(R.mipmap.icon_white_back);
        title.setTextColor(getResources().getColor(R.color.colorWhite));
        title.setText("上传照片");
    }

    @OnClick({R.id.backLayout, R.id.tvOk})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.backLayout:
                backToActivity();
                break;
            case R.id.tvOk:
                UpdatePic();
                break;
        }
    }

    private void UpdatePic() {
        PostFormBuilder builder = OkHttpUtils.post().url(Api.GET_UPDATAIMAGES);
        for (int i = 0; i < localMedia.size(); i++) {
            if (!TextUtils.isEmpty(localMedia.get(i).getPath())) {
                File f = new File(localMedia.get(i).getPath());
                builder.addFile("file[" + i + "]", f.getName(), f);
            }
        }
        builder.tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UpdataImagesResonse resp = JsonUtils.parseByGson(response, UpdataImagesResonse.class);
                        if (resp != null) {
                            showToast(resp.getMessage());
                            if (HttpResponse.SUCCESS.equals(resp.getCode())) {
                                List<String> path = resp.getResult().getPath();
                                OkHttpUtils.post()
                                        .url(Api.GET_CHECK_TAKE_CAR_UPLOAD_PIC)
                                        .addParams("orderId", orderId)
                                        .addParams("file",path.toString())
                                        .tag(this)
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {

                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                CommonResonse parse = JsonUtils.parseByGson(response, CommonResonse.class);
                                                if (parse!=null){
                                                    showToast(parse.getMessage());
                                                    if (HttpResponse.SUCCESS.equals(parse.getCode())){
                                                        finish();
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    localMedia = PictureSelector.obtainMultipleResult(data);
                    initList((ArrayList<LocalMedia>) localMedia);

                    break;
            }
        }
    }

    private void ClearCache() {
        // 清空图片缓存，包括裁剪、压缩后的图片 注意:必须要在上传完成后调用 必须要获取权限
        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new io.reactivex.Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            PictureFileUtils.deleteAllCacheDirFile(mContext);
                        } else {
                            showToast(getString(R.string.picture_jurisdiction));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
