package com.ysxsoft.common_base.map;

import android.Manifest;
import android.app.Activity;
import android.os.Build;

import androidx.fragment.app.FragmentActivity;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.ysxsoft.common_base.utils.ToastUtils;

import io.reactivex.functions.Consumer;


/**
 * create by Sincerly on 2019/1/22 0022
 **/
public class GDMapUtils {
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    public static final int REQUEST_PERMISION = 0x99;

    public void startOnceLocation(final Activity activity, final AMapLocationListener listener) {
        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
                //初始化定位
                mlocationClient = new AMapLocationClient(activity);
                //初始化定位参数
                mLocationOption = new AMapLocationClientOption();
                //设置定位回调监听
                mlocationClient.setLocationListener(listener);
                //设置为高精度定位模式
                mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                //设置定位参数
                mLocationOption.setOnceLocation(true);
                mlocationClient.setLocationOption(mLocationOption);
                mlocationClient.startLocation();//启动定位
            } else {
                rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            //初始化定位
                            mlocationClient = new AMapLocationClient(activity);
                            //初始化定位参数
                            mLocationOption = new AMapLocationClientOption();
                            //设置定位回调监听
                            mlocationClient.setLocationListener(listener);
                            //设置为高精度定位模式
                            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
                            //设置定位参数
                            mLocationOption.setOnceLocation(true);
                            mlocationClient.setLocationOption(mLocationOption);
                            mlocationClient.startLocation();//启动定位
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            ToastUtils.shortToast(activity, "请在设置中开启权限");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            ToastUtils.shortToast(activity, "请在设置中开启权限");
//                                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                    Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
//                                    intent.setData(uri);
//                                    activity.startActivityForResult(intent, REQUEST_PERMISION);
                        }
                    }
                });
            }
        } else {
            //初始化定位
            mlocationClient = new AMapLocationClient(activity);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(listener);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mLocationOption.setOnceLocation(true);
            mlocationClient.setLocationOption(mLocationOption);
            mlocationClient.startLocation();//启动定位
        }
    }

}
