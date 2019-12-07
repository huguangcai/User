package com.ysxsoft.common_base.view.custom;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.utils.DisplayUtils;
import com.ysxsoft.common_base.utils.LogUtils;

import java.util.logging.Handler;

/**
 * create by Sincerly on 2019/1/8 0008
 * 纸牌view
 **/
public class ProkView extends View {
    private Context mContext;
    private int brandCount = 48;
    private int spacing = 11;
    private Paint paint;
    private int width;//组件宽度
    private boolean isPreseed;
    private int selectedLeft;//选中纸牌的左距
    private boolean init = false;
    private Bitmap bitmap;
    private Rect rect;
    private int itemWidth;
    private int itemHeight;
    private Mode mode = Mode.NORMAL;
    private int maxCount;//纸牌最大数量
    private boolean isCanClick = false;//洗牌中不能点击
    private boolean isClosed = false;//洗牌是否完成
    private int count = 15;//16一叠  下标是15

    public ProkView(Context context) {
        this(context, null);
    }

    public ProkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProkView);
        brandCount = typedArray.getInteger(R.styleable.ProkView_count, 1);
        int m = typedArray.getInteger(R.styleable.ProkView_mode, 0);
        if (m == 0) {
            mode = Mode.NORMAL;
        } else if (m == 1) {
            mode = Mode.CUT;
        } else {
            mode = Mode.SHUFFLEED;
        }
        typedArray.recycle();

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!init) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_brand);
            bitmap.setDensity(DisplayUtils.getDensityDpi(mContext));
//            BitmapDrawable drawable = (BitmapDrawable) getResources().getDrawable(R.mipmap.icon_brand);
//            bitmap = drawable.getBitmap();
            rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            itemWidth = bitmap.getScaledWidth(canvas);//一个的宽度
            float ratio = bitmap.getScaledWidth(canvas) / itemWidth;//宽度缩减比
