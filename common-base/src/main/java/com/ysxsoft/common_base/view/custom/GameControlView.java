package com.ysxsoft.common_base.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.ysxsoft.common_base.R;

/**
 * create by Sincerly on 2018/12/29 0029
 **/
public class GameControlView extends View {
    private Context context;
    private Paint paint;
    private Paint mLinePaint;
    private int bgColor;
    private int lineColor;
    private float mRadius;
    private Path path = new Path();

    public GameControlView(Context context) {
        this(context, null);
    }

    public GameControlView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameControlView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        switch (wmode) {
            case MeasureSpec.EXACTLY:
                int size = MeasureSpec.getSize(wmode);
                mRadius = size / 2;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                mRadius = 100f;
                break;
        }
        int width = (int) (mRadius * 2);
        setMeasuredDimension(width, width);
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        bgColor = Color.parseColor("#008577");
        lineColor = Color.parseColor("#00ffff");

        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bgColor);

        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mLinePaint.setStrokeWidth(12f);
        mLinePaint.setColor(lineColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //1.移动到中心点
        canvas.translate(mRadius, mRadius);
//        drawBg(canvas);
        test(canvas);
    }

    private void drawBg(Canvas canvas) {
        canvas.save();
        canvas.drawCircle(0, 0, mRadius, mLinePaint);
        float r2=mRadius-mRadius*0.1f;
        for (int i = 0; i <12; i++) {
            canvas.save();
            canvas.rotate(360 / 12f * i);
            canvas.drawLine(0,-mRadius,0,-r2,mLinePaint);
            canvas.restore();
        }
        canvas.restore();
    }

    private void test(Canvas canvas){
        canvas.save();
        canvas.drawCircle(0, 0, mRadius, mLinePaint);
        float r2 = mRadius - 1f * mRadius;//下圆半径
        canvas.drawCircle(0, 0, r2, mLinePaint);
        for (int i = 0; i < 22; i++) {//循环画出小黑条
            canvas.save();
            canvas.rotate(360 / 22f * i);
            canvas.drawLine(0, -mRadius, 0, -r2, mLinePaint);
            canvas.restore();
        }
        canvas.restore();
    }
}
