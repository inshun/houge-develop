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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;

/**
 * This class is used for show Dialog with one editText
 * 
 * @author tyn
 * @version 1.0, 2014-9-2 下午1:45:56
 */

public class YesDialog extends Dialog implements
		android.view.View.OnClickListener {
	private Context context;
	private YesOrNoDialogEntity dialogEntity;
	private OnYesOrNoDialogClickListener listener;
	private Button mSureBtn;
	private TextView mYesOrNoLabel;
	private TextView mYesOrNoLabel1;

	public YesDialog(Context context, YesOrNoDialogEntity dialogEntity,
			OnYesOrNoDialogClickListener listener) {
		super(context, R.style.FileDesDialogNoBackground);
		this.context = context;
		this.dialogEntity = dialogEntity;
		this.listener = listener;
		setCanceledOnTouchOutside(true);
		setContentView(R.layout.dialog_yes);
		initViews();
		notifyDataSetChanged();
		/**
		 * 在继承的dialog的类里面用这个方法：getWindow().setWindowAnimations(R.style.xxxx);
		 * R.style.xxxx里面的数值可以用百分号
		 * */
		// 设置显示动画
		getWindow().setWindowAnimations(R.style.mydialog);
	}

	/**
	 * 
	 */
	private void initViews() {
		mYesOrNoLabel = (TextView) this.findViewById(R.id.mYesOrNoLabel);
		mYesOrNoLabel1 = (TextView) this.findViewById(R.id.mYesOrNoLabel1);
		mSureBtn = (Button) this.findViewById(R.id.mSureBtn);
		mSureBtn.setOnClickListener(this);
	}

	private void notifyDataSetChanged() {
		if (!TextUtils.isEmpty(dialogEntity.getBtnOkLabel())) {
			mSureBtn.setText(dialogEntity.getBtnOkLabel());
			mSureBtn.setVisibility(View.VISIBLE);
		} else {
			mSureBtn.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(dialogEntity.getTitleOne())) {
			mYesOrNoLabel.setText(dialogEntity.getTitleOne());
			mYesOrNoLabel.setVisibility(View.VISIBLE);
		} else {
			mYesOrNoLabel.setVisibility(View.GONE);
		}
		if (!TextUtils.isEmpty(dialogEntity.getTitleTwo())) {
			mYesOrNoLabel1.setText(dialogEntity.getTitleTwo());
			mYesOrNoLabel1.setVisibility(View.VISIBLE);
		} else {
			mYesOrNoLabel1.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		// click the button in the dialog
		case R.id.mSureBtn:
			dismiss();
			if (listener != null) {
				listener.onYesOrNoDialogClick(YesOrNoType.BtnOk);
			}
			break;
		case R.id.mCancelBtn:
			dismiss();
			break;

		default:
			break;
		}
	}

}
