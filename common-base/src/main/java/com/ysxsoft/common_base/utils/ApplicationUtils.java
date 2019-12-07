package com.ysxsoft.common_base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sincerly on 2018/5/24 0024.
 * {@link #getAppName(Context context)}    													获取应用名称
 * {@link #getVersionName(Context context)}    												获取应用VersionName
 * {@link #getVersionCode(Context context)}    												获取应用VersionCode
 * {@link #getMeteDataValue(Context context, String key)}    								获取应用AndroidManifest.xml元数据
 * {@link #getAppIcon(Context context, String packageName)}    								获取应用图标
 * {@link #getAppFirstInstallTime(Context context, String packageName)}    					获取应用第一次安装时间
 * {@link #getAppLastUpdateTime(Context context, String packageName)}    					获取应用更新时间
 * {@link #getAppSize(Context context, String packageName)}    								获取应用更新时间
 * {@link #getAppApkPath(Context context, String packageName)}    							获取应用apk路径
 * {@link #getAppInstaller(Context context)}    											获取应用安装市场
 * {@link #getAppSign(Context context)}    													获取应用签名
 * {@link #getPackageName(Context context)}    												获取应用包名
 * {@link #getAppTargetSdkVersion(Context context, String packageName)}    					获取应用sdk版本
 * {@link #getAppUid(Context context, String packageName)}    								获取应用的uid
 * {@link #getNumCores()}    																获取CPU内核数量
 * {@link #getRootPermission(Context context)}    											获取Root权限
 * {@link #getAppPermissions(Context context, String packname)}    							获取应用的所有权限
 * {@link #hasPermission(Context context, String permission)}    							判断应用是否拥有权限
 * {@link #isInstalled(Context context, String packageName)}    							判断应用是否安装
 * {@link #uninstallApk(Context context, String packageName)}    							卸载应用
 * {@link #isSystemApp(Context context, String packageName)}    							判断应用是否是系统应用
 * {@link #isServiceRunning(Context context, String className)}    							判断服务是否在运行
 * {@link #stopRunningService(Context context, String className)}    						停止服务
 * {@link #killProcesses(Context context, int pid, String processName)}    					结束进程
 * {@link #runScript(String script)}    													运行脚本
 * {@link #runApp(Context context, String packagename)}    									启动应用
 * {@link #getApplicationMetaData(Context context, String key)}    							获取MetaData值
 * {@link #getPermissions(Context context)}    												获取权限列表
 * {@link #getPermissions(Context context,String packageName)}    							获取其他应用权限列表
 * {@link #getUninstallApkInfo(Context context, String apkPath)}    						获取未安装apk的信息
 * {@link #getUninstallApkResources(Context context, String apkPath)}    					获取未安装apk的Resources对象
 * {@link #getCurrentProcessName(Context context)}    										获取当前进程名称
 * {@link #getProcessName(int pid)}    														获取pid对应的进程名
 */

public class ApplicationUtils {
	/**
	 * 获取本地apk的名称
	 * @param context 上下文
	 * @return String
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager e = context.getPackageManager();
			PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (PackageManager.NameNotFoundException var4) {
			var4.printStackTrace();
			return "unKnown";
		}
	}

	/**
	 * 获取本地Apk版本名称
	 * @param context 上下文
	 * @return String
	 */
	public static String getVersionName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			LogUtils.e("AppApplicationMgr-->>getVerName()", e.getMessage() + "获取本地Apk版本名称失败！");
			e.printStackTrace();
		}
		return verName;
	}

	/**
	 * 获取本地Apk版本号
	 * @param context 上下文
	 * @return int
	 */
	public static int getVersionCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			LogUtils.e("AppApplicationMgr-->>getVerCode()", e.getMessage() + "获取本地Apk版本号失败！");
			e.printStackTrace();
		}
		return verCode;
	}

	/**
	 * 根据key获取xml中Meta的值
	 * @param context 上下文
	 * @param key
	 * @return
	 */
	public static String getMeteDataValue(Context context, String key){
		String result="";
		try {
			ApplicationInfo info=context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
			return info.metaData.getString(key);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取应用图标
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static Drawable getAppIcon(Context context, String packageName) {
		PackageManager pm = context.getPackageManager();
		Drawable appIcon = null;
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
			appIcon = applicationInfo.loadIcon(pm);
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return appIcon;
	}

	/**
	 * 获取应用第一次安装日期
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static long getAppFirstInstallTime(Context context, String packageName) {
		long lastUpdateTime = 0;
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			lastUpdateTime = packageInfo.firstInstallTime;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return lastUpdateTime;
	}

	/**
	 * 获取应用更新日期
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static long getAppLastUpdateTime(Context context, String packageName) {
		long lastUpdateTime = 0;
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			lastUpdateTime = packageInfo.lastUpdateTime;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return lastUpdateTime;
	}

	/**
	 * 获取应用大小
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static long getAppSize(Context context, String packageName) {
		long appSize = 0;
		try {
			ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
			appSize = new File(applicationInfo.sourceDir).length();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return appSize;
	}

	/**
	 * 获取应用apk文件
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getAppApkPath(Context context, String packageName) {
		String sourceDir = null;
		try {
			ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
			sourceDir = applicationInfo.sourceDir;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return sourceDir;
	}

	/**
	 * 获取应用的安装市场
	 * @param context
	 * @return
	 */
	public static String getAppInstaller(Context context) {

		return context.getPackageManager().getInstallerPackageName(context.getPackageName());
	}

	/**
	 * 获取应用签名
	 * @param context
	 * @return
	 */
	public static String getAppSign(Context context) {
		String packageName=context.getPackageName();
		try {
			PackageInfo pis = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
			return hexdigest(pis.signatures[0].toByteArray());
		} catch (PackageManager.NameNotFoundException e) {
			throw new RuntimeException(ApplicationUtils.class.getName() + "the " + packageName + "'s application not found");
		}
	}

	/**
	 * 获得包名
	 *
	 * @param context 上下文
	 * @return 包名
	 */
	public static String getPackageName(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String hexdigest(byte[] paramArrayOfByte) {
		final char[] hexDigits = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
		try {
			MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
			localMessageDigest.update(paramArrayOfByte);
			byte[] arrayOfByte = localMessageDigest.digest();
			char[] arrayOfChar = new char[32];
			for (int i = 0, j = 0; ; i++, j++) {
				if (i >= 16) {
					return new String(arrayOfChar);
				}
				int k = arrayOfByte[i];
				arrayOfChar[j] = hexDigits[(0xF & k >>> 4)];
				arrayOfChar[++j] = hexDigits[(k & 0xF)];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取应用兼容sdk
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getAppTargetSdkVersion(Context context, String packageName) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			return applicationInfo.targetSdkVersion;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取应用uid
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static int getAppUid(Context context, String packageName) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
			ApplicationInfo applicationInfo = packageInfo.applicationInfo;
			return applicationInfo.uid;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取Cpu内核数
	 * @return
	 */
	public static int getNumCores() {
		try {
			File dir = new File("/sys/devices/system/cpu/");
			File[] files = dir.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return Pattern.matches("cpu[0-9]", pathname.getName());
				}

			});
			return files.length;
		} catch (Exception e) {
			e.printStackTrace();
			return 1;
		}
	}

	/**
	 * 获得root权限
	 * @param context
	 * @return
	 */
	public static boolean getRootPermission(Context context) {
		String packageCodePath = context.getPackageCodePath();
		Process process = null;
		DataOutputStream os = null;
		try {
			String cmd = "chmod 777 " + packageCodePath;
			process = Runtime.getRuntime().exec("su");
			os = new DataOutputStream(process.getOutputStream());
			os.writeBytes(cmd + "\n");
			os.writeBytes("exit\n");
			os.flush();
			process.waitFor();
		} catch (Exception e) {
			return false;
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				process.destroy();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 获取应用的所有权限
	 * @param context
	 * @param packname
	 * @return
	 */
	public static String[] getAppPermissions(Context context, String packname) {
		String[] requestedPermissions = null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(packname, PackageManager.GET_PERMISSIONS);
			requestedPermissions = info.requestedPermissions;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return requestedPermissions;
	}

	/**
	 * 是否有权限
	 * @param context
	 * @param permission
	 * @return
	 */
	public static boolean hasPermission(Context context, String permission) {
		if (context != null && !TextUtils.isEmpty(permission)) {
			try {
				PackageManager packageManager = context.getPackageManager();
				if (packageManager != null) {
					if (PackageManager.PERMISSION_GRANTED == packageManager.checkPermission(permission, context.getPackageName())) {
						return true;
					}
					Log.d("AppUtils", "Have you  declared permission " + permission + " in AndroidManifest.xml ?");
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	/**
	 * 应用是否安装
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isInstalled(Context context, String packageName) {
		boolean installed = false;
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		List<ApplicationInfo> installedApplications = context.getPackageManager().getInstalledApplications(0);
		for (ApplicationInfo in : installedApplications) {
			if (packageName.equals(in.packageName)) {
				installed = true;
				break;
			} else {
				installed = false;
			}
		}
		ActivityManager activityManager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos=activityManager.getRunningAppProcesses();
		for (int i = 0; i <runningAppProcessInfos.size() ; i++) {
			ActivityManager.RunningAppProcessInfo info=runningAppProcessInfos.get(i);
			Log.e("tag",info.processName);
		}
		return installed;
	}

	/**
	 * 安装应用
	 * @param context
	 * @param filePath
	 * @return
	 */
	public static boolean installApk(Context context, String filePath) {
		File file = new File(filePath);
		if (!file.exists() || !file.isFile() || file.length() <= 0) {
			return false;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 卸载应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean uninstallApk(Context context, String packageName) {
		if (TextUtils.isEmpty(packageName)) {
			return false;
		}
		Intent i = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + packageName));
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
		return true;
	}

	/**
	 * 是否是系统应用
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static boolean isSystemApp(Context context, String packageName) {
		boolean isSys = false;
		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo applicationInfo = pm.getApplicationInfo(packageName, 0);
			if (applicationInfo != null && (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
				isSys = true;
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
			isSys = false;
		}
		return isSys;
	}

	/**
	 * 服务是否在运行
	 * @param context
	 * @param className
	 * @return
	 */
	public static boolean isServiceRunning(Context context, String className) {
		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);
		for (ActivityManager.RunningServiceInfo si : servicesList) {
			if (className.equals(si.service.getClassName())) {
				isRunning = true;
			}
		}
		return isRunning;
	}

	/**
	 * 停止服务
	 * @param context
	 * @param className
	 * @return
	 */
	public static boolean stopRunningService(Context context, String className) {
		Intent intent_service = null;
		boolean ret = false;
		try {
			intent_service = new Intent(context, Class.forName(className));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (intent_service != null) {
			ret = context.stopService(intent_service);
		}
		return ret;
	}

	/**
	 * 结束进程
	 * @param context
	 * @param pid
	 * @param processName
	 */
	public static void killProcesses(Context context, int pid, String processName) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName;
		try {
			if (!processName.contains(":")) {
				packageName = processName;
			} else {
				packageName = processName.split(":")[0];
			}
			activityManager.killBackgroundProcesses(packageName);
			Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);
			forceStopPackage.invoke(activityManager, packageName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 运行脚本
	 * @param script
	 * @return
	 */
	public static String runScript(String script) {
		String sRet;
		try {
			final Process m_process = Runtime.getRuntime().exec(script);
			final StringBuilder sbread = new StringBuilder();
			Thread tout = new Thread(new Runnable() {
				public void run() {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(m_process.getInputStream()),
							8192);
					String ls_1;
					try {
						while ((ls_1 = bufferedReader.readLine()) != null) {
							sbread.append(ls_1).append("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							bufferedReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			tout.start();

			final StringBuilder sberr = new StringBuilder();
			Thread terr = new Thread(new Runnable() {
				public void run() {
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(m_process.getErrorStream()),
							8192);
					String ls_1;
					try {
						while ((ls_1 = bufferedReader.readLine()) != null) {
							sberr.append(ls_1).append("\n");
						}
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							bufferedReader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			terr.start();

			m_process.waitFor();
			while (tout.isAlive()) {
				Thread.sleep(50);
			}
			if (terr.isAlive())
				terr.interrupt();
			String stdout = sbread.toString();
			String stderr = sberr.toString();
			sRet = stdout + stderr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return sRet;
	}

	/**
	 * 启动应用
	 * @param context
	 * @param packagename
	 */
	public static void runApp(Context context, String packagename) {
		context.startActivity(new Intent(context.getPackageManager().getLaunchIntentForPackage(packagename)));
	}

	/**
	 * 获取application层级的metadata
	 *
	 * @param context 上下文
	 * @param key     key
	 * @return value
	 */
	public static String getApplicationMetaData(Context context, String key) {
		try {
			Bundle metaData = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA).metaData;
			return metaData.get(key).toString();
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获得应用申明的所有权限列表
	 * @param context 上下文
	 * @return 获得应用申明的所有权限列表
	 */
	public static List<String> getPermissions(Context context){
		List<String> permissions=new ArrayList<String>();
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
			permissions.addAll(Arrays.asList(packageInfo.requestedPermissions));

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return permissions;
	}

	public static List<String> getPermissions(Context context,String packageName){
		List<String> permissions=new ArrayList<String>();
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
			permissions.addAll(Arrays.asList(packageInfo.requestedPermissions));

		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return permissions;
	}


	/**
	 * 获取未安装apk的信息
	 * @param context
	 * @param apkPath apk文件的path
	 * @return
	 */
	public static String[] getUninstallApkInfo(Context context, String apkPath) {
		String[] info = new String[2];
		PackageManager pm = context.getPackageManager();
		PackageInfo pkgInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
		if (pkgInfo != null) {
			ApplicationInfo appInfo = pkgInfo.applicationInfo;
			String versionName = pkgInfo.versionName;//版本号
			Drawable icon = pm.getApplicationIcon(appInfo);//图标
			String appName = pm.getApplicationLabel(appInfo).toString();//app名称
			String pkgName = appInfo.packageName;//包名
			info[0] = appName;
			info[1] = pkgName;
		}
		return info;
	}

	/**
	 * 获取未安装apk的Resource
	 * @param context
	 * @param apkPath
	 * @return 得到对应插件的Resource对象
	 */
	public static Resources getUninstallApkResources(Context context,String apkPath) {
		try {
			AssetManager assetManager = AssetManager.class.newInstance();
			Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);//反射调用方法addAssetPath(String path)
			//第二个参数是apk的路径：Environment.getExternalStorageDirectory().getPath()+File.separator+"plugin"+File.separator+"apkplugin.apk"
			addAssetPath.invoke(assetManager, apkPath);//将未安装的Apk文件的添加进AssetManager中，第二个参数为apk文件的路径带apk名
			Resources superRes = context.getResources();
			Resources mResources = new Resources(assetManager, superRes.getDisplayMetrics(),
					superRes.getConfiguration());
			return mResources;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取当前进程
	 * @param context
	 * @return
	 */
	public  static String getCurrentProcessName(Context context){
		String currentProcessName="";
		ActivityManager activityManager= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> processInfo=activityManager.getRunningAppProcesses();
		int pid= android.os.Process.myPid();
		for (int i = 0; i <processInfo.size(); i++) {
			ActivityManager.RunningAppProcessInfo a=processInfo.get(i);
			if(a.pid==pid){
				currentProcessName=a.processName;
				break;
			}
		}
		return currentProcessName;
	}

	/**
	 * 获取进程号对应的进程名
	 *
	 * @param pid 进程号
	 * @return 进程名
	 */
	public static String getProcessName(int pid) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
			String processName = reader.readLine();
			if (!TextUtils.isEmpty(processName)) {
				processName = processName.trim();
			}
			return processName;
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		return null;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 判断网络类型
	 *
	 * @param context
	 * @return
	 */
	public static String NetType(Context context) {
		try {
			ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
			if (typeName.equalsIgnoreCase("wifi")) {
			} else {
				typeName = info.getExtraInfo().toLowerCase();
				// 3gnet/3gwap/uninet/uniwap/cmnet/cmwap/ctnet/ctwap
			}
			return typeName;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 网络是否可用
	 *
	 * @param mContext
	 * @return
	 */
	public static boolean isNetworkAvaiable(Context mContext) {
		ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		return (info != null && info.isConnected());
	}

	/**
	 * 关闭软键盘
	 *
	 * @param mContext
	 * @param view
	 */
	public static void colseKeyboard(Context mContext, View view) {
		try {
			InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		} catch (Exception e) {
		}
	}

	/**
	 * 关闭手机软件盘
	 *
	 * @param activity
	 */
	public static void colsePhoneKeyboard(Activity activity) {
		InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive() && activity.getCurrentFocus() != null) {
			if (activity.getCurrentFocus().getWindowToken() != null) {
				imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}
	}

	/**
	 * 打开软件盘
	 *
	 * @param mContext
	 */
	public static void openKeyboard(Context mContext) {
		InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	/**
	 * 启动app
	 *
	 * @param context
	 * @param appName
	 */
	public static void launchApp(Context context, String appName) {
		PackageManager packageManager = context.getPackageManager();
		// 获取手机里的应用列表
		List<PackageInfo> pInfo = packageManager.getInstalledPackages(0);
		for (int i = 0; i < pInfo.size(); i++) {
			PackageInfo p = pInfo.get(i);
			// 获取相关包的<application>中的label信息，也就是-->应用程序的名字
			String label = packageManager
					.getApplicationLabel(p.applicationInfo).toString();
			System.out.println(label);
			if (label.equals(appName)) { // 比较label
				String pName = p.packageName; // 获取包名
				Intent intent = new Intent();
				// 获取intent
				intent = packageManager.getLaunchIntentForPackage(pName);
				context.startActivity(intent);

			}

		}
	}

	/**
	 * 分享功能
	 *
	 * @param mContext      上下文
	 * @param activityTitle Activity的名字
	 * @param msgTitle      消息标题
	 * @param msgText       消息内容
	 * @param imgPath       图片路径，不分享图片则传null
	 */
	public static void shareMsg(Context mContext, String activityTitle,
								String msgTitle, String msgText, String imgPath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		if (imgPath == null || imgPath.equals("")) {
			intent.setType("text/plain"); // 纯文本
		} else {
			File f = new File(imgPath);
			if (f != null && f.exists() && f.isFile()) {
				intent.setType("image/jpg");
				Uri u = Uri.fromFile(f);
				intent.putExtra(Intent.EXTRA_STREAM, u);
			}
		}
		intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
		intent.putExtra(Intent.EXTRA_TEXT, msgText);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mContext.startActivity(Intent.createChooser(intent, activityTitle));
	}

	/**
	 * GSON解析json数据
	 *
	 * @param json
	 * @param cls
	 */

	public static <T> T parse(String json, Type cls) {
		try {
			Gson gson = new Gson();
			return gson.fromJson(json, cls);
		} catch (Exception e) {
			Log.i("", "数据解析异常--" + e.getMessage());
		}
		return null;
	}

	/**
	 * 拍照sd卡地址
	 **/
	public static String getImageSDpath() {
		if (Build.MANUFACTURER.equalsIgnoreCase("meizu")) {
			return Environment.getExternalStorageDirectory().toString() + "/Camera/Nuctech";
		} else {
			return Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera/Nuctech";
		}
	}

	/**
	 * 创建图片名称
	 *
	 * @param dateTaken
	 * @return
	 */
	public static String createName(long dateTaken) {
		Date date = new Date(dateTaken);
		@SuppressLint("SimpleDateFormat")
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date);
	}
	/**
	 * 获取图标 bitmap
	 *
	 * @param context
	 */
	public static synchronized Bitmap getLogoBitmap(Context context) {
		PackageManager packageManager = null;
		ApplicationInfo applicationInfo = null;
		try {
			packageManager = context.getApplicationContext().getPackageManager();
			applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
		} catch (PackageManager.NameNotFoundException e) {
			applicationInfo = null;
		}
		Drawable d = packageManager.getApplicationIcon(applicationInfo); //xxx根据自己的情况获取drawable
		BitmapDrawable bd = (BitmapDrawable) d;
		Bitmap bm = bd.getBitmap();
		return bm;
	}

	/**
	 * 检测手机号的格式是否正确
	 *
	 * @param phonenumber
	 * @return
	 */
	public static boolean checkPhoneNum(String phonenumber) {
		String regExp = "^13[0-9]{9}$|^14[0-9]{9}$|^15[0-9]{9}$|^18[0-9]{9}$|^16[0-9]{9}$|^17[0-9]{9}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(phonenumber);
		return m.find();
	}

	/**
	 * 检测身份证号格式是否正确
	 *
	 * @param idNum
	 * @return
	 */
	public static boolean checkIdNum(String idNum) {
		String regExp = "^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(idNum);
		return m.find();
	}

	/**
	 * 核对车牌号
	 *
	 * @param carNBum
	 * @return
	 */
	public static boolean checkCarNum(String carNBum) {
		String regExp = "[\u4e00-\u9fa5]{1}[a-zA-Z]{1}[a-zA-Z_0-9]{5}";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(carNBum);
		return m.find();
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 手机震动
	 *
	 * @param mContext
	 */
	@SuppressLint("MissingPermission")
	public static void showRock(Context mContext) {
		Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = {100, 400, 100, 400}; // 停止 开启 停止 开启
		vibrator.vibrate(pattern, -1);// 重复两次上面的pattern 如果只想震动一次，index设为-1
		vibrator = null;
	}

	/**
	 * 播放系统提示音
	 *
	 * @param mContext
	 */
	public static void playSystemNotificationVoice(Context mContext) {
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(mContext, notification);
		r.play();
	}

	/**
	 * 播放自定义提示音
	 */
	public static void playCustomeVoice(Context mContext, int voiceId) {
		SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 3);
		soundPool.load(mContext, voiceId, 1);
		soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
			@Override
			public void onLoadComplete(SoundPool soundPool, int sampleId,
									   int status) {
				soundPool.play(1, 1, 1, 0, 0, 1);
				soundPool = null;
			}
		});

	}


	/**
	 * 获取屏幕宽
	 *
	 * @param mContext
	 * @return
	 */
	public static int getScreenWidth(Context mContext) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels;
	}

	/**
	 * 获取屏幕高
	 *
	 * @param mContext
	 * @return
	 */
	public static int getScreenHeight(Context mContext) {
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels;
	}

	/**
	 * 根据逗号分割字符串
	 *
	 * @param str
	 * @return
	 */
	public static String splitStr(String str) {
		String[] as = str.split(",");
		for (int i = 0; i < as.length; i++) {
			return as[i];
		}
		return str;
	}

	/**
	 * 根据点分割
	 *
	 * @param str
	 * @return
	 */
	public static String splitStrPoint(String str) {
		String[] as = str.split("\\.");
		for (int i = 0; i < as.length; i++) {
			return as[i];
		}
		return str;
	}

	/**
	 * 去掉双引号
	 *
	 * @param string
	 * @return
	 */
	public static String stringReplace(String string) {
		//去掉" "号
		String str = string.replace("\"", "");
		return str;

	}

	/**
	 * 下载完apk  通知系统安装  打开安装界面
	 *
	 * @param c
	 * @param fileSavePath apk路径
	 */
	private void openAPKInstall(Context c, String fileSavePath) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + fileSavePath), "application/vnd.android.package-archive");
		c.startActivity(intent);
	}

	/**
	 * 判断当前gps是否开启
	 *
	 * @param c
	 * @return
	 */
	public static boolean isGPSEnable(Context c) {
		/*
		 * 用Setting.System来读取也可以，只是这是更旧的用法 String str =
		 * Settings.System.getString(getContentResolver(),
		 * Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		 */
		String str = Settings.Secure.getString(c.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		Log.v("GPS", str);
		if (str != null) {
			return str.contains("gps");
		} else {
			return false;
		}
	}

	/**
	 * 判断GPS是否开启，GPS
	 *
	 * @param context
	 * @return true 表示开启
	 */
	public static final boolean isOPen(final Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (gps) {
			return true;
		}
		return false;

	}

	/**
	 * 辅助gps 是否开启
	 *
	 * @param context
	 * @return
	 */
	public static boolean isAGpsOpen(final Context context) {
		LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (network) {
			return true;
		}
		return false;
	}

	/**
	 * 强制帮用户打开GPS
	 *
	 * @param context
	 */
	public static final void openGPS(Context context) {
		Intent GPSIntent = new Intent();
		GPSIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
		GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
		GPSIntent.setData(Uri.parse("custom:3"));
		try {
			PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
		} catch (PendingIntent.CanceledException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 跳转到gps页面
	 *
	 * @param c
	 */
	public static void initGpsSetttings(Context c) {
		LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		// 判断GPS是否正常启动
		if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Toast.makeText(c, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
			// 返回开启GPS导航设置界面
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			((Activity) c).startActivityForResult(intent, 0);
			return;
		}
	}


	/**
	 * 获取字符串的前三位
	 *
	 * @param str
	 * @param i   前几位
	 * @return
	 */
	public static String subBefore3Num(String str, int i) {
		return str.substring(0, i);
	}

	/**
	 * 获取字符串的后四位数
	 *
	 * @param str 字符串
	 * @param i   后几位
	 * @return
	 */
	public static String subAfter4Num(String str, int i) {
		return str.substring(str.length() - i, str.length());
	}

	/**
	 * 复制
	 * @param context
	 * @param text
	 */
	public static void CopyToClipboard(Context context,String text){
		ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
		//clip.getText(); // 粘贴
		clip.setText(text); // 复制
	}
}