//            itemHeight = (int) (bitmap.getHeight() * ratio);//一个的宽度
            itemHeight = bitmap.getScaledHeight(canvas);//一个的高度
            spacing = (width - bitmap.getScaledWidth(canvas)) / brandCount;//空隙
            init = true;
        }
        switch (mode) {
            case SHUFFLEED:
                if (isClosed) {
                    //洗牌模式
                    drawAll(canvas);//绘制全部数量
                } else {
                    //洗牌模式
                    drawAll(canvas);
                }
                break;
            case CUT:
                if (cutEnd) {
                    //切牌模式
                    if (switchAnimStared) {
                        //切换动画
                        drawPosition(canvas);
                    } else {
                        drawAll(canvas);
                    }
                } else {
                    drawAllByThree(canvas);
                }
                break;
            case NORMAL:
                //松开重置
                drawAll(canvas);
                if (isPreseed) {
                    //按下
                    Rect toRect = new Rect(selectedLeft, 0, selectedLeft + itemWidth, itemHeight);
                    canvas.drawBitmap(bitmap, rect, toRect, paint);
                }
                break;
        }
    }

    /**
     * 画3叠
     *
     * @param canvas
     */
    private void drawAllByThree(Canvas canvas) {
//        for (int i = 0; i < count; i++) {
//            int left = i * spacing;
//            Rect toRect = new Rect(left, 0, left + itemWidth, itemHeight);
//            canvas.drawBitmap(bitmap, rect, toRect, paint);
//
//            left = (i + 15) * spacing;
//            Rect toRect2 = new Rect(left, 0, left + itemWidth, itemHeight);
//            canvas.drawBitmap(bitmap, rect, toRect2, paint);
//
//            left = (i + 29) * spacing;
//            Rect toRect3 = new Rect(left, 0, left + itemWidth, itemHeight);
//            canvas.drawBitmap(bitmap, rect, toRect3, paint);
//        }
//        0  23  47
        int num2 = 16 - count;
        for (int i = 0; i < num2; i++) {
            int left = i * spacing;
            Rect toRect = new Rect(left, 0, left + itemWidth, itemHeight);
            canvas.drawBitmap(bitmap, rect, toRect, paint);
        }
        int i1 = 0, i2 = 0;
        //中间从左向右收缩
        int num3 = 16 + count / 2;  //count 0-15
        for (int i = num3; i < 24; i++) {//16-31
            i1++;
            int left = i * spacing;
            Rect toRect = new Rect(left, 0, left + itemWidth, itemHeight);
            canvas.drawBitmap(bitmap, rect, toRect, paint);
        }
        //中间从右向左收缩
        int num4 = 31 - count / 2;//count 0-15   32-17
        for (int i = 24; i < num4; i++) {
            i2++;
            int left = i * spacing;
            Rect toRect = new Rect(left, 0, left + itemWidth, itemHeight);
            canvas.drawBitmap(bitmap, rect, toRect, paint);
        }
//        Log.e("tag", "i1:" + i1 + "i2:" + i2);
        int num = 32 + count;
        for (int i = num; i < 48; i++) {
            int left = i * spacing;
            Rect toRect = new Rect(left, 0, left + itemWidth, itemHeight);
            canvas.drawBitmap(bitmap, rect, toRect, paint);
        }
    }

    private void drawAll(Canvas canvas) {
        for (int i = 1; i < brandCount; i++) {
            int left = i * spacing;
            Rect toRect = new Rect(left, 0, i * spacing + itemWidth, itemHeight);
            canvas.drawBitmap(bitmap, rect, toRect, paint);
//            canvas.drawBitmap(bitmap,toRect.left,0,paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isPreseed = true;
                changePosition(x);
                break;
            case MotionEvent.ACTION_MOVE:
                changePosition(x);
                break;
            case MotionEvent.ACTION_UP:
                isPreseed = false;
                postInvalidate();
                break;
        }
        return true;
    }

    /**
     * 根据坐标判断点击的是哪个
     *
     * @param x
     * @return
     */
    private void changePosition(float x) {
        if (x > (brandCount * spacing)) {
            //点击了最后一个
            selectedLeft = (brandCount - 1) * spacing;
        } else {
            int totalSpacing = width - itemWidth;
            int position = (int) (x / totalSpacing * brandCount);
            selectedLeft = position * spacing;
        }
        postInvalidate();
    }

    /**
     * 洗牌模式
     */
    public void shuffle() {
        //洗牌
        mode = Mode.SHUFFLEED;
        close();
    }

    private void close() {
        //洗牌先关闭后打开
        if (!isCanClick) {
            isCanClick = true;
            isClosed = true;
            maxCount = brandCount;
            brandCount = 2;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(maxCount, 1);//纸牌数量
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int a = (int) animation.getAnimatedValue();
                    brandCount = a;
                    postInvalidate();
                }
            });
            valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    brandCount = maxCount;
                    isCanClick = false;
                    isClosed = false;//是否是关闭模式
                    expand();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            valueAnimator.setDuration(600);
            valueAnimator.start();
            isCanClick = true;
        }
    }

    /**
     * 展开
     */
    private void expand() {
        if (!isCanClick) {
            isCanClick = true;
            maxCount = brandCount;
            brandCount = 1;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(1, maxCount);//纸牌数量
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int a = (int) animation.getAnimatedValue();
                    brandCount = a;
                    postInvalidate();
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    brandCount = maxCount;
                    isCanClick = false;

                    if(listener!=null){
                        listener.finishShuffle();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            valueAnimator.setDuration(600);
            valueAnimator.start();
            isCanClick = true;
        }
    }

    /**
     * 切牌模式
     */
    public void cut() {
        //切牌
        mode = Mode.CUT;
        reverse = false;
        cutEnd = false;
        cutAnim();
    }

    private boolean reverse = false;
    private boolean cutEnd = false;
    private boolean switchAnimStared = false;

    private void cutAnim() {
            int min = reverse ? 15 : 0;
            int max = reverse ? 0 : 15;
            ValueAnimator valueAnimator = ValueAnimator.ofInt(min, max);//纸牌数量
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int a = (int) animation.getAnimatedValue();
                    count = a;
                    postInvalidate();
                }
            });
            valueAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (reverse) {
                        reverse = false;
                        cutEnd = true;
//                    postInvalidate(); 3叠复合动画
                        //交换位置
                        switchAnimStared = true;
                        type = 0;//先2和3换
                        tranlationX();
                    } else {
                        //切牌首先折叠
                        reverse = true;
                        cutAnim();//折叠动画
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            valueAnimator.setDuration(600);
            valueAnimator.start();
    }

    private void tranlationX() {
//        ValueAnimator animator = ValueAnimator.ofInt(0, 15);
//        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int p = (int) ((float)animation.getAnimatedValue()*15);
//                Log.e("tag","动画："+p+animation.getAnimatedFraction());
//
//            }
//        });
//        animator.setInterpolator(new LinearInterpolator());
//        animator.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//            }
//        });
//        animator.setRepeatCount(0);
//        animator.setDuration(600);
//        animator.start();
        cup=0;
        position=0;
        postInvalidate();
        handler.sendEmptyMessageDelayed(0x02,50);
    }

    private int cup=0;
    private android.os.Handler handler=new android.os.Handler(){
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what){
                case 0x02:
                    if(cup==15){
                        position=15;
                        postInvalidate();
                        if (type == 0) {
                            type = 1;//1和3换
                            Log.e("tag","动画开启第二波");
                            tranlationX();
                        } else if (type == 1) {
                            type = 2;//2和1换
                            Log.e("tag","动画开启第三波");
                            tranlationX();
                        } else if (type == 2) {
                            //切换完毕之后恢复
                            reverse = false;
                            cutEnd = true;
                            switchAnimStared = false;
                            if(listener!=null){
                                listener.finishCut();
                            }
                        }
                    }else{
                        cup++;
                        position=cup;
                        postInvalidate();
                        handler.sendEmptyMessageDelayed(0x02,50);
                    }

                    break;
            }
        }
    };

    private int type = 0;//0[2和3换]  1[1和3换]  2[2和1换]

    private int position;

    private void drawPosition(Canvas canvas) {
        //2和3换  1和3换  2和1换
        LogUtils.e("tag","position:"+position);
//        position值是0-15
        int left = 0;
        int left2 = 0;
        int left3 = 0;
        switch (type) {
            case 0://2和3换
                left = 0 * spacing;//1固定
//                Log.e("tag","position :"+position);
                left2 = (24 + position) * spacing+position/2*spacing;
                left3 = (47 - position) * spacing-position/2*spacing;
                break;
            case 1://1和3换
                left = (0 + 3 * position) * spacing;
                left2 = 23 * spacing;
                left3 = (48 - 3* position) * spacing;
                break;
            case 2://2和1换
                left = (position) * spacing+position/2*spacing;
                left2 = (24 - position) * spacing-position/2*spacing;
                left3 = 47 * spacing;
                break;
        }
        paint.reset();
        paint.setColor(Color.GRAY);
        Rect toRect = new Rect(left, 0, left + itemWidth, itemHeight);
        canvas.drawBitmap(bitmap, rect, toRect, paint);
//        canvas.drawRect(toRect,paint);
        paint.reset();
        paint.setColor(Color.BLUE);
        Rect toRect2 = new Rect(left2, 0, left2 + itemWidth, itemHeight);
        canvas.drawBitmap(bitmap, rect, toRect2, paint);
//        canvas.drawRect(toRect2,paint);
        paint.reset();
        paint.setColor(Color.RED);
        Rect toRect3 = new Rect(left3, 0, left3 + itemWidth, itemHeight);
        canvas.drawBitmap(bitmap, rect, toRect3, paint);
//        canvas.drawRect(toRect3,paint);
    }

    @Override
    public boolean isHardwareAccelerated() {
        return true;
    }

    public int getBitmapHeight() {
        return itemHeight;
    }

    public int getBitmapWidth() {
        return itemWidth;
    }

    public int getLeft(int p) {
        return p*spacing;
    }

    public int getTop(int p) {
        return 0;
    }

    enum Mode {
        SHUFFLEED, CUT, NORMAL
    }

    public OnProkViewStatusChanageListener listener;

    public void setListener(OnProkViewStatusChanageListener listener) {
        this.listener = listener;
    }

    public interface OnProkViewStatusChanageListener{
        void finishShuffle();//洗牌完成
        void finishCut();//切牌动画完成
    }
}
