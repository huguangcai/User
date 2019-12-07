package com.ysxsoft.common_base.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ysxsoft.common_base.bean.im.FriendsBean;
import com.ysxsoft.common_base.view.custom.picker.CityPicker;
import com.ysxsoft.common_base.view.custom.picker.JsonBean;

import org.json.JSONArray;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * create by Sincerly on 2019/5/17 0017
 **/
public class CityUtils {
    public static List<String> init(Context context) {
        List<String> cityData = new ArrayList<>();
        String JsonData = CityPicker.JsonFileReader.getJson(context, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        //遍历省份
        for (int i = 0; i < jsonBean.size(); i++) {
            List<String> cityList = new ArrayList<>();
            //添加城市
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);
            }
            cityData.addAll(cityList);
        }
        return cityData;
    }

    /**
     * 根据城市 获取城市下所有区
     * @param context
     * @param cityName
     * @return
     */
    public static List<String> getDistrictByCity(Context context,String cityName){
        List<String> data=new ArrayList<>();
        String JsonData = CityPicker.JsonFileReader.getJson(context, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        //遍历省份
        for (int i = 0; i < jsonBean.size(); i++) {
            List<String> cityList = new ArrayList<>();
            //添加城市
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                if(!"".equals(cityName)&&cityName.equals(jsonBean.get(i).getCityList().get(c).getName())){
                    data.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
            }
        }
        return data;
    }

    /**
     * 获取省份列表
     * @param context
     * @return
     */
    public static ArrayList<JsonBean> getProvince(Context context) {
        String JsonData = CityPicker.JsonFileReader.getJson(context, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        Collections.sort(jsonBean, new Comparator<JsonBean>() {
            @Override
            public int compare(JsonBean o1, JsonBean o2) {
                String o = PinYinUtils.getFirst(o1.getName());
                String a = PinYinUtils.getFirst(o2.getName());
                return Collator.getInstance(Locale.CHINESE).compare(o, a);
            }
        });
        return jsonBean;
    }

    public static List<Citys> initCitys(Context context) {
        List<String> cityData = new ArrayList<>();
        String JsonData = CityPicker.JsonFileReader.getJson(context, "province_data.json");
        ArrayList<JsonBean> jsonBean = parseData(JsonData);
        //遍历省份
        for (int i = 0; i < jsonBean.size(); i++) {
            List<String> cityList = new ArrayList<>();
            //添加城市
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);
            }
            cityData.addAll(cityList);
        }

        List<Citys> citys=new ArrayList<>();
        for (int i = 0; i <cityData.size(); i++) {
            if("其他".equals(citys.get(i))){
                continue;
            }
            Citys c = new Citys();
            c.setLetter(PinYinUtils.getFirst(cityData.get(i)));
            c.setName(cityData.get(i));
            citys.add(c);
        }
        return citys;
    }

    public static ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 获取分组后的数据  A B分组
     * @param context
     * @return
     */
    public static Map<String, List<Citys>> getCitys(Context context) {
        return parseData(init(context));
    }

    /**
     * 解析数据
     */
    public static Map<String, List<Citys>> parseData(List<String> citys) {
        Map<String, List<Citys>> maps = new HashMap<>();//把所有数据分组
        List<Citys> citys1 = new ArrayList<>();
        for (int i = 0; i < citys.size(); i++) {
            if("其他".equals(citys.get(i))){
                continue;
            }
            Citys c = new Citys();
            c.setLetter(PinYinUtils.getFirst(citys.get(i)));
            c.setName(citys.get(i));
            citys1.add(c);
        }
        Collections.sort(citys1, new Comparator<Citys>() {
            @Override
            public int compare(Citys o1, Citys o2) {
                String o = o1.getLetter();
                String a = o2.getLetter();
                return Collator.getInstance(Locale.CHINESE).compare(o, a);
            }
        });
        for (int i = 0; i < citys1.size(); i++) {
            Citys f = citys1.get(i);
            String a = f.getLetter();//获取首字母
            if (maps.containsKey(a)) {
                List<Citys> list = maps.get(a);
                list.add(f);
            } else {
                List<Citys> list2 = new ArrayList<>();
                list2.add(f);
                maps.put(a, list2);
            }
        }
        return maps;
    }

    public static Map<String, Integer> getPosition(Map<String, List<Citys>> maps) {
        Map<String, Integer> positions = new HashMap<>();
        positions.clear();//清空首字母
        int lastListSize = 0;
        //把所有数据整合  得到首次出现位置 把#号数据放后边
        String[] labels = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        List<Citys> tempList = new ArrayList<>();
        for (int i = 0; i < labels.length; i++) {
            String key = labels[i];
            if (maps.containsKey(key)) {
                List<Citys> ll = maps.get(key);
                tempList.addAll(ll);
                if (positions.isEmpty()) {
                    positions.put(key, lastListSize);//从分组开始计算position
                } else {
                    positions.put(key, lastListSize);
                }
                lastListSize += ll.size();
            }
        }
        return positions;
    }

    public static class Citys {
        private String letter;
        private String name;

        public String getLetter() {
            return letter == null ? "" : letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
