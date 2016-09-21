/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ysp.houge.R;

/**
 * This class is used for ...
 * 
 * @author tyn
 * @version 1.0, 2015-2-13 下午11:49:02
 */

public class ImageViewSubClass extends ImageView {

	private Context context;

	public ImageViewSubClass(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}

	public ImageViewSubClass(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public ImageViewSubClass(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 获取控件需要重新绘制的区域
		Rect rect = canvas.getClipBounds();
		rect.bottom--;
		rect.right--;
		Paint paint = new Paint();
		paint.setColor(context.getResources().getColor(R.color.color_e5e5e5));
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		canvas.drawRect(rect, paint);
	}
}
