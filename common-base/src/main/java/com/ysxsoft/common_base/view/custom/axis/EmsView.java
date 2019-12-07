package com.ysxsoft.common_base.view.custom.axis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;

/**
 * create by Sincerly on 2019/2/19 0019
 **/
public class EmsView extends LinearLayout {
    private Context context;
    private Paint paint;
    private Paint linePaint;

    private int bitmapWidth;//图标宽度
    private Bitmap normalBitmap;//默认图片
    private Bitmap selectedBitmap;//选中图片
    private float circleRadius;//节点半径
    private float lineWidth;//线宽
    private float paddingLeft;//距离边距
    private int color;//线颜色

    public EmsView(Context context) {
        this(context, null);
    }

    public EmsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);
        setOrientation(VERTICAL);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        initPaint();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmsView);
        int normalResourceId = typedArray.getResourceId(R.styleable.EmsView_normalResource, R.mipmap.icon_ems_normal);
        int selectedResourceId = typedArray.getResourceId(R.styleable.EmsView_selectedResource, R.mipmap.icon_ems_blue);
        color = typedArray.getColor(R.styleable.EmsView_lineColor, getResources().getColor(R.color.color_spacing));//线颜色
        lineWidth = typedArray.getDimension(R.styleable.EmsView_lineEWidth, 4f);//线宽
        circleRadius = typedArray.getDimension(R.styleable.EmsView_pointRadius,8f);//节点半径
        paddingLeft = typedArray.getDimension(R.styleable.EmsView_viewPaddingLeft,DisplayUtils.dip2px(context,24));//轴距离位置
        linePaint.setColor(color);
        linePaint.setStrokeWidth(lineWidth);
        normalBitmap = BitmapFactory.decodeResource(getResources(), normalResourceId);
        selectedBitmap = BitmapFactory.decodeResource(getResources(), selectedResourceId);
        bitmapWidth = normalBitmap.getWidth();//图片宽度
        typedArray.recycle();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        linePaint = new Paint();
        linePaint.setDither(true);
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            int height = child.getHeight();
            if (i == (childCount - 1)) {
                //最后一个
            } else {
                //画线
                drawLine(canvas, child.getTop(), height);
            }
            drawPoint(i, canvas, child.getTop());
        }
    }

    /**
     * 画点
     *
     * @param position
     * @param canvas
     * @param startY
     */
    private void drawPoint(int position, Canvas canvas, int startY) {
        if (position == 0) {
            canvas.drawBitmap(selectedBitmap,paddingLeft , startY, paint);
        } else if(position == getChildCount()-1){
            canvas.drawBitmap(normalBitmap,paddingLeft , startY, paint);
        }else{
            canvas.drawCircle(paddingLeft + bitmapWidth/2, startY+circleRadius, circleRadius, linePaint);
        }
    }

    /**
     * 画连接点
     *
     * @param canvas
     * @param startY
     * @param height
     */
    private void drawLine(Canvas canvas, int startY, int height) {
        canvas.drawLine(paddingLeft+bitmapWidth/2, startY, paddingLeft+bitmapWidth/2, startY + height, linePaint);
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    //     早上         |    运输中
    //     00:26        |    快件在[] 装车，已发往某某
    //
    ///////////////////////////////////////////////////////////////////////////
    public static class Node{
        private String title;
        private String subTitle;
        private boolean isPoint;//节点是否是圆
        private String rightTitle;
        private String rightContent;

        public Node(String title, String subTitle, boolean isPoint, String rightTitle, String rightContent) {
            this.title = title;
            this.subTitle = subTitle;
            this.isPoint = isPoint;
            this.rightTitle = rightTitle;
            this.rightContent = rightContent;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return subTitle == null ? "" : subTitle;
        }

        public void setSubTitle(String subTitle) {
            this.subTitle = subTitle;
        }

        public boolean isPoint() {
            return isPoint;
        }

        public void setPoint(boolean point) {
            isPoint = point;
        }

        public String getRightTitle() {
            return rightTitle == null ? "" : rightTitle;
        }

        public void setRightTitle(String rightTitle) {
            this.rightTitle = rightTitle;
        }

        public String getRightContent() {
            return rightContent == null ? "" : rightContent;
        }

        public void setRightContent(String rightContent) {
            this.rightContent = rightContent;
        }
    }
}
