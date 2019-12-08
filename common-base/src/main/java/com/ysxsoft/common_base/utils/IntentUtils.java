package com.ysxsoft.common_base.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.ysxsoft.common_base.R;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.util.BGAPhotoHelper;
import cn.bingoogolapple.photopicker.util.BGAPhotoPickerUtil;
import dalvik.system.DexClassLoader;

import static android.content.Context.MODE_PRIVATE;
import static android.content.pm.PackageManager.GET_ACTIVITIES;

/**
 * Created by Sincerly on 2018/5/24 0024.
 * <p>
 * {@link #call(Context context, String phoneNum)}  			   									打电话
 * {@link #call(Context context, String phoneNum)}  			   									打电话前编辑
 * {@link #openBrowser(Context context, String url)}  			   									打开浏览器
 * {@link #takeCamera(Activity activity, String filePath, int requestCode)}  			   			打开相机
 * {@link #takeImage(Activity activity, String filePath, int requestCode)}  			   			打开相册
 * {@link #gotoActivity(Context context, Class clazz)}  			   								打开Activity
 * {@link #gotoActivity(Context context, String targetAction)}  			   						打开Activity(隐式)
 * {@link #gotoActivityForResult(Activity activity, Class clazz, int requestCode)}  			   	打开Activity携带请求码
 * {@link #sendBroadCastReceiver(Context context, String action)}  			   						发送广播
 * {@link #openQQ(Context context, String qq)}  			   										打开QQ客服聊天
 * {@link #loadInstalledApk(Context c, String packName, String activityName)}  			   			打开已安装apk的指定activity
 * {@link #sendSMSMessage(Context context,String phoneNo,String message)}  			   			    发送短信
 */

public class IntentUtils {

    public static long lastClickTime = 0;

