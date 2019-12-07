package com.ysxsoft.common_base.view.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * 通讯录控件
 * Created by Administrator on 2018/2/28 0028.
 */

public class RightBar extends View {
    private Paint paint;
    private OnSideBarListener listener;
    private Context context;
    private String[] labels = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z","#"};

    public RightBar(Context context) {
        this(context, null);
    }

    public RightBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RightBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        paint = new Paint();
        paint.setTextSize(DisplayUtils.sp2px(context, 12));
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setDither(true);
        TypedArray array=context.obtainStyledAttributes(attrs,R.styleable.RightBar);
        paint.setColor(array.getColor(R.styleable.RightBar_barTextColor,Color.parseColor("#666666")));
        array.recycle();
    }

    int size = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
//		canvas.translate(0, 5f);
        List<Point> points = new ArrayList<>();
        float y = 0;
        //计算出超过两个字符的位置
        float spacingHeight=0;
        for (int i = 0; i < labels.length; i++) {
            Point point = new Point();
            if (labels[i].length() > 2) {
                String[] strs = split(labels[i]);
                paint.setTextSize(DisplayUtils.sp2px(context, 12));
                List<Point> points1=new ArrayList<>();
                for (int j = 0; j < strs.length; j++) {
                    Point p2 = new Point();
                    String str = strs[j];
                    Rect rect = new Rect();
                    paint.getTextBounds(str, 0, str.length(), rect);
                    float x = width / 2 - paint.measureText(str) / 2;
                    p2.setX(x);
                    p2.setStr(str);
                    float h = rect.bottom- rect.top;
                    p2.setH(h);
                    p2.setY(y+(h));
                    //item高度
                    spacingHeight+=h;
                    points1.add(p2);
                    y+=h;
                }
                point.setPoints(points1);
                points.add(point);
                size++;
            } else {
                Rect rect = new Rect();
                paint.getTextBounds(labels[i], 0, labels[i].length(), rect);
                //字符高度
                float h = rect.bottom- rect.top;
                spacingHeight+=h;//item高度
                point.setX(getWidth()/2-(paint.measureText(labels[i])/2));
                point.setY(y+(h));
                point.setH(h);
                point.setStr(labels[i]);
                points.add(point);
                y += h;
            }
        }
        spacingHeight=getHeight()-spacingHeight;
        //单元格之间的均分高度
        float remainingSpace=spacingHeight/labels.length;
        for (int i = 0; i <points.size(); i++) {
            Point p=points.get(i);
            List<Point> pointList=p.getPoints();
            if(!pointList.isEmpty()){
                //超过2个字符
                for (int j = 0; j <pointList.size(); j++) {
                    Point p2=pointList.get(j);
                    canvas.drawText(p2.getStr(),p2.getX(),remainingSpace*i+p2.getY(),paint);
                }
            }else{
                canvas.drawText(p.getStr(),p.getX(),remainingSpace*i+p.getY(),paint);
            }
        }
    }

    private class Point {
        private float x;
        private float y;
        private String str;
        private float h;

        private List<Point> points;

        public List<Point> getPoints() {
            if (points == null) {
                return new ArrayList<>();
            }
            return points;
        }

        public void setPoints(List<Point> points) {
            this.points = points;
        }

        public float getH() {
            return h;
        }

        public void setH(float h) {
            this.h = h;
        }

        public float getX() {
            return x;
        }

        public void setX(float x) {
            this.x = x;
        }

        public float getY() {
            return y;
        }

        public void setY(float y) {
            this.y = y;
        }

        public String getStr() {
            return str == null ? "" : str;
        }

        public void setStr(String str) {
            this.str = str;
        }
    }

    /**
     * 分割数据
     *
     * @return
     */
    public String[] split(String str) {
        List<String> list = new ArrayList<>();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i = i + 2) {
            if (i == c.length - 1) {
                list.add(c[i] + "");
            } else {
                list.add(c[i] + "" + c[i + 1]);
            }
        }
        return list.toArray(new String[]{});
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        float height = getHeight();
        int position = (int) ((y / height) * labels.length);
        Log.e("tag", "移动:" + position);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (listener != null) {
                    listener.onDown(position, labels[position]);
                }
                setBackgroundColor(getResources().getColor(R.color.color_spacing));
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (listener != null) {
                    if (labels.length > position && position >= 0) {
                        listener.onMove(x, y, position, labels[position]);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if (listener != null) {
                    listener.onUp();
                }
                setBackgroundColor(getResources().getColor(android.R.color.transparent));//松开设置背景色
                break;
            case MotionEvent.ACTION_CANCEL:
                setBackgroundColor(getResources().getColor(android.R.color.transparent));//松开设置背景色
                invalidate();
                break;
        }
        return true;
    }

    public void setListener(OnSideBarListener listener) {
        this.listener = listener;
    }

    public interface OnSideBarListener {
        void onMove(float x, float y, int position, String content);
        void onUp();
        void onDown(int position, String content);
    }
}
