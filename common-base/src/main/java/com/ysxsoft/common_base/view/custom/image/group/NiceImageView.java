package com.ysxsoft.common_base.view.custom.image.group;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.ysxsoft.common_base.R;
import com.ysxsoft.common_base.view.custom.image.group.helper.Utils;

/**
 * create by Sincerly on 2019/1/9 0009
 * 群头像  仿叮叮圆形头像(最多4个)
 * 微信方格
 **/
public class NiceImageView extends AppCompatImageView {
    private Context context;
    private boolean isCircle;
    private boolean isCoverSrc;
    private int borderWidth;
    private int borderColor;
    private int innerBorderWidth;
    private int innerBorderColor;
    private int cornerRadius;
    private int cornerTopLeftRadius;
    private int cornerTopRightRadius;
    private int cornerBottomLeftRadius;
    private int cornerBottomRightRadius;
    private int maskColor;
    private Xfermode xfermode;
    private int width;
    private int height;
    private float radius;
    private float[] borderRadii;
    private float[] srcRadii;
    private RectF srcRectF;
    private RectF borderRectF;
    private Path path;
    private Paint paint;

    public NiceImageView(Context context) {
        this(context, (AttributeSet)null);
    }

    public NiceImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.borderColor = -1;
        this.innerBorderColor = -1;
        this.path = new Path();
        this.paint = new Paint();
        this.context = context;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NiceImageView, 0, 0);

        for(int i = 0; i < ta.getIndexCount(); ++i) {
            int attr = ta.getIndex(i);
            if (attr == R.styleable.NiceImageView_is_cover_src) {
                this.isCoverSrc = ta.getBoolean(attr, this.isCoverSrc);
            } else if (attr == R.styleable.NiceImageView_is_circle) {
                this.isCircle = ta.getBoolean(attr, this.isCircle);
            } else if (attr == R.styleable.NiceImageView_border_width) {
                this.borderWidth = ta.getDimensionPixelSize(attr, this.borderWidth);
            } else if (attr == R.styleable.NiceImageView_border_color) {
                this.borderColor = ta.getColor(attr, this.borderColor);
            } else if (attr == R.styleable.NiceImageView_inner_border_width) {
                this.innerBorderWidth = ta.getDimensionPixelSize(attr, this.innerBorderWidth);
            } else if (attr == R.styleable.NiceImageView_inner_border_color) {
                this.innerBorderColor = ta.getColor(attr, this.innerBorderColor);
            } else if (attr == R.styleable.NiceImageView_corner_radius) {
                this.cornerRadius = ta.getDimensionPixelSize(attr, this.cornerRadius);
            } else if (attr == R.styleable.NiceImageView_corner_top_left_radius) {
                this.cornerTopLeftRadius = ta.getDimensionPixelSize(attr, this.cornerTopLeftRadius);
            } else if (attr == R.styleable.NiceImageView_corner_top_right_radius) {
                this.cornerTopRightRadius = ta.getDimensionPixelSize(attr, this.cornerTopRightRadius);
            } else if (attr == R.styleable.NiceImageView_corner_bottom_left_radius) {
                this.cornerBottomLeftRadius = ta.getDimensionPixelSize(attr, this.cornerBottomLeftRadius);
            } else if (attr == R.styleable.NiceImageView_corner_bottom_right_radius) {
                this.cornerBottomRightRadius = ta.getDimensionPixelSize(attr, this.cornerBottomRightRadius);
            } else if (attr == R.styleable.NiceImageView_mask_color) {
                this.maskColor = ta.getColor(attr, this.maskColor);
            }
        }

        ta.recycle();
        this.borderRadii = new float[8];
        this.srcRadii = new float[8];
        this.borderRectF = new RectF();
        this.srcRectF = new RectF();
        this.setScaleType(ScaleType.FIT_XY);
        this.xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.calculateRadii();
        this.clearInnerBorderWidth();
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
        this.initBorderRectF();
        this.initSrcRectF();
    }

    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(this.srcRectF, (Paint)null, Canvas.ALL_SAVE_FLAG);
        if (!this.isCoverSrc) {
            float sx = 1.0F * (float)(this.width - 2 * this.borderWidth - 2 * this.innerBorderWidth) / (float)this.width;
            float sy = 1.0F * (float)(this.height - 2 * this.borderWidth - 2 * this.innerBorderWidth) / (float)this.height;
            canvas.scale(sx, sy, (float)this.width / 2.0F, (float)this.height / 2.0F);
        }

        super.onDraw(canvas);
        this.paint.reset();
        this.path.reset();
        if (this.isCircle) {
            this.path.addCircle((float)this.width / 2.0F, (float)this.height / 2.0F, this.radius, Path.Direction.CCW);
        } else {
            this.path.addRoundRect(this.srcRectF, this.srcRadii, Path.Direction.CCW);
        }

        this.paint.setAntiAlias(true);
        this.paint.setStyle(Paint.Style.FILL);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //解决9.0不显示圆形问题
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        }else{
            this.paint.setXfermode(this.xfermode);
        }
        canvas.drawPath(this.path, this.paint);
        this.paint.setXfermode((Xfermode)null);
        if (this.maskColor != 0) {
            this.paint.setColor(this.maskColor);
            canvas.drawPath(this.path, this.paint);
        }

        canvas.restore();
        this.drawBorders(canvas);
    }

    private void drawBorders(Canvas canvas) {
        if (this.isCircle) {
            if (this.borderWidth > 0) {
                this.drawCircleBorder(canvas, this.borderWidth, this.borderColor, this.radius - (float)this.borderWidth / 2.0F);
            }

            if (this.innerBorderWidth > 0) {
                this.drawCircleBorder(canvas, this.innerBorderWidth, this.innerBorderColor, this.radius - (float)this.borderWidth - (float)this.innerBorderWidth / 2.0F);
            }
        } else if (this.borderWidth > 0) {
            this.drawRectFBorder(canvas, this.borderWidth, this.borderColor, this.borderRectF, this.borderRadii);
        }

    }

    private void drawCircleBorder(Canvas canvas, int borderWidth, int borderColor, float radius) {
        this.initBorderPaint(borderWidth, borderColor);
        this.path.addCircle((float)this.width / 2.0F, (float)this.height / 2.0F, radius, Path.Direction.CCW);
        canvas.drawPath(this.path, this.paint);
    }

    private void drawRectFBorder(Canvas canvas, int borderWidth, int borderColor, RectF rectF, float[] radii) {
        this.initBorderPaint(borderWidth, borderColor);
        this.path.addRoundRect(rectF, radii, Path.Direction.CCW);
        canvas.drawPath(this.path, this.paint);
    }

    private void initBorderPaint(int borderWidth, int borderColor) {
        this.path.reset();
        this.paint.setStrokeWidth((float)borderWidth);
        this.paint.setColor(borderColor);
        this.paint.setStyle(Paint.Style.STROKE);
    }

    private void initBorderRectF() {
        if (!this.isCircle) {
            this.borderRectF.set((float)this.borderWidth / 2.0F, (float)this.borderWidth / 2.0F, (float)this.width - (float)this.borderWidth / 2.0F, (float)this.height - (float)this.borderWidth / 2.0F);
        }

    }

    private void initSrcRectF() {
        if (this.isCircle) {
            this.radius = (float)Math.min(this.width, this.height) / 2.0F;
            this.srcRectF.set((float)this.width / 2.0F - this.radius, (float)this.height / 2.0F - this.radius, (float)this.width / 2.0F + this.radius, (float)this.height / 2.0F + this.radius);
        } else {
            this.srcRectF.set(0.0F, 0.0F, (float)this.width, (float)this.height);
            if (this.isCoverSrc) {
                this.srcRectF = this.borderRectF;
            }
        }

    }

    private void calculateRadii() {
        if (!this.isCircle) {
            if (this.cornerRadius > 0) {
                for(int i = 0; i < this.borderRadii.length; ++i) {
                    this.borderRadii[i] = (float)this.cornerRadius;
                    this.srcRadii[i] = (float)this.cornerRadius - (float)this.borderWidth / 2.0F;
                }
            } else {
                this.borderRadii[0] = this.borderRadii[1] = (float)this.cornerTopLeftRadius;
                this.borderRadii[2] = this.borderRadii[3] = (float)this.cornerTopRightRadius;
                this.borderRadii[4] = this.borderRadii[5] = (float)this.cornerBottomRightRadius;
                this.borderRadii[6] = this.borderRadii[7] = (float)this.cornerBottomLeftRadius;
                this.srcRadii[0] = this.srcRadii[1] = (float)this.cornerTopLeftRadius - (float)this.borderWidth / 2.0F;
                this.srcRadii[2] = this.srcRadii[3] = (float)this.cornerTopRightRadius - (float)this.borderWidth / 2.0F;
                this.srcRadii[4] = this.srcRadii[5] = (float)this.cornerBottomRightRadius - (float)this.borderWidth / 2.0F;
                this.srcRadii[6] = this.srcRadii[7] = (float)this.cornerBottomLeftRadius - (float)this.borderWidth / 2.0F;
            }

        }
    }

    private void calculateRadiiAndRectF(boolean reset) {
        if (reset) {
            this.cornerRadius = 0;
        }

        this.calculateRadii();
        this.initBorderRectF();
        this.invalidate();
    }

    private void clearInnerBorderWidth() {
        if (!this.isCircle) {
            this.innerBorderWidth = 0;
        }

    }

    public void isCoverSrc(boolean isCoverSrc) {
        this.isCoverSrc = isCoverSrc;
        this.initSrcRectF();
        this.invalidate();
    }

    public void isCircle(boolean isCircle) {
        this.isCircle = isCircle;
        this.clearInnerBorderWidth();
        this.initSrcRectF();
        this.invalidate();
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = Utils.dp2px(this.context, (float)borderWidth);
        this.calculateRadiiAndRectF(false);
    }

    public void setBorderColor(@ColorInt int borderColor) {
        this.borderColor = borderColor;
        this.invalidate();
    }

    public void setInnerBorderWidth(int innerBorderWidth) {
        this.innerBorderWidth = Utils.dp2px(this.context, (float)innerBorderWidth);
        this.clearInnerBorderWidth();
        this.invalidate();
    }

    public void setInnerBorderColor(@ColorInt int innerBorderColor) {
        this.innerBorderColor = innerBorderColor;
        this.invalidate();
    }

    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = Utils.dp2px(this.context, (float)cornerRadius);
        this.calculateRadiiAndRectF(false);
    }

    public void setCornerTopLeftRadius(int cornerTopLeftRadius) {
        this.cornerTopLeftRadius = Utils.dp2px(this.context, (float)cornerTopLeftRadius);
        this.calculateRadiiAndRectF(true);
    }

    public void setCornerTopRightRadius(int cornerTopRightRadius) {
        this.cornerTopRightRadius = Utils.dp2px(this.context, (float)cornerTopRightRadius);
        this.calculateRadiiAndRectF(true);
    }

    public void setCornerBottomLeftRadius(int cornerBottomLeftRadius) {
        this.cornerBottomLeftRadius = Utils.dp2px(this.context, (float)cornerBottomLeftRadius);
        this.calculateRadiiAndRectF(true);
    }

    public void setCornerBottomRightRadius(int cornerBottomRightRadius) {
        this.cornerBottomRightRadius = Utils.dp2px(this.context, (float)cornerBottomRightRadius);
        this.calculateRadiiAndRectF(true);
    }

    public void setMaskColor(@ColorInt int maskColor) {
        this.maskColor = maskColor;
        this.invalidate();
    }
}
