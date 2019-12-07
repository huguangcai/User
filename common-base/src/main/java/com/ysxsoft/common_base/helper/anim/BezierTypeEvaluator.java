package com.ysxsoft.common_base.helper.anim;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * 二阶贝塞尔曲线估值器
 */
public class BezierTypeEvaluator implements TypeEvaluator<PointF> {
	private PointF mControllPoint;

	public BezierTypeEvaluator(PointF mControllPoint) {
		this.mControllPoint = mControllPoint;
	}

	@Override
	public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
		PointF pointCur = new PointF();
		pointCur.x = (1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * (float)mControllPoint.x + fraction * fraction * endValue.x;
		pointCur.y = (1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * (float)mControllPoint.y + fraction * fraction * endValue.y;
		return pointCur;
	}
}