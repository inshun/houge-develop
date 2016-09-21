package com.ysp.houge.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/**
 * @描述:切换viewpager时，圆点指示器
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月25日下午2:32:58
 * @version 2.4
 */
public class EmoDotView extends View {
	private static final int RADIUS = 3;
	private static final float PADDING = 15;
	private int mPosition;
	private int mDisWidth;
	private Paint p;
	private int mInitX = 0;
	private float density;
	private int count;

	private Context context;
	private int colorIdNormal;
	private int colorIdPressed;

	public EmoDotView(Context context) {
		super(context);
		initializeView(context);
	}

	public EmoDotView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeView(context);
	}

	public EmoDotView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeView(context);
	}

	private void initializeView(Context context) {
		this.context = context;
		density = context.getResources().getDisplayMetrics().density;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, (int) (RADIUS * density) * 2);
		lp.gravity = Gravity.CENTER;
		mDisWidth = context.getResources().getDisplayMetrics().widthPixels;
		this.setLayoutParams(lp);
	}

	public void setPos(int count, int mPosition) {
		this.mPosition = mPosition;
		this.count = count;
		mInitX = (mDisWidth - (count - 1) * (int) (density * PADDING)) / 2;
		invalidate();
	}

	public void setColor(int colorIdNormal, int colorIdPressed) {
		this.colorIdNormal = colorIdNormal;
		this.colorIdPressed = colorIdPressed;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		p = new Paint();
		for (int i = 0; i < count; i++) {
			if (i == mPosition) {
				if (colorIdPressed != 0) {
					p.setColor(colorIdPressed);
				} else {
					p.setColor(Color.WHITE);
				}
				canvas.drawCircle(i * (int) (density * PADDING) + mInitX,
						(int) (RADIUS * density), (int) (RADIUS * density), p);
			} else {
				if (colorIdNormal != 0) {
					p.setColor(colorIdNormal);
				} else {
					p.setColor(Color.GRAY);
				}
				canvas.drawCircle(i * (int) (density * PADDING) + mInitX,
						(int) (RADIUS * density), (int) (RADIUS * density), p);
			}
		}
	}
}
