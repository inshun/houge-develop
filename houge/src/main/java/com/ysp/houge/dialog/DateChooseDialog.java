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
package com.ysp.houge.dialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.DateDialogDescriptor;

/**
 * @描述:年月选择对话框
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日下午8:55:26
 * @version 1.0
 */
public class DateChooseDialog extends Dialog implements
		android.view.View.OnClickListener {
	/** 当前的年份 */
	private int curYear;
	/** 当前的月份 */
	private int curMonth;
	/** 选择的年份 */
	private String yearChoosed;
	/** 选择的月份 */
	private String monthChoosed;
	/** 对话框描述 */
	private DateDialogDescriptor descriptor;
	/** 至今按钮 */
	private TextView mDateNowOn;
	/** 年份滚动轮 */
	private WheelView mDateYear;
	/** 月份滚动轮 */
	private WheelView mDateMonth;
	/** 取消按钮 */
	private Button mCancelBtn;
	/** 确认按钮 */
	private Button mSureBtn;
	/** 上下文 */
	private Context context;
	/** 回调给页面的接口 */
	private OnDateDialogListener onDateDialogListener;
	/** 要显示的所有的月份 */
	private List<String> months = new ArrayList<String>();
	/** 要显示的所有的年份 */
	private List<String> years = new ArrayList<String>();
	public DateChooseDialog(Context context,
			OnDateDialogListener onDateDialogListener,
			DateDialogDescriptor descriptor) {
		// 设置样式
		super(context, R.style.FileDesDialogNoBackground);

		this.context = context;
		this.onDateDialogListener = onDateDialogListener;
		this.descriptor = descriptor;
		// 设置对话框外部可点击
		setCanceledOnTouchOutside(true);
		// 初始化布局
		setContentView(R.layout.dialog_date);
		initViews();
		initData();
		// 设置显示动画
		getWindow().setWindowAnimations(R.style.mydialog);
		// 显示对话框
		try {
			show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 初始化控件
	 */
	private void initViews() {
		mDateNowOn = (TextView) findViewById(R.id.mDateNowOn);
		mDateYear = (WheelView) findViewById(R.id.mDateYear);
		mDateMonth = (WheelView) findViewById(R.id.mDateMonth);
		mCancelBtn = (Button) findViewById(R.id.mCancelBtn);
		mSureBtn = (Button) findViewById(R.id.mSureBtn);
		this.mCancelBtn.setOnClickListener(this);
		this.mSureBtn.setOnClickListener(this);
		if (descriptor.isShowNowOn) {
			this.mDateNowOn.setVisibility(View.VISIBLE);
			this.mDateNowOn.setOnClickListener(this);
		} else {
			this.mDateNowOn.setVisibility(View.GONE);
		}
	}

	private void initDate() {
		Calendar calendar = Calendar.getInstance();
		curYear = calendar.get(Calendar.YEAR);
		curMonth = calendar.get(Calendar.MONTH) + 1;
		for (int i = 1971; i <= curYear; i++) {
			years.add(0, i + "年");
		}
		for (int i = 1; i < 13; i++) {
			months.add(i + "月");
		}
	}

	private void initData() {
		initDate();
		mDateYear.setVisibleItems(5); // Number of items
		mDateYear.setWheelBackground(R.drawable.wheel_bg_holo);
		mDateYear.setWheelForeground(R.drawable.wheel_val_holo);
		mDateYear.setShadowColor(0x00000000, 0x00000000, 0x00000000);
		mDateYear.setViewAdapter(new YearAdapter(context));
		if (descriptor.defaultDisplayYear > curYear) {
			mDateYear.setCurrentItem(0);
			yearChoosed = years.get(0).replace("年", "");
		} else {
			mDateYear.setCurrentItem(curYear - descriptor.defaultDisplayYear);
			yearChoosed = years.get(curYear - descriptor.defaultDisplayYear)
					.replace("年", "");
		}
		mDateYear.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				yearChoosed = years.get(newValue).replace("年", "");
				if (String.valueOf(curYear).equals(yearChoosed)) {
					months.clear();
					for (int i = 1; i <= curMonth; i++) {
						months.add(i + "月");
					}
				} else {
					months.clear();
					for (int i = 1; i < 13; i++) {
						months.add(i + "月");
					}
				}
				mDateMonth.setViewAdapter(new MonthAdatper(context));
			}
		});

		mDateMonth.setVisibleItems(5); // Number of items
		mDateMonth.setWheelBackground(R.drawable.wheel_bg_holo);
		mDateMonth.setWheelForeground(R.drawable.wheel_val_holo);
		mDateMonth.setShadowColor(0x00000000, 0x00000000, 0x00000000);
		mDateMonth.setViewAdapter(new MonthAdatper(context));
		if (descriptor.defaultDisplayMonth > 12) {
			mDateMonth.setCurrentItem(6);
			monthChoosed = months.get(6).replace("月", "");
		} else {
			if (descriptor.defaultDisplayMonth > curMonth) {
				mDateMonth.setCurrentItem(curMonth - 1);
				monthChoosed = months.get(curMonth - 1).replace("月", "");
			} else {
				mDateMonth.setCurrentItem(descriptor.defaultDisplayMonth - 1);
				monthChoosed = months.get(descriptor.defaultDisplayMonth - 1)
						.replace("月", "");

			}
		}
		mDateMonth.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				monthChoosed = months.get(newValue).replace("月", "");
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		dismiss();
		switch (arg0.getId()) {
		case R.id.mDateNowOn:
			onDateDialogListener.clickNowOnBtn();
			break;
		case R.id.mCancelBtn:
			onDateDialogListener.clickCancelBtn();
			break;
		case R.id.mSureBtn:
			onDateDialogListener.clickSureBtn(yearChoosed, monthChoosed);
			break;
		default:
			break;
		}
	}

	public interface OnDateDialogListener {
		/** 点击日期选择对话框中的取消按钮 */
		void clickCancelBtn();

		/** 点击日期选择对话框中的确定按钮 */
		void clickSureBtn(String year, String month);

		/** 点击日期选择对话框中的至今按钮 */
		void clickNowOnBtn();
	}

	/**
	 * Adapter for year
	 */
	private class YearAdapter extends AbstractWheelTextAdapter {

		/**
		 * Constructor
		 */
		protected YearAdapter(Context context) {
			super(context, R.layout.city_holo_layout, NO_RESOURCE);
			setItemTextResource(R.id.city_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return years.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return years.get(index);
		}
	}

	/**
	 * Adapter for year
	 */
	private class MonthAdatper extends AbstractWheelTextAdapter {

		/**
		 * Constructor
		 */
		protected MonthAdatper(Context context) {
			super(context, R.layout.city_holo_layout, NO_RESOURCE);
			setItemTextResource(R.id.city_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return months.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return months.get(index);
		}
	}
}
