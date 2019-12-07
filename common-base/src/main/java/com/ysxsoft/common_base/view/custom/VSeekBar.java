package com.ysxsoft.common_base.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import androidx.annotation.Nullable;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ysxsoft.common_base.R;

/**
 * create by Sincerly on 2019/1/10 0010
 * 处置滑块view
 **/
public class VSeekBar extends View {
    private Context mContext;
    private int bgColor, progressColor;
    private int maxProgress=100, currentProgress;
    private int w, h;
    private Paint paint, progressPaint;
    private RectF rectF = new RectF();
    private OnProgressChangeListener listener;

    public VSeekBar(Context context) {
        this(context, null);
    }

    public VSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wsize = MeasureSpec.getSize(widthMeasureSpec);
        int hsize = MeasureSpec.getSize(heightMeasureSpec);
        int wmode = MeasureSpec.getMode(widthMeasureSpec);
        int hmode = MeasureSpec.getMode(heightMeasureSpec);
        w = wsize;
        h = hsize;
        switch (wmode) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
            case MeasureSpec.UNSPECIFIED:
                break;
        }
    }

    /**
     * 初始化
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);
        progressPaint.setDither(true);
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(getResources().getColor(R.color.theme_color));
    }

    private float seekbarHalfWidth = 2f;
    private int bitmapHalfWidth = 0;

    private Rect picRect;
    private Rect picResourceRect;
    private Bitmap bitmap;
    private float itemHeight;
    private int offsetHeight = 12;

    private boolean isInit=false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_music_point);
            bitmapHalfWidth = bitmap.getWidth() / 2;
            picResourceRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            itemHeight = (h - bitmapHalfWidth * 2) / (float)maxProgress;//的
            picRect = new Rect(w / 2 - bitmapHalfWidth, 0, w / 2 + bitmapHalfWidth, bitmap.getHeight());//图片首次显示顶部
        }
        float left = w / 2 - seekbarHalfWidth;
        float right = w / 2 + seekbarHalfWidth;
        if(!isInit){
            //首次进入 初始化位置
            picRect.top = (int) (itemHeight * currentProgress);
            picRect.bottom = picRect.top + bitmapHalfWidth * 2;
            isInit=true;
        }

        RectF rectF = new RectF(left, bitmapHalfWidth, right, h - bitmapHalfWidth);
        canvas.drawRoundRect(rectF, 8, 8, paint);
        //进度
        RectF rectF2 = new RectF(left, picRect.top + bitmapHalfWidth, right, h - bitmapHalfWidth);
        canvas.drawRoundRect(rectF2, 8, 8, progressPaint);
        canvas.drawBitmap(bitmap, picResourceRect, picRect, progressPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public void setCurrentProgress(int progress){
        isInit=false;
        this.currentProgress=maxProgress-progress;
    }

    public void setProgress(int progress) {
        picRect.top = (int) (itemHeight * progress);
        picRect.bottom = picRect.top + bitmapHalfWidth * 2;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                if (y > 0 && y <=h) {
                    Log.e("tag","onMove");
                    int progress = (int) (y / h * maxProgress);
//                    int progress= (int) ((h-y)/itemHeight);
                    setProgress(progress);
                    if(listener!=null){
                        Log.e("tag","onChang progress："+progress);
                        if(progress==(maxProgress-1)){
                            progress=maxProgress;
                        }
                        listener.onChanged(maxProgress-progress);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
//                setProgress(100);
                break;
        }
        return true;
    }

    public void refresh(){
        postInvalidate();
    }

    /**
     * 设置最大进度
     * @param maxAudioVolume
     */
    public void setMax(int maxAudioVolume) {
        this.maxProgress=maxAudioVolume;
        this.itemHeight=h/maxAudioVolume;
        postInvalidate();
    }

    public interface OnProgressChangeListener{
        void onChanged(int progress);
    }
    public void setListener(OnProgressChangeListener listener) {
        this.listener = listener;
    }
}
