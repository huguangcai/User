package com.ysxsoft.common_base.utils;

import android.text.TextUtils;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by Sincerly on 2018/5/24 0024.
 */

public class StringUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (str != null && !"".equals(str)) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为null
     *
     * @param str
     * @return
     */
    public static String convertString(String str) {
        if (str == null) {
            return "";
        }
        return str;
    }

    /**
     * 更改编码
     *
     * @param utf8Str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String updateCode(String utf8Str) throws UnsupportedEncodingException {
        if (utf8Str == null || "".equals(utf8Str)) {
            return "";
        }
        String result = new String(utf8Str.getBytes(), "utf-8");
        return result;
    }

    /**
     * 8-16位字符
     *
     * @param str
     * @return
     */
    public static boolean isPasswordType(String str) {
        if (str == null || "".equals(str)) {
            return false;
        }
        if (str.length() >= 8 && str.length() <= 16) {
            Pattern p2 = Pattern.compile("^([\\d]){8,16}$");
            Matcher matcher = p2.matcher(str);
            if (matcher.matches()) {//匹配规则  纯数字
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测手机号是否合格
     * @param phone
     * @return
     */
    public static boolean checkPhone(String phone) {
        boolean result = false;
        if(phone!=null&&!"".equals(phone)){
            if(phone.length()==11){
                result=true;
            }
        }
        return result;
    }

    /**
     * 检测手机号是否合格
     * @param phone
     * @return
     */
    public static boolean checkPhone(EditText phone) {
        boolean result = false;
        String str=phone.getText().toString().trim();
        if(!"".equals(str)){
            if(str.length()==11){
                result=true;
            }
        }
        return result;
    }

    public static String convertMoney(double amount) {
        //壹贰叁肆伍陆柒捌玖拾佰仟萬亿  圆
        final String[] CN_NUMBERS = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
        final String[] CN_MONETETARY_UNIT = {"分", "角", "元", "拾", "佰", "仟",
                "萬", "拾", "佰", "仟", "亿", "拾",
                "佰", "仟", "兆", "拾", "佰", "仟"};
        final String CN_FULL = "整";
        final String CN_NEGATIVE = "负";
        final String CN_ZERO = "零角零分";
        final long MAX_NUMBER = 10000000000000000l;//最大16位整数
        final String INVALID_AMOUNT = "金额超出最大金额千兆亿(16位整数)";

        if (Math.abs(amount) >= MAX_NUMBER) return INVALID_AMOUNT;
        StringBuilder result = new StringBuilder();
        long value = Math.abs(Math.round(amount * 100));
        int i = 0;
        while (true) {
            int temp = (int) (value % 10);
            result.insert(0, CN_MONETETARY_UNIT[i]);
            result.insert(0, CN_NUMBERS[temp]);
            value = value / 10;
            if (value < 1)
                break;
            i++;
        }
        //"零角零分" 转换成 "整"
        int index = result.indexOf(CN_ZERO);
        if (index > -1) {
            result.replace(index, index + 4, CN_FULL);
        }
        //负数
        if (amount < 0) {
            result.insert(0, CN_NEGATIVE);
        }
        return result.toString();
    }

    public static String convertNumber(int amount) {
        //壹贰叁肆伍陆柒捌玖拾佰仟萬亿  圆
        final String[] CN_NUMBERS = {"", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        final String[] CN_MONETETARY_UNIT = {"拾", "佰", "仟",
                "萬", "拾", "佰", "仟", "亿", "拾",
                "佰", "仟", "兆", "拾", "佰", "仟"};
//		final String CN_FULL = "整";
//		final String CN_NEGATIVE = "负";
//		final String CN_ZERO = "零角零分";
        final long MAX_NUMBER = 10000000000000000l;//最大16位整数
        final String INVALID_AMOUNT = "金额超出最大金额千兆亿(16位整数)";

        if (Math.abs(amount) >= MAX_NUMBER) return INVALID_AMOUNT;
        StringBuilder result = new StringBuilder();
        long value = amount;
        int i = 0;
        while (true) {
            int temp = (int) (value % 9);
            result.insert(0, CN_MONETETARY_UNIT[i]);
            result.insert(0, CN_NUMBERS[temp]);
            value = value / 9;
            if (value < 1)
                break;
            i++;
        }
        return result.toString();
    }

    /**
     * json 格式化
     *
     * @param json
     * @return
     */
    public static String jsonFormat(String json) {
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content";
        }
        String message;
        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                message = jsonObject.toString(4);
            } else if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                message = jsonArray.toString(4);
            } else {
                message = json;
            }
        } catch (JSONException e) {
            message = json;
        }
        return message;
    }

    /**
     * xml 格式化
     *
     * @param xml
     * @return
     */
    public static String xmlFormat(String xml) {
        if (TextUtils.isEmpty(xml)) {
            return "Empty/Null xml content";
        }
        String message;
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(xmlInput, xmlOutput);
            message = xmlOutput.getWriter().toString().replaceFirst(">", ">\n");
        } catch (TransformerException e) {
            message = xml;
        }
        return message;
    }

    public static boolean isEmpty(EditText editText){
        if("".equals(editText.getText().toString().trim())){
            return true;
        }
        return false;
    }

    /**
     * 内容一样 返回false   不一样返回true
     * @param editText
     * @param editText2
     * @return
     */
    public static boolean isLiked(EditText editText,EditText editText2){
        if("".equals(editText.getText().toString().trim())){
            return true;
        }
        if("".equals(editText2.getText().toString().trim())){
            return true;
        }
        if(editText.getText().toString().trim().equals(editText2.getText().toString().trim())){
            return false;
        }
        return true;
    }

    /**
     * 时间格式化
     */
    public static String formattedTime(long second) {
        String hs, ms, ss, formatTime;
        long h, m, s;
        h = second / 3600;
        m = (second % 3600) / 60;
        s = (second % 3600) % 60;
        if (h < 10) {
            hs = "0" + h;
        } else {
            hs = "" + h;
        }
        if (m < 10) {
            ms = "0" + m;
        } else {
            ms = "" + m;
        }
        if (s < 10) {
            ss = "0" + s;
        } else {
            ss = "" + s;
        }
        if (h > 0) {
            formatTime =  hs + ":" + ms + ":" + ss;
        } else {
            formatTime =  ms + ":" + ss;
        }
        return formatTime;
    }

    public static String duration(long durationMs) {
        long duration = durationMs / 1000;
        long h = duration / 3600;
        long m = (duration - h * 3600) / 60;
        long s = duration - (h * 3600 + m * 60);
        String durationValue;
        if (h == 0) {
            durationValue = asTwoDigit(m) + ":" + asTwoDigit(s);
        } else {
            durationValue = asTwoDigit(h) + ":" + asTwoDigit(m) + ":" + asTwoDigit(s);
        }
        return durationValue;
    }

    public static String asTwoDigit(long digit) {
        String value = "";

        if (digit < 10) {
            value = "0";
        }

        value += String.valueOf(digit);
        return value;
    }


}
