package com.ysxsoft.common_base.view.custom.axis;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.ysxsoft.common_base.R;

public class EmsLineView extends LinearLayout {
	boolean isShowGradient = false;
	private Paint pointPaint;
	private Paint emptyPointPaint;
	private Paint linePaint;
	private Context context;
	private int normalPointWidth;//节点宽/高度
	private int selectPointWidth;//节点宽/高度
	private Bitmap normalBitmap;
	private Bitmap selectBitmap;
	private int lineColor = -1;
	private boolean pointWrap = false;//节点宽高是否 自适应图片资源 宽高
	private float lineWidth = 0;//节点宽高是否 自适应图片资源 宽高
	private boolean canDrawEnd = false;//是否绘制结束
	private boolean isOnlyFirstSelected = false;//是否只第一个选中
	private boolean normalIsEmpty = false;//默认是空心圆
	private int normalResourceId;
	private int selectedResouceId;
	private int normalPointColor;
	private int selectedPointColor;

	public EmsLineView(Context context) {
		this(context, null);
	}

	public EmsLineView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EmsLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context = context;
		setWillNotDraw(false);
		setOrientation(VERTICAL);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmsLineView);
		normalResourceId = typedArray.getResourceId(R.styleable.EmsLineView_normalDrawable, -1);
		selectedResouceId = typedArray.getResourceId(R.styleable.EmsLineView_selectDrawable, -1);
		normalPointColor = typedArray.getColor(R.styleable.EmsLineView_normalPointColor, getResources().getColor(R.color.color_989898));
		selectedPointColor = typedArray.getColor(R.styleable.EmsLineView_selectPointColor, getResources().getColor(R.color.color_f64444));

		normalPointWidth = typedArray.getDimensionPixelSize(R.styleable.EmsLineView_normalPointWidth, 40);
		selectPointWidth = typedArray.getDimensionPixelSize(R.styleable.EmsLineView_selectPointWidth, 40);
		lineWidth = typedArray.getDimensionPixelOffset(R.styleable.EmsLineView_lineWidth, 2);
		typedArray.recycle();
		initPaint();
	}

	private void initPaint() {
		pointPaint = new Paint();
		pointPaint.setDither(true);
		pointPaint.setAntiAlias(true);
		pointPaint.setStyle(Paint.Style.FILL);

		emptyPointPaint = new Paint();//空心圆
		emptyPointPaint.setDither(true);
		emptyPointPaint.setStrokeWidth(lineWidth);
		emptyPointPaint.setColor(lineColor);//线条颜色
		emptyPointPaint.setAntiAlias(true);
		emptyPointPaint.setStyle(Paint.Style.STROKE);

		linePaint = new Paint();
		linePaint.setDither(true);
		linePaint.setStrokeWidth(lineWidth);
		linePaint.setColor(lineColor);//线条颜色
		linePaint.setAntiAlias(true);
		linePaint.setStyle(Paint.Style.STROKE);
	}

	public void setBlueGradient(boolean isShowGradient) {
		this.isShowGradient = isShowGradient;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (isShowGradient) {
			pointPaint.reset();
			linePaint.reset();
			//渐变色
			LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, new int[]{getResources().getColor(R.color.blueStart), getResources().getColor(R.color.blueEnd)}, null, LinearGradient.TileMode.CLAMP);
			pointPaint.setShader(linearGradient);
			linePaint.setShader(linearGradient);
		}

		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			int height = child.getHeight();
			drawPoint(i, canvas, child.getTop() + normalPointWidth);
			if (i == (childCount - 1)) {
				if (canDrawEnd) {
					drawLine(canvas, child.getTop() + normalPointWidth + normalPointWidth / 2, height - normalPointWidth);
				}
			} else {
				drawLine(canvas, child.getTop() + normalPointWidth + normalPointWidth / 2, height - normalPointWidth);
			}
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
	}

	/**
	 * 画点
	 *
	 * @param position
	 * @param canvas
	 * @param startY
	 */
	private void drawPoint(int position, Canvas canvas, int startY) {
		if (isOnlyFirstSelected && position == 0) {
			pointPaint.setColor(selectedPointColor);
			canvas.drawCircle(getPaddingLeft() + normalPointWidth, startY, normalPointWidth / 2, pointPaint);
		} else {
			emptyPointPaint.setColor(normalPointColor);
			if (normalIsEmpty) {
				canvas.drawCircle(getPaddingLeft() + normalPointWidth, startY, normalPointWidth / 2, emptyPointPaint);
			} else {
				canvas.drawCircle(getPaddingLeft() + normalPointWidth, startY, normalPointWidth / 2, pointPaint);//蓝色渐变
			}
		}
	}

	private void drawLine(Canvas canvas, int startY, int height) {
		canvas.drawLine(getPaddingLeft() + normalPointWidth, startY, getPaddingLeft() + normalPointWidth, startY + height, linePaint);
	}

	/**
	 * 默认是空心圆
	 *
	 * @param normalIsEmpty
	 */
	public void setNormalIsEmpty(boolean normalIsEmpty) {
		this.normalIsEmpty = normalIsEmpty;
	}

	/**
	 * 是否画最后一条线
	 *
	 * @param canDrawEnd
	 */
	public void setCanDrawEnd(boolean canDrawEnd) {
		this.canDrawEnd = canDrawEnd;
	}

	///////////////////////////////////////////////////////////////////////////
	// 对外方法
	///////////////////////////////////////////////////////////////////////////
	public void setLineColor(int lineColor) {
		if (linePaint != null) {
			linePaint.setColor(lineColor);
		} else {
			this.lineColor = lineColor;
		}
	}

	/**
	 * 是否绘制最后节点
	 *
	 * @param canDraw
	 */
	public void setDrawEndPoint(boolean canDraw) {
		this.canDrawEnd = canDraw;
	}

	/**
	 * 是否绘制最后节点
	 *
	 * @param isOnlyFirstSelected
	 */
	public void setIsFirst(boolean isOnlyFirstSelected) {
		this.isOnlyFirstSelected = isOnlyFirstSelected;
	}
}
