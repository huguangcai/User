package com.ysxsoft.common_base.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sincerly on 2018/10/27 0024.
 * {@link #parseByGson(String json,Class<T> t)}    													解析json串至指定的对象
 * {@link #toJsonByGson(T t)}    																	转换对象为json
 */
public class JsonUtils {

	/**
	 * Gson数据泛型解析 转换为对应的实体类
	 * @param json
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> T parseByGson(String json,Class<T> t) {
		T bean=null;
		try{
			bean = new Gson().fromJson(json, t);
		}catch (Exception e){
			e.printStackTrace();
		}
		return bean;
	}

	/**
	 * 把实体类转换为json
	 * @param t
	 * @param <T>
	 * @return
	 */
	public static <T> String toJsonByGson(T t) {
		String json = new Gson().toJson(t);
		return json;
	}

	public static void main(){
		A a=new A();
		//a.setA("a");
		a.setB("b");
		String json=toJsonByGson(a);
		A bean=parseByGson(json,A.class);
		Log.e("tag","json串："+json+" 转换后的实体类 "+bean.getA());
	}

	public static class A{
		private String a="";
		private String b="";

		public String getA() {
			return a == null ? "" : a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b == null ? "" : b;
		}

		public void setB(String b) {
			this.b = b;
		}
	}

	/**
	 * 解析json对象
	 *
	 * @param string 要解析的json
	 * @param clazz  解析类
	 */
	public static <T> T parseObject(String string, Class<T> clazz) {
		return new Gson().fromJson(string, clazz);
	}

	/**
	 * 解析json数组为ArrayList
	 *
	 * @param json  要解析的json
	 * @param clazz 解析类
	 * @return ArrayList
	 */
	public static <T> ArrayList<T> parseToArrayList(String json, Class<T> clazz) {
		Type type = new TypeToken<ArrayList<JsonObject>>() {
		}.getType();
		ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);
		ArrayList<T> arrayList = new ArrayList<>();
		for (JsonObject jsonObject : jsonObjects) {
			arrayList.add(new Gson().fromJson(jsonObject, clazz));
		}
		return arrayList;
	}

	/**
	 * 解析json数组为List
	 *
	 * @param json  要解析的json
	 * @param clazz 解析类
	 * @return List
	 */
	public static <T> List<T> parseToList(String json, Class<T[]> clazz) {
		Gson gson = new Gson();
		T[] array = gson.fromJson(json, clazz);
		return Arrays.asList(array);
	}

}
