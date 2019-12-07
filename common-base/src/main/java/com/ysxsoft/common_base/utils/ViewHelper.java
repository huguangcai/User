package com.ysxsoft.common_base.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.LayoutInflaterCompat;

import androidx.appcompat.widget.AppCompatEditText;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Sincerly on 2018/5/23 0023.
 * {@link #disableShift(BottomNavigationView bottomNavigationView)}    						禁用bottomNavigationView默认切换动画
 * {@link #controllView(View view)}    														控制View显示与隐藏
 * {@link #setText(TextView textView, String content)}    									TextView设置文本
 * {@link #setNumberPickerDividerColor(NumberPicker picker, Context context, int colorId)}   设置NumberPicker选中线颜色
 * {@link #checkEditTextEmpty(EditText editText)}   										检测editText是否为空
 * {@link #changeFontTypeFace(Activity activity, String path, AppCompatDelegate delegate, View parent)} 全局替换字体
 */

public class ViewHelper {
    /**
     * 禁用默认动画BottomNavigationView
     *
     * @param bottomNavigationView
     */
    @SuppressLint("RestrictedApi")
    public static void disableShift(BottomNavigationView bottomNavigationView) {
        if (bottomNavigationView == null) {
            return;
        }
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    /**
     * 控制view显示与隐藏
     *
     * @param view
     */
    public static void controllView(View view) {
        if (view.getVisibility() == View.GONE) {
            view.setVisibility(View.VISIBLE);
        } else if (view.getVisibility() == View.VISIBLE) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * 设置文本
     *
     * @param textView
     * @param content
     */
    public static void setText(TextView textView, String content) {
        if (textView == null || content == null) {
            return;
        }
        textView.setText(content);
    }

    /**
     * 设置numberPicker选择线颜色
     *
     * @param numberPicker
     */
    public static void setNumberPickerDividerColor(NumberPicker numberPicker, Context context, int colorResouceId) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    //设置分割线的颜色值
                    pf.set(picker, new ColorDrawable(context.getResources().getColor(colorResouceId)));
                    // pf.set(picker, new Div)
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public static void setViewHeightById(Activity activity, int viewId) {
        StatusBarUtils.setStatusBarTranslucent(activity);
        View view = activity.findViewById(viewId);
        if (view != null) {
            view.setLayoutParams(new LinearLayout.LayoutParams(DisplayUtils.getDisplayWidth(activity), DisplayUtils.getStatusBarHeight(activity)));
        }
    }

    /**
     * 检查EditText内容是否为空
     *
     * @param editText
     * @return
     */
    public static boolean checkEditTextEmpty(EditText editText) {
        if (editText == null) {
            return true;
        }
        if ("".equals(editText.getText().toString().trim())) {
            return true;
        }
        return false;
    }

    /**
     * 全局更换字体  在oncreate方法前调用
     *
     * @param activity
     * @param path
     * @param delegate
     * @param parent
     */
    public static void changeFontTypeFace(Activity activity, String path, final AppCompatDelegate delegate, final View parent) {
        File file = new File(path);
        final Typeface typeface = Typeface.createFromFile(file);
        LayoutInflaterCompat.setFactory2(LayoutInflater.from(activity), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                return null;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                AppCompatDelegate appCompatDelegate = delegate;
                View view = appCompatDelegate.createView(parent, name, context, attrs);
                if (view != null && (view instanceof TextView)) {
                    ((TextView) view).setTypeface(typeface);
                }
                return view;
            }
        });
    }

    public static void setAlpha(View view, float alpha) {
        view.setAlpha(alpha);
    }

    /**
     * 移动edittext光标至结尾
     *
     * @param editText
     */
    public static void moveCursorToLast(AppCompatEditText editText) {
        CharSequence text = editText.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());//移动光标至结尾
        }
    }

    /**
     * 切换明文密文
     */
    public static void changeInputType(AppCompatEditText editText){
        int type = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT;
        if (editText.getInputType() == type) {
            editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        } else {
            editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
        }
        moveCursorToLast(editText);
    }

    public static void checkEditTextNotNull(EditText editText,String message){
        if("".equals(editText.getText().toString())) {
            ToastUtils.shortToast(editText.getContext(), message);
            throw new IllegalArgumentException(message);
        }
    }
}
