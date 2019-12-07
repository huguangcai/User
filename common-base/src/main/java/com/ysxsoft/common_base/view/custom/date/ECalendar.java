package com.ysxsoft.common_base.view.custom.date;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.core.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.ysxsoft.common_base.utils.LunarHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * 单页日历
 */

class ECalendar extends View {

	private String[] weekTitles = new String[]{"日", "一", "二", "三", "四", "五", "六"};
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
	private Calendar calendar = Calendar.getInstance();
	//今天所在的年月日信息
	private int currentYear, currentMonth, currentDay;
	//点击选中的年月日信息
	private int clickYear, clickMonth, clickDay;
	//当前选择的年月信息
	private int selectYear, selectMonth;
	private GregorianCalendar date = new GregorianCalendar();
	private HashMap<String, Boolean> signRecords;
	private HashMap<String, CalendarBean> datas;
	private GestureDetectorCompat detectorCompat;
	private Bitmap signSuccess, signError;
	private ECalendarView.Config config;
	private Paint paint = new Paint();
	private LunarHelper lunarHelper;
	private int itemWidth, itemHeight;
	private float solarTextHeight;
	private int currentPosition;
	private float signDelay;



	public ECalendar(Context context) {
		super(context);
	}

	public ECalendar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ECalendar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	void init(ECalendarView.Config config) {
		this.config = config;
		currentYear = calendar.get(Calendar.YEAR);
		currentMonth = calendar.get(Calendar.MONTH);
		currentDay = calendar.get(Calendar.DAY_OF_MONTH);
		selectYear = currentYear;
		selectMonth = currentMonth;
		clickYear = currentYear;
		clickMonth = currentMonth;
		clickDay = currentDay;
		detectorCompat = new GestureDetectorCompat(getContext(), gestureListener);
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextAlign(Paint.Align.CENTER);
		paint.setStrokeWidth(sp2px(0.6f));
		currentPosition = (currentYear - 1970) * 12 + currentMonth + 1;
		setClickable(true);
		if (config.isShowLunar){
			lunarHelper = new LunarHelper();
		}
		if (config.signIconSuccessId != 0) {
			signSuccess = BitmapFactory.decodeResource(getResources(), config.signIconSuccessId);
			signError = BitmapFactory.decodeResource(getResources(), config.signIconErrorId);
			if (signSuccess != null) {
				int width = signSuccess.getWidth();
				int height = signSuccess.getHeight();
				Matrix matrix = new Matrix();
				matrix.postScale(config.signIconSize / width, config.signIconSize / height);
				signSuccess = Bitmap.createBitmap(signSuccess, 0, 0, width, height, matrix, true);
				signError = Bitmap.createBitmap(signError, 0, 0, width, height, matrix, true);
			}
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		itemWidth = getWidth() / 7;
		itemHeight = (getHeight() - (int) config.weekHeight) / 6;
		paint.setTextSize(config.calendarTextSize);
		solarTextHeight = getTextHeight();
		signDelay = getX(Math.min(itemHeight, itemWidth) / 2, -45);
	}

	final void selectDate(int position) {
		currentPosition = position - 1;
		selectYear = 1970 + currentPosition / 12;
		selectMonth = currentPosition % 12;
		invalidate();
	}

	final void initSelect(int clickYear, int clickMonth, int clickDay) {
		this.clickYear = clickYear;
		this.clickMonth = clickMonth;
		this.clickDay = clickDay;
		invalidate();
	}

	private int delayHeight = 30;
	private int delayWidth = 10;
	private float halfCalendarDistanceX;
	@Override
	protected void onDraw(Canvas canvas) {
		paint.setColor(Color.LTGRAY);
		//画日历顶部周的标题
		paint.setTextSize(config.weekTextSize);
		paint.setColor(config.weekTextColor);
		float delay = getTextHeight() / 4;
		//画日历
		int year = 1970 + currentPosition / 12;
		int month = currentPosition % 12;
		calendar.set(year, month, 1);
		int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		int selectMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		//上一个月的最大天数
		calendar.add(Calendar.MONTH, -1);
		int previousMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		delay = solarTextHeight / 4;
		if (config.isShowLunar){
			delay = 0;
		}
		for (int i = 1; i <= 42; i++) {
			int copyI = i - 1;
			int x = (copyI % 7) * itemWidth + itemWidth / 2;
			int y = (copyI / 7) * itemHeight + itemHeight / 2 + (int) config.weekHeight ;
			if (i <= firstDay) {//前一月数据
				if (!config.isShowOtherMonth){
					continue;
				}
				int day = previousMonthMaxDay - firstDay + i;
				paint.setColor(config.otherMonthTextColor);
				paint.setTextSize(config.calendarTextSize);
				canvas.drawText(String.valueOf(day), x, y , paint);
//				drawLunar(canvas, month == 0 ? (year - 1) : year, month == 0 ? 11 : (month - 1), day, x, y);
			} else if (i > selectMonthMaxDay + firstDay) {//后一月数据
				if (!config.isShowOtherMonth){
					continue;
				}
				int day = i - firstDay - selectMonthMaxDay;
				paint.setColor(config.otherMonthTextColor);
				paint.setTextSize(config.calendarTextSize);

				canvas.drawText(String.valueOf(day), x, y, paint);
//				drawLunar(canvas, month == 11 ? (year + 1) : year, month == 11 ? 0 : (month + 1), day, x, y);
			} else {//当前月数据
				int day = i - firstDay;
				if (year == currentYear && month == currentMonth && day == currentDay) {
					//本月今天
					paint.setColor(config.todayTextColor);
				} else {
					//本月其他天
					if((i-1)%7==0||i%7==0){
						paint.setColor(Color.parseColor("#999999"));
					}else{
						paint.setColor(config.calendarTextColor);
					}
				}
				if (year == clickYear && month == clickMonth && day == clickDay) {//当前选中的一天
					//paint.setColor(config.selectColor);
					//canvas.drawCircle(x, y , Math.min(itemHeight, itemWidth) / 2, paint);
					//paint.setColor(config.selectTextColor);
				}
				paint.setTextSize(config.calendarTextSize);

				Rect rect=new Rect();
				paint.getTextBounds(String.valueOf(day),0,String.valueOf(day).length(),rect);
				//halfCalendarDistanceX=paint.measureText(String.valueOf(day))/2;//日历文字的一半距离
				int dayHeight=rect.bottom-rect.top;

//				drawSign(canvas, year, month, day, x, y);
				drawSignBlueCircle(canvas,year,month,day,x,y);
				canvas.drawText(String.valueOf(day), x, y+dayHeight/2, paint);
//				drawLunar(canvas, year, month, day, x, y);
//				drawOrderNum(canvas, year, month, day, x, y);
			}
		}
	}

	private void drawLunar(Canvas canvas, int year, int month, int day, int x, int y) {
		if (config.isShowLunar) {
			if (year != clickYear || month != clickMonth || day != clickDay) {
				paint.setColor(config.lunarTextColor);
			}
			String lunar = lunarHelper.SolarToLunarString(year, month + 1, day);
			paint.setTextSize(config.lunarTextSize);
			canvas.drawText(lunar, x, y + solarTextHeight * 2 / 3, paint);
		}
	}

	private void drawSign(Canvas canvas, int year, int month, int day, int x, int y) {
		if (signSuccess == null || signRecords == null){
			return;
		}
		date.set(year, month, day);
		String dateStr = format.format(date.getTime());
		if (signRecords.containsKey(dateStr)) {
			if (year != clickYear || month != clickMonth || day != clickDay) {
				paint.setColor(config.signTextColor);
			}
			if (signRecords.get(dateStr)) {
				canvas.drawBitmap(signSuccess, x + signDelay - config.signIconSize / 2,
						y - signDelay - config.signIconSize / 2, paint);
			} else {
				canvas.drawBitmap(signError, x + signDelay - config.signIconSize / 2,
						y - signDelay - config.signIconSize / 2, paint);
			}
		}
	}

	/**
	 * 画签名颜色
	 * @param canvas
	 * @param year
	 * @param month
	 * @param day
	 * @param x
	 * @param y
	 */
	private void drawSignBlueCircle(Canvas canvas, int year, int month, int day, int x, int y) {
		if (datas == null){
			return;
		}
		date.set(year, month, day);
		String dateStr = format.format(date.getTime());
		if (datas.containsKey(dateStr)) {
			paint.setColor(config.selectColor);
			//签到的圆是2分之1
			canvas.drawCircle(x,y,Math.min(itemHeight, itemWidth)*1/3, paint);
			paint.setColor(config.selectTextColor);
		}
	}

	private void drawOrderNum(Canvas canvas, int year, int month, int day, int x, int y) {
		if (datas == null){
			return;
		}
		date.set(year, month, day);
		String dateStr = format.format(date.getTime());
		if (datas.containsKey(dateStr)) {
			CalendarBean c = datas.get(dateStr);
			if (year != clickYear || month != clickMonth || day != clickDay) {
				paint.setColor(Color.parseColor("#282828"));
			} else {
				paint.setColor(Color.WHITE);
			}
			paint.setTextAlign(Paint.Align.LEFT);
			float size= sp2px(10);
			paint.setTextSize(size);
			if (year != clickYear || month != clickMonth || day != clickDay) {
				//选中时候
			}else {
			}
		}
		paint.setTextAlign(Paint.Align.CENTER);
	}

	int[] getCurrentDayInfo() {
		return new int[]{currentYear, currentMonth, currentDay};
	}

	private float getX(float radius, int angle) {
		int centerX = 0;
		return (float) (centerX + radius * Math.cos(angle * 3.14 / 180));
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		detectorCompat.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	private GestureDetector.OnGestureListener
			gestureListener = new GestureDetector.SimpleOnGestureListener() {
		@Override
		public boolean onDown(MotionEvent e) {
			return super.onDown(e);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			int position = getPosition(e.getX(), e.getY());
			if (dateClickListener != null) {
				calendar.set(selectYear, selectMonth, 1);
				int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				calendar.set(selectYear, selectMonth, position - firstDay + 1);
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
				if (month == selectMonth) {
					clickYear = year;
					clickMonth = month;
					clickDay = day;
					invalidate();
					dateClickListener.dateSelect(year, month, day, week);
				}
			}
			return super.onSingleTapUp(e);
		}
	};

	private int getPosition(float x, float y) {
		y -= config.weekHeight;
		int y1 = (int) (y / itemHeight);
		int x1 = (int) (x / itemWidth);
		return y1 * 7 + x1;
	}

	void setSignRecords(HashMap<String, Boolean> signRecords) {
		this.signRecords = signRecords;
	}

	void setDatas(HashMap<String, CalendarBean> datas) {
		this.datas = datas;
	}

	private float sp2px(float spValue) {
		float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
		return spValue * fontScale + 0.5f;
	}

	private float getTextHeight() {
		return paint.descent() - paint.ascent();
	}

	interface DateSelectListener {
		void dateSelect(int year, int month, int day, int week);
	}

	private DateSelectListener dateClickListener;

	void setDateSelectListener(DateSelectListener clickListener) {
		dateClickListener = clickListener;
	}
}
