package com.ysxsoft.common_base.view.custom.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;

import com.ysxsoft.common_base.R;

/**
 * create by Sincerly on 2019/3/20 0020
 * 从左到右闪光 类似跑马灯效果 imageview
 **/
public class LightTextView extends AppCompatTextView {
    private Context context;
    private Bitmap bitmap;
    private Paint paint;
    private Matrix matrix;
    private int width = 0;
    private int mTranslate = 0;
    private LinearGradient gradient;

    public LightTextView(Context context) {
        this(context,null);
    }

    public LightTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_jian_tou);
        }
        if (matrix != null) {
            mTranslate += width / 10;
            if (mTranslate > 2 * width) {
                mTranslate = -width;
            }
            matrix.setTranslate(mTranslate, 0);
            gradient.setLocalMatrix(matrix);

//            canvas.drawBitmap(bitmap,0,0,paint);
            postInvalidateDelayed(50);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("tag","onLayout");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (width == 0) {
            width = getMeasuredWidth();
            if (width > 0) {
                paint = getPaint();
//                gradient = new LinearGradient(-width, 0, 0, 0, new int[]{0x33000000, 0xffffffff, 0x33ffffff}, new float[]{0,0.25f, 0.5f, 0.75f,1}, Shader.TileMode.CLAMP);
                gradient = new LinearGradient(-width, 0, 0, 0, new int[]{0x3300ff00, 0xffffffff, 0x3300ff00}, new float[]{0, 0.5f,1}, Shader.TileMode.CLAMP);
                paint.setShader(gradient);
                matrix = new Matrix();
            }
        }
    }
}
