package com.ysp.houge.widget;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.TimeEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author it_hu 选择时间的扇形视图
 *
 */
@SuppressLint({ "UseSparseArrays", "DrawAllocation" })
public class TimeSector extends View {

	private int dia;// 图形的直径
	private int colorOrange = getResources().getColor(R.color.color_app_theme_orange_bg);
    private int colorBlue = getResources().getColor(R.color.color_app_theme_blue_bg);
	private int colorGray = getResources().getColor(R.color.color_e5e5e5);
	private TimeEntity timeEntity;
	private Paint paint;
	private Paint paintGray;

	public TimeSector(Context context) {
		super(context);
		initViews(context);
	}

	public TimeSector(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}

	public TimeSector(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews(context);
	}

	@SuppressLint("NewApi")
	public TimeSector(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initViews(context);
	}

	private void initViews(Context context) {
		// TODO 初始化View

		paint = new Paint();
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_SELLER:
                paint.setColor(colorBlue);
                break;
            case MyApplication.LOG_STATUS_BUYER:
                paint.setColor(colorOrange);
                break;
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1); // 设置圆环的宽度
        paint.setAntiAlias(true); // 消除锯齿

		paintGray = new Paint();
		paintGray.setColor(colorGray);
		paintGray.setStyle(Paint.Style.FILL);
		paintGray.setStrokeWidth(1); // 设置圆环的宽度
		paintGray.setAntiAlias(true);

		timeEntity = new TimeEntity(-1, TimeEntity.have_time, TimeEntity.have_time, TimeEntity.have_time);
	}

	public void setEntity(TimeEntity timeEntity) {
		this.timeEntity = timeEntity;
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// 计算半径
		int w = getWidth();
		int h = getHeight();
		dia = Math.min(w, h);

		// (定义弧形的宽高,当这个弧为一个圆的时候的位置)
		RectF rectF = null;
		int margin = 0;// 长边的边距
		if (w > dia) {
			margin = (w - dia) / 2;
			rectF = new RectF(margin, 0, dia + margin, dia);
		} else {
			margin = (h - dia) / 2;
			rectF = new RectF(0, margin, dia, dia + margin);
		}

		// 根据数据绘制三个扇形
		if (timeEntity.getMorning() == TimeEntity.have_time) {
			// 上午的弧形
			canvas.drawArc(rectF, -90, 120, true, paint);
		} else {
			canvas.drawArc(rectF, -90, 120, true, paintGray);

		}

		if (timeEntity.getNoon() == TimeEntity.have_time) {
			// 下午的弧形
			canvas.drawArc(rectF, 30, 120, true, paint);
		}else {
			canvas.drawArc(rectF, 30, 120, true, paintGray);
		}

		if (timeEntity.getNight() == TimeEntity.have_time) {
			// 晚上的弧
			canvas.drawArc(rectF, 150, 120, true, paint);
		}else {
			canvas.drawArc(rectF, 150, 120, true, paintGray);
		}

	}
}