    /**
     * 打电话
     *
     * @param context
     * @param phoneNum
     */
    public static void call(Context context, String phoneNum) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel://" + phoneNum));
        context.startActivity(intent);
    }

    /**
     * 跳转到拨号界面，用户手动点击拨打
     *
     * @param context
     * @param phoneNum
     */
    public static void callEdit(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    /**
     * 打开浏览器
     *
     * @param context
     * @param url
     */
    public static void openBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * 打开相机 未测试
     */
    public static void takeCamera(Activity activity, String filePath, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(filePath)));
        activity.startActivity(intent);
    }

    /**
     * 打开相册  未测试
     */
    public static void takeImage(Activity activity, String filePath, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
    }

    public static void gotoActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    public static void gotoActivity(Context context, String targetAction) {
        Intent intent = new Intent();
        intent.setAction(targetAction);
        context.startActivity(intent);
    }

    public static void gotoActivityForResult(Activity activity, Class clazz, int requestCode) {
        Intent intent = new Intent(activity, clazz);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void sendBroadCastReceiver(Context context, String action) {
        Intent intent = new Intent();
        intent.setAction(action);
        context.sendBroadcast(intent);
    }

    public static void openQQ(Context context, String qq) {
        if (ApplicationUtils.isInstalled(context, "com.tencent.qqlite") || ApplicationUtils.isInstalled(context, "com.tencent.mobileqq")) {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;//tips : QQ account can't be your own
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } else {
            Toast.makeText(context, "抱歉！请安装QQ!", Toast.LENGTH_SHORT).show();
        }
    }

    public static Intent getCropIntent(Context context, String inputFilePath, String outFilePath, int width, int height) throws IOException {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        Uri uri = null;
        File file = new File(inputFilePath);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String authority = ApplicationUtils.getPackageName(context) + ".file_provider";
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(outFilePath)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    public static Intent getCropIntent(Context context, String inputFilePath, String outFilePath) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        Uri uri = null;
        File file = new File(inputFilePath);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String authority = ApplicationUtils.getPackageName(context) + ".file_provider";
            uri = FileProvider.getUriForFile(context, authority, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", false);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(outFilePath)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        return intent;
    }

    /**
     * 检测点击时间
     *
     * @return
     */
    public static boolean detectionForDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 加载已安装apk
     * <p>
     * IntentUtils.loadInstalledApk(this,"com.QIKU.LFF","com.unity3d.player.UnityPlayerActivity");
     *
     * @param c
     * @param packName
     * @param activityName
     */
    public static void loadInstalledApk(Context c, String packName, String activityName) {
        try {
            String pkgName = packName;
            Context context = c.createPackageContext(pkgName,
                    Context.CONTEXT_IGNORE_SECURITY | Context.CONTEXT_INCLUDE_CODE);
            // 获取动态加载得到的资源
            Resources resources = context.getResources();
            // 过去该apk中的字符串资源"tips"， 并且toast出来，apk换肤的实现就是这种原理
//			String toast = resources.getString(resources.getIdentifier("tips", "string", pkgName));
//			Toast.makeText(c, ""+toast, Toast.LENGTH_SHORT).show();
//			Class cls = context.getClassLoader().loadClass(pkgName + ".UninstallApkActivity");
            Class cls = context.getClassLoader().loadClass(activityName);
            c.startActivity(new Intent(context, cls));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d("IntentUtils", e == null ? "" : e.toString());
        }
    }

    /**
     * 加载未安装apk的activity
     *
     * @param activity
     * @param apkPath
     */
    public static void loadUnInstalledApk(Activity activity, String apkPath) {
        PackageInfo packageInfo = activity.getPackageManager().getPackageArchiveInfo(apkPath, GET_ACTIVITIES);
        ActivityInfo[] activityInfos = packageInfo.activities;
        if ((packageInfo.activities != null)
                && (packageInfo.activities.length > 0)) {
            String activityName = packageInfo.activities[0].name;//获取插件中第一个activity
            String mClass = activityName;
//			String mClass = "com.unity3d.player.UnityPlayerProxyActivity";
            File dexOutputDir = activity.getDir("dex", MODE_PRIVATE);
            String dexOutputPath = dexOutputDir.getAbsolutePath();
            ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
            DexClassLoader dexClassLoader = new DexClassLoader(apkPath, dexOutputPath, null, localClassLoader);
            try {
                Class<?> localClass = dexClassLoader.loadClass(mClass);//创建插件Activity的实例
                //调用插件Activity的构造函数，将当前activity的上下文传递到插件activity中去
                Constructor<?> localConstructor = localClass.getConstructor(new Class[]{});
                Object instance = localConstructor.newInstance(new Object[]{});
                Method[] m = localClass.getMethods();
                Method onCreate = localClass.getDeclaredMethod("onCreate", new Class[]{Bundle.class});
                onCreate.setAccessible(true);
                Bundle bundle = new Bundle();
                onCreate.invoke(instance, bundle);//调用插件activity的onCreate方法
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage());
            }
        }
    }

    /**
     * 加载未安装apk的资源
     *
     * @param context
     */
    public static void loadUnInstalledResource(Context context, String packageName, String apkPath) {
        String dexPath = context.getDir("dex", Context.MODE_PRIVATE).getAbsolutePath();
        DexClassLoader classLoader = new DexClassLoader(apkPath, dexPath, null, context.getClassLoader());
        try {
            //反射得到R文件的内部类drawable
            Class<?> drawable_clazz = classLoader.loadClass(packageName + ".R$drawable");
            //得到drawable类的所有属性
            Field[] fields = drawable_clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.getName().equals("ten")) {
//					int id = field.getInt(new R.id());
//					imageViewInstall.setBackground(getUnInstalledResource().getDrawable(id));
                }
            }
            //反射得到R文件的内部类string
            Class<?> string_clazz = classLoader.loadClass(packageName + ".R$string");
            StringBuffer sb = new StringBuffer();
            //得到string内部类的所有属性，这些属性就是我们在string.xml文件中生命的字符串资源
            Field[] fields2 = string_clazz.getDeclaredFields();
            int id = 0;
            for (Field field : fields2) {
                //得到对应的字符串资源的id值
//				id = field.getInt(new R.id());
//				sb.append(getUnInstalledResource().getString(id));
            }
            Toast.makeText(context, sb.toString(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startService(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.startService(intent);
    }

    public static void stopService(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        activity.stopService(intent);
    }


    /**
     * 安装apk
     */
    public void installApk(Context context, String path) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.View");
        intent.addCategory("android.intent.category.DEFAULT");
        Uri data = Uri.parse("file://" + path);
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 跳转相机拍照
     */
    public static void startToCameraByBGAPicker(BGAPhotoHelper mPhotoHelper, Activity activity, int REQUEST_CODE_TAKE_PHOTO) {
        try {
            activity.startActivityForResult(mPhotoHelper.getTakePhotoIntent(), REQUEST_CODE_TAKE_PHOTO);
        } catch (Exception e) {
            BGAPhotoPickerUtil.show(R.string.bga_pp_not_support_take_photo);
        }
    }

    public static void open(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setDataAndType(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, MimeTypeMap.getSingleton().getExtensionFromMimeType("audio"));
        activity.startActivity(intent);
    }

    public static void openNotify(Context context) {
//        try {
//            // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
//            Intent intent = new Intent();
//            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//            //这种方案适用于 API 26, 即8.0（含8.0）以上可以用
//            intent.putExtra(EXTRA_APP_PACKAGE, context.getPackageName());
//            intent.putExtra(EXTRA_CHANNEL_ID, context.getApplicationInfo().uid);
//
//            //这种方案适用于 API21——25，即 5.0——7.1 之间的版本可以使用
//            intent.putExtra("app_package", context.getPackageName());
//            intent.putExtra("app_uid", context.getApplicationInfo().uid);
//            // 小米6 -MIUI9.6-8.0.0系统，是个特例，通知设置界面只能控制"允许使用通知圆点"——然而这个玩意并没有卵用，我想对雷布斯说：I'm not ok!!!
//            //  if ("MI 6".equals(Build.MODEL)) {
//            //      intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            //      Uri uri = Uri.fromParts("package", getPackageName(), null);
//            //      intent.setData(uri);
//            //      // intent.setAction("com.android.settings/.SubSettings");
//            //  }
//            context.startActivity(intent);
//        } catch (Exception e) {
//            e.printStackTrace();
//            // 出现异常则跳转到应用设置界面：锤子坚果3——OC105 API25
//            Intent intent = new Intent();
//            //下面这种方案是直接跳转到当前应用的设置界面。
//            //https://blog.csdn.net/ysy950803/article/details/71910806
//            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            Uri uri = Uri.fromParts("package", getPackageName(), null);
//            intent.setData(uri);
//            context.startActivity(intent);
//        }
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(localIntent);
    }

    /**
     * 跳转拍照
     * @param activity
     * @param REQUEST_IMAGE_CAPTURE
     */
    private void dispatchTakePictureIntent(Activity activity,int REQUEST_IMAGE_CAPTURE ) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            imageView.setImageBitmap(imageBitmap);
//        }
    }

    /**
     * 跳转录制视频
     * @param activity
     * @param REQUEST_VIDEO_CAPTURE
     */
    private void dispatchTakeVideoIntent(Activity activity,int REQUEST_VIDEO_CAPTURE) {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
//        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
//            Uri videoUri = intent.getData();
//            videoView.setVideoURI(videoUri);
//        }
    }

    protected void sendSMSMessage(Context context,String phoneNo,String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, message, null, null);
            ToastUtils.shortToast(context,"短信已发送！");
        } catch (Exception e) {
            ToastUtils.shortToast(context,"发送短信失败！");
            e.printStackTrace();
        }
    }
}
