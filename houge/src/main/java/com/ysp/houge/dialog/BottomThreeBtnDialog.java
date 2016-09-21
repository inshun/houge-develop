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

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.BottomThreeBtnDescriptor;
import com.ysp.houge.model.entity.bean.BottomThreeBtnDescriptor.ClickType;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月14日下午6:44:55
 * @version 1.0
 */
public class BottomThreeBtnDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Button mBtnOne;
	private Button mBtnTwo;
	private Button mBtnCancel;

	private Context context;
	private OnThreeBtnClickListener listener;
	private BottomThreeBtnDescriptor descriptor;

	public BottomThreeBtnDialog(Context context,
			BottomThreeBtnDescriptor descriptor,
			OnThreeBtnClickListener listener) {
		super(context, R.style.BottomDialog);
		this.context = context;
		this.descriptor = descriptor;
		this.listener = listener;
		LayoutParams a = getWindow().getAttributes();
		a.gravity = Gravity.BOTTOM;
		getWindow().setAttributes(a);
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.dialog_bottom_three_btn);
		/** http://stackoverflow.com/questions/21699538/full-width-dialog */
		this.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		initViews();
		notifyDataSetChanged();
		try {
			show();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 */
	private void initViews() {
		mBtnOne = (Button) this.findViewById(R.id.mBtnOne);
		mBtnOne.setOnClickListener(this);
		mBtnTwo = (Button) this.findViewById(R.id.mBtnTwo);
		mBtnTwo.setOnClickListener(this);
		mBtnCancel = (Button) this.findViewById(R.id.mBtnCancel);
		mBtnCancel.setOnClickListener(this);
	}

	private void notifyDataSetChanged() {
		if (descriptor == null) {
			return;
		}
		if (!TextUtils.isEmpty(descriptor.labelOne)) {
			mBtnOne.setText(descriptor.labelOne);
			mBtnOne.setVisibility(View.VISIBLE);
		} else {
			mBtnOne.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(descriptor.labelTwo)) {
			mBtnTwo.setText(descriptor.labelTwo);
			mBtnTwo.setVisibility(View.VISIBLE);
		} else {
			mBtnTwo.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(descriptor.labelCancel)) {
			mBtnCancel.setText(descriptor.labelCancel);
		}
		if (descriptor.btnOneColorId != 0) {
			mBtnOne.setTextColor(descriptor.btnOneColorId);
		}
		if (descriptor.btnTwoColorId != 0) {
			mBtnTwo.setTextColor(descriptor.btnTwoColorId);
		}
	}

	@Override
	public void onClick(View arg0) {
		dismiss();
		switch (arg0.getId()) {
		case R.id.mBtnOne:
			if (listener != null) {
				listener.onThreeBtnClick(ClickType.ClickOne);
			}
			break;
		case R.id.mBtnTwo:
			if (listener != null) {
				listener.onThreeBtnClick(ClickType.ClickTwo);
			}
			break;
		case R.id.mBtnCancel:
			if (listener != null) {
				listener.onThreeBtnClick(ClickType.Cancel);
			}
			break;

		default:
			break;
		}
	}

	/**
	 * @描述:
	 * @Copyright Copyright (c) 2015
	 * @Company .
	 *
	 * @author tyn
	 * @date 2015年6月14日下午6:38:39
	 * @version 1.0
	 */
	public interface OnThreeBtnClickListener {
		void onThreeBtnClick(ClickType clickType);
	}

}
