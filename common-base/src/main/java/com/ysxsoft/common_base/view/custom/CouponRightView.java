package com.ysxsoft.common_base.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.ysxsoft.common_base.R;

/**
 * create by Sincerly on 2019/5/30 0030
 **/
public class CouponRightView extends View {
    private Context context;
    private Paint paint;
    public CouponRightView(Context context) {
        this(context,null);
    }

    public CouponRightView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CouponRightView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint(){
        paint=new Paint();
        paint.setColor(Color.WHITE);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

        int width=getWidth()/2;
        int height=getHeight()/2;
        paint.setColor(getResources().getColor(R.color.couponBlue));
        Path path=new Path();
        path.lineTo(0,0.5f);
        path.lineTo(width,0.5f);
        path.lineTo(getWidth(),getHeight()-height);
        path.lineTo(getWidth(),getHeight());
        path.close();
        canvas.drawPath(path,paint);

        paint.setColor(getResources().getColor(R.color.colorWhite));
        paint.setTextSize(22);


        canvas.drawTextOnPath("剩余3天",path,0,0,paint);
        canvas.restore();
    }
}
