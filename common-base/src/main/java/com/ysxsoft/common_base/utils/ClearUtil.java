package com.ysxsoft.common_base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/27 0027.
 */

public class ClearUtil {
	static String[] clearType = {".apk", ".log", ".tmp", ".temp", ".bak", ".amr", ".jpg", ".jpeg", ".png"};
	private Context cx;
	private ActivityManager am;
	private PackageManager pm;

	public ClearUtil(Context context) {
		super();
		cx = context;
		am = (ActivityManager) cx.getSystemService(Context.ACTIVITY_SERVICE);
		pm = cx.getPackageManager();
	}

	/**
	 * 获取单个文件大小
	 */
	public static float getFileSize(File file) {
		float size = 0;
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			size = inputStream.available();
		} catch (Exception e) {
			Log.d("--- DYP --- getFileSize", "catch");
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (java.io.IOException e) {
					e.printStackTrace();
				}
			}
		}
		return (float) size / 1024;
	}

	/**
	 * 删除文件，并返回文件大小
	 */
	public static String deleteFile(List<File> files) {
		float allFileSize = 0;
		float size = 0;
		for (File file : files) {
			size = getFileSize(file);
			if (file.delete()) {
				allFileSize += size;
			}
		}
		if (allFileSize < 1024) {
			return allFileSize + "KB";
		}
		return numToString((float) allFileSize / 1024);
	}

	public static List<File> getAllFiles(String sd, String[] clearType) {
		List<File> fileList = new ArrayList<File>();
		try {//遍历可能遇到.开头的文件
			File file = new File(sd);
			if (file.exists()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					if (files[i].isDirectory()) {
						fileList.addAll(getAllFiles(files[i].getAbsolutePath(), clearType));// 递归查找
					} else {
						for (int j = 0; j < clearType.length; j++) {
							if (files[i].getAbsolutePath().contains((clearType[j]))) {// 以.apk这些结尾
								fileList.add(files[i]);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileList;
	}

	public static String numToString(float f) {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String s = decimalFormat.format(f);
		return s;
	}

	public static String startDeleteFile(String path) {
		return deleteFile(getAllFiles(path, clearType));
	}

	/**
	 * 得到文件夹的大小
	 *
	 * @param file
	 * @return
	 */
	public static double getDirSize(File file) {
		// 判断文件是否存在
		if (file.exists()) {
			// 如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {// 如果是文件则直接返回其大小,以“兆”为单位
				double size = file.length();
				return size;
			}
		} else {
			return 0.0;
		}
	}

	/**
	 * 获取进程白名单
	 */
	private List<String> getFilterPackgeName() {
		List<String> filterPackgeNames = new ArrayList<String>(); // 过滤一些进程
		ActivityInfo launcherInfo = new Intent(Intent.ACTION_MAIN).addCategory(
				Intent.CATEGORY_HOME).resolveActivityInfo(pm, 0);
		filterPackgeNames.add(launcherInfo.packageName); // Launcher
		filterPackgeNames.add("com.hitv.locker"); // 定时锁屏管理
		filterPackgeNames.add(cx.getPackageName()); // 自己
		filterPackgeNames.add(getCurTopPackgeName()); // 过滤正在运行的进程
		return filterPackgeNames;
	}

	/**
	 * 获取最顶层的app包名,若是自己，则指定为其上一个
	 */
	public String getCurTopPackgeName() {
		String curAppTaskPackgeName = null;
		String myPackageName = cx.getPackageName();
		List<ActivityManager.RunningTaskInfo> appTask = am.getRunningTasks(Integer.MAX_VALUE);
		if (appTask.size() > 0) {
			curAppTaskPackgeName = appTask.get(0).topActivity.getPackageName();
			if (appTask.size() > 1) {
				if (curAppTaskPackgeName.equals(myPackageName)
						&& appTask.get(1) != null) {
					curAppTaskPackgeName = appTask.get(1).topActivity
							.getPackageName();
				}
			}
		}
		return curAppTaskPackgeName;
	}

	/**
	 * 系统内存:当前使用百分比/字符串格式
	 */
	public String usePercentNumString() {
		float surplus_size = getSurplusMemory();// 剩余MB
		float all_size = AndroidUtils.getTotalMemory();// 总共MB
		// 小数不足2位,以0补足
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		String percentnum = decimalFormat
				.format((1 - surplus_size / all_size) * 100);
		return percentnum;
	}

	/**
	 * 系统剩余的内存
	 */
	public float getSurplusMemory() {
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(info);
		return (float) info.availMem / 1024 / 1024;
	}

	/**
	 * 系统内存:当前使用百分比/数字格式
	 */
	public float usePercentNum() {// 用于清理后UI更新
		float surplus_size = getSurplusMemory();// 剩余MB
		float all_size = AndroidUtils.getTotalMemory();// 总共MB
		return (1 - surplus_size / all_size) * 100;
	}

	/**
	 * 使用反射方法调用系统隐藏api：forceStopPackage 通过包名杀死进程
	 */
	public boolean forceStopPackageByPackageName(String packageName) {
		boolean forceSucceed = false;
		Class<ActivityManager> clazz = ActivityManager.class;
		Method method;
		try {
			method = clazz.getDeclaredMethod("forceStopPackage", String.class);
			method.invoke(am, packageName);
			forceSucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return forceSucceed;
	}

	/**
	 * 获取所有已经安装的应用程序 ,包括那些卸载了的，但没有清除数据的应用程序
	 */
	public List<PackageInfo> getPackageInfos() {
		List<PackageInfo> packageInfos = pm.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
		return packageInfos;
	}
}