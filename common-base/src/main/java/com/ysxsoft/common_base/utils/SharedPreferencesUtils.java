package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/5/24 0024.
 * {@link #save(Context context,String key,T t)} 											保存值
 * {@link #get(Context context)} 															获取一个SharedPreferences对象
 * {@link #saveUid(Context context,String uid)} 											保存用户uid
 * {@link #getUid(Context context)} 														获取用户uid
 * {@link #saveUsername(Context context, String username)} 									保存用户名
 * {@link #getUsername(Context context)} 													获取用户名
 * {@link #saveSex(Context context, String sex)} 											保存性别
 * {@link #getSex(Context context)} 														获取性别
 * {@link #saveAvatar(Context context, String avatar)} 										保存头像
 * {@link #getAvatar(Context context)} 														获取头像
 * {@link #saveUserpwd(Context context, String userpwd)} 									保存密码
 * {@link #getUserpwd(Context context)} 													获取密码
 * {@link #saveFirst(Context context, boolean isFirst)} 									保存是否第一次
 * {@link #isFirst(Context context)} 														判断是否登陆过
 */

public class SharedPreferencesUtils {

	public static <T> void save(Context context, String key, T t) {
		SharedPreferences.Editor editor = get(context).edit();//获取编辑器
		if (t instanceof String) {
			editor.putString(key, (String) t);
		} else if (t instanceof Float) {
			editor.putFloat(key, (Float) t);
		} else if (t instanceof Boolean) {
			editor.putBoolean(key, (Boolean) t);
		} else if (t instanceof Long) {
			editor.putLong(key, (Long) t);
		} else if (t instanceof Integer) {
			editor.putInt(key, (Integer) t);
		}
		editor.commit();//提交修改
	}

	public static SharedPreferences get(Context context) {
//		File file= new File("/data/data/"+getPackageName().toString()+"/shared_prefs","Activity.xml");
		SharedPreferences share = context.getSharedPreferences("ysxsoft_abase", Context.MODE_PRIVATE);
		return share;
	}

	/**
	 * 登录的uid
	 *
	 * @param context
	 * @param value
	 */
	public static void saveUid(Context context, String value) {
		save(context, "uid", value);
	}

	public static String getUid(Context context) {
		return get(context).getString("uid", "");
	}

	/**
	 * 邀请码
	 *
	 * @param context
	 * @param value
	 */
	public static void saveInviteCode(Context context, String value) {
		save(context, "inviteCode", value);
	}

	public static String getInviteCode(Context context) {
		return get(context).getString("inviteCode", "");
	}

	/**
	 * 登录的nickname
	 *
	 * @param context
	 * @param value
	 */
	public static void saveNickname(Context context, String value) {
		save(context, "nickname", value);
	}

	public static String getNickname(Context context) {
		return get(context).getString("nickname", "");
	}

	/**
	 * 登录的手机号
	 *
	 * @param context
	 * @param value
	 */
	public static void savePhone(Context context, String value) {
		save(context, "phone", value);
	}

	public static String getPhone(Context context) {
		return get(context).getString("phone", "");
	}

	/**
	 * 登录的性别
	 *
	 * @param context
	 * @param value
	 */
	public static void saveSex(Context context, String value) {
		save(context, "sex", value);
	}

	public static String getSex(Context context) {
		return get(context).getString("sex", "");
	}

	/**
	 * 登录的头像
	 *
	 * @param context
	 * @param value
	 */
	public static void saveAvatar(Context context, String value) {
		save(context, "icon", value);
	}

	public static String getAvatar(Context context) {
		return get(context).getString("icon", "");
	}

	/**
	 * 登录的密码
	 *
	 * @param context
	 * @param value
	 */
	public static void saveUserpwd(Context context, String value) {
		save(context, "upd", value);
	}

	public static String getUserpwd(Context context) {
		return get(context).getString("upd", "");
	}

	/**
	 * 设置是否第一次登陆
	 * @param context
	 * @param value
	 */
	public static void saveFirst(Context context, boolean value) {
		save(context, "first", value);
	}

	public static boolean isFirst(Context context) {
		return get(context).getBoolean("first", false);
	}

	/**
	 * 设置是否弹出优惠券
	 * @param context
	 * @param value
	 */
	public static void saveHasQuan(Context context, boolean value) {
		save(context, "isquan", value);
	}

	public static boolean isHasQuan(Context context) {
		return get(context).getBoolean("isquan", false);
	}

	/**
	 * 设置是否有密码
	 * @param context
	 * @param value
	 */
	public static void saveHasPwd(Context context, boolean value) {
		save(context, "ispwd", value);
	}

	public static boolean isHasPwd(Context context) {
		return get(context).getBoolean("ispwd", false);
	}

	/**
	 * 设置是否弹出拼车免责声明
	 * @param context
	 * @param value
	 */
	public static void savePin(Context context, boolean value) {
		save(context, "pin", value);
	}

	public static boolean getPin(Context context) {
		return get(context).getBoolean("pin", false);
	}

	/**
	 * 登录选择的省
	 *
	 * @param context
	 * @param value
	 */
	public static void saveSelectProvince(Context context, String value) {
		save(context, "selectProvince", value);
	}

	public static String getSelectProvince(Context context) {
		return get(context).getString("selectProvince", "");
	}

	/**
	 * 登录选择的城市
	 *
	 * @param context
	 * @param value
	 */
	public static void saveSelectCity(Context context, String value) {
		save(context, "selectCity", value);
	}

	public static String getSelectCity(Context context) {
		return get(context).getString("selectCity", "");
	}

	/**
	 * 选择的城市
	 *
	 * @param context
	 * @param value
	 */
	public static void saveSelectCity2(Context context, String value) {
		save(context, "selectCity2", value);
	}

	public static String getSelectCity2(Context context) {
		return get(context).getString("selectCity2", "");
	}

	/**
	 * 登录选择的县
	 *
	 * @param context
	 * @param value
	 */
	public static void saveSelectDistrict(Context context, String value) {
		save(context, "selectDistrict", value);
	}

	public static String getSelectDistrict(Context context) {
		return get(context).getString("selectDistrict", "");
	}

	/**
	 * 融云token
	 *
	 * @param context
	 * @param value
	 */
	public static void saveToken(Context context, String value) {
		save(context, "token", value);
	}

	public static String getToken(Context context) {
		return get(context).getString("token", "");
	}

	public static void saveBadge(Context context, int value) {
		int badge=getBadge(context);
		badge++;
		save(context,"badgeCount", badge);
	}

	public static void clearBadge(Context context) {
		save(context,"badgeCount", 0);
	}

	public static int getBadge(Context context) {
		return get(context).getInt("badgeCount", 0);
	}

	public static void saveSp(Context context, String key, String value) {
		SharedPreferences.Editor sp = context.getSharedPreferences("SAVE", Context.MODE_PRIVATE).edit();
		sp.putString(key, value);
		sp.apply();
	}

	public static String getSp(Context context, String key) {
		SharedPreferences sp = context.getSharedPreferences("SAVE", Context.MODE_PRIVATE);
		return sp.getString(key, "");
	}

	public static void deleteSp(Context context) {
		SharedPreferences.Editor deleteSp = context.getSharedPreferences("SAVE", Context.MODE_PRIVATE).edit();
		deleteSp.clear();
		deleteSp.apply();
	}
}
