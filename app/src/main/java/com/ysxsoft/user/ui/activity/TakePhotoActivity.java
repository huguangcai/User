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
import com.ysxsoft.common_base.utils.ImageUtils;
import com.ysxsoft.common_base.view.custom.image.RoundImageView;
import com.ysxsoft.user.ARouterPath;
import com.ysxsoft.user.R;
import com.ysxsoft.user.base.RBaseAdapter;
import com.ysxsoft.user.base.RViewHolder;
import com.ysxsoft.user.config.AppConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import io.reactivex.functions.Consumer;

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
    private RBaseAdapter<String> adapter;
    private RxPermissions r;
    private BGAPhotoHelper mPhotoHelper;

    public static void start() {
        ARouter.getInstance().build(ARouterPath.getTakePhotoActivity()).navigation();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_take_photo_layout;
    }

    @Override
    public void doWork() {
        super.doWork();
        initTitle();

        initPhotoHelper();
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

    private void initList(List<String> selectedPhotos) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(String.valueOf(i));
        }
        list.add(list.size(), "");

        adapter = new RBaseAdapter<String>(mContext, R.layout.item_take_photo_layout, list) {
            @Override
            protected void fillItem(RViewHolder holder, String item, int position) {
                RoundImageView iv = holder.getView(R.id.iv);
                ImageView ivClose = holder.getView(R.id.ivClose);
//                if (list.size()-1 <= 0) {
//                    iv.setBackgroundResource(R.mipmap.icon_upload_img);
//                    ivClose.setVisibility(View.INVISIBLE);
//                    iv.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            choicePhotoWrapper();
//                        }
//                    });
//                } else {
                    if (list.size() - 1 == position) {
                        iv.setBackgroundResource(R.mipmap.icon_upload_img);
                        ivClose.setVisibility(View.INVISIBLE);
                        iv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                choicePhotoWrapper();
                            }
                        });
                    } else {
                        iv.setBackgroundResource(R.mipmap.ic_launcher);
                        ivClose.setVisibility(View.VISIBLE);
                    }
                    ivClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                    });
//                }
            }

            @Override
            protected int getViewType(String item, int position) {
                return 0;
            }
        };

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        recyclerView.setAdapter(adapter);
    }

    private static final int RC_CHOOSE_PHOTO = 0x01;
    public static final int REQUEST_CODE_CROP = 0x02;

    @SuppressLint("CheckResult")
    private void choicePhotoWrapper() {
        new RxPermissions(this).request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
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

                break;
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
                        initList(selectedPhotos);
                    }
                    break;
                case REQUEST_CODE_CROP:
                    String cropPath = mPhotoHelper.getCropFilePath();
                    String path = ImageUtils.compress(this, System.currentTimeMillis() + "", new File(cropPath), AppConfig.PHOTO_PATH);
//                    //裁剪后的
//                    Glide.with(mContext).load(new File(path)).into(civHead);
//                    editLogo(path);
                    break;
                default:
                    break;
            }
        }
    }
}
