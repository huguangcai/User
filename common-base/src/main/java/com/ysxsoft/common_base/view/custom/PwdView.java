package com.ysxsoft.common_base.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * create by Sincerly on 2019/6/1 0001
 **/
public class PwdView extends AppCompatEditText {
    private int maxPwd = 6;
    private int itemHeight = 0;
    private Paint paint;
    private Context context;
    private List<String> pwd = new ArrayList<>();
    private int solidColor, strokColor;
    public static final String LETTER="^\\d$";

    public PwdView(Context context) {
        this(context, null);
    }

    public PwdView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.PwdView);
        solidColor = typedArray.getColor(R.styleable.PwdView_solidColor, Color.parseColor("#DB2B1F"));
        strokColor = typedArray.getColor(R.styleable.PwdView_storkColor, Color.parseColor("#CCCCCC"));
        typedArray.recycle();

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(DisplayUtils.sp2px(context, 1));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(DisplayUtils.sp2px(context, 24));
        paint.setColor(strokColor);//边框颜色
        setFocusableInTouchMode(true);
        setCursorVisible(false);//去除游标
        setFilters(new InputFilter[]{new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (!"".equals(source) && !" ".equals(source)) {
                    Pattern pattern=Pattern.compile(LETTER);
                    if(pattern.matcher(source).matches()){
                        if (pwd.size() < 6) {
                            pwd.add(source.toString());
                        }
                        postInvalidate();
                        if(listener!=null&&pwd.size()==6){
                            StringBuffer stringBuffer=new StringBuffer();
                            for (int i = 0; i <pwd.size(); i++) {
                                stringBuffer.append(pwd.get(i));
                            }
                            listener.inputEnd(stringBuffer.toString());
                        }
                    }else{
                        //输入的字母  无效只接受数字
                    }
                }
                return "";
            }
        }});
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean result = false;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DEL:
            case KeyEvent.KEYCODE_FORWARD_DEL:
                if (pwd.size() > 0) {
                    pwd.remove(pwd.size() - 1);
                    refreshUi();
                }
                result = true;
            default:
                result = super.onKeyDown(keyCode, event);
                break;
        }
        return result;
    }

    private void refreshUi() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wmode = View.MeasureSpec.getMode(widthMeasureSpec);
        switch (wmode) {
            case View.MeasureSpec.EXACTLY:
                int size = View.MeasureSpec.getSize(widthMeasureSpec);
                itemHeight = size / maxPwd;
                break;
            case View.MeasureSpec.AT_MOST:
                //wrap
                itemHeight = DisplayUtils.dp2px(context, 44);
                break;
        }
        //根据宽固定高
        setMeasuredDimension(itemHeight * maxPwd, itemHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawbg(canvas);
        drawStar(canvas);
    }

    private void drawbg(Canvas canvas) {
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect, paint);

        for (int i = 1; i < maxPwd; i++) {
            canvas.drawLine(i * itemHeight, 0, i * itemHeight, getHeight(), paint);
        }
    }

    private void drawStar(Canvas canvas) {
        Rect rect = new Rect();
        paint.getTextBounds("*", 0, 1, rect);

        paint.setColor(solidColor);
        for (int i = 0; i < pwd.size(); i++) {
            canvas.drawText("*", i * itemHeight + itemHeight / 2 - rect.width() / 2, itemHeight / 2 + rect.height(), paint);
        }
        paint.setColor(strokColor);
    }

    public OnInputListener listener;
    public void setListener(OnInputListener listener) {
        this.listener = listener;
    }
    public interface OnInputListener {
        void inputEnd(String pwd);
    }
}
