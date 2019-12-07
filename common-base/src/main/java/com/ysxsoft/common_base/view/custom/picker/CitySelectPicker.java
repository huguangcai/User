package com.ysxsoft.common_base.view.custom.picker;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.google.gson.Gson;
import com.ysxsoft.common_base.R;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class CitySelectPicker {
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private ArrayList<JsonBean> jsonBean;
    private OnCityPickerClickListener listener;
    private Context context;
    OptionsPickerView pvOptions;

    public void setListener(OnCityPickerClickListener listener) {
        this.listener = listener;
    }

    public void initData(Context context) {   //解析数据
        this.context = context;
        //  获取json数据
        String JsonData = JsonFileReader.getJson(context, "province_data.json");
        jsonBean = parseData(JsonData);//用Gson 转成实体
        options2Items.clear();
        options3Items.clear();
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）
            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> getProvinces() {
        return jsonBean;
    }

    public ArrayList<ArrayList<String>> getCitys() {
        return options2Items;
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
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

    public void show() {
        show("");
    }

    public void show(String text) {
        pvOptions = new OptionsPickerView.Builder(context, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                //获取省份数据
                String province = jsonBean.get(options1).getPickerViewText();
                //获取城市数据
                String city = options2Items.get(options1).get(options2);
                String district = options3Items.get(options1).get(options2).get(options3);
                pvOptions.dismiss();
                if (listener != null) {
                    listener.onSelect(province, city, district);
                }
            }
        }).setLayoutRes(R.layout.picker_select_city, new CustomListener() {
            @Override
            public void customLayout(View v) {
                TextView sure = v.findViewById(R.id.sure);
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvOptions.returnData();
                    }
                });
                TextView cancel = v.findViewById(R.id.cancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvOptions.dismiss();
                    }
                });
            }
        }).setCyclic(false, false, false)//去除无限循环
                .setTitleText("")
                .setLineSpacingMultiplier(2)
                .isCenterLabel(false)
                .setDividerColor(Color.parseColor("#ebebeb"))
                .setTextColorCenter(Color.parseColor("#333333"))
                .setContentTextSize(15)
                .setTitleSize(12)
                .setOutSideCancelable(true)
                .build();
//		pvOptions.setPicker(jsonBean, options2Items);//二级选择器
        pvOptions.setPicker(jsonBean, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public void change(OptionsPickerView view) {
        view.returnData();
    }

    public interface OnCityPickerClickListener {
        void onSelect(String province, String city, String district);
    }

    public static class JsonFileReader {
        public JsonFileReader() {
        }

        public static String getJson(Context context, String fileName) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            try {
                AssetManager assetManager = context.getAssets();
                InputStream inputStream = assetManager.open(fileName);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                byte[] buffer = new byte[1024];

                int len;
                while ((len = bufferedInputStream.read(buffer)) != -1) {
                    baos.write(buffer, 0, len);
                }
            } catch (IOException var8) {
                var8.printStackTrace();
            }

            return baos.toString();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 用法
    ///////////////////////////////////////////////////////////////////////////
//	CityPicker cityPicker=new CityPicker();
//	cityPicker.initData(this);//初始化数据
//	cityPicker.setListener(new CityPicker.OnCityPickerClickListener() {
//		@Override
//		public void onSelect(String province, String city, String district) {
//			dizhi.setText(province+city+district);
//		}
//	});
//	cityPicker.show();
}
