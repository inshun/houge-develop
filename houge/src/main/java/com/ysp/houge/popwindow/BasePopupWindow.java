package com.ysp.houge.popwindow;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

/**
 * @描述: PopupWindow基类
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月5日下午8:27:05
 * @version 1.0
 */
public abstract class BasePopupWindow extends PopupWindow {

	public View contentView;

	@SuppressWarnings("deprecation")
	public BasePopupWindow(View contentView, int width, int height) {
		super(contentView, width, height, true);

		this.contentView = contentView;

		// 使其聚集
		setFocusable(true);
		// 设置允许在外点击消失
		setOutsideTouchable(true);
		// 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
		setBackgroundDrawable(new BitmapDrawable());

		initViews();
		initEvents();
		initData();
	}

	public abstract void initViews();

	public abstract void initEvents();

	public abstract void initData();

	/**
	 * 显示在parent的上部并水平居中
	 * 
	 * @param parent
	 */
	public void showViewTopCenter(View parent) {
		showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
	}

	/**
	 * 显示在parent的中心
	 * 
	 * @param parent
	 */
	public void showViewCenter(View parent) {
		showAtLocation(parent, Gravity.CENTER, 0, 0);
	}

}
