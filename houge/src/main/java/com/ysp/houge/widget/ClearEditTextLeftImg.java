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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.ClearEditDescriptor;
import com.ysp.houge.widget.EditChooseView.OnTextChangedListener;

/**
 * This class is used for ...
 * 
 * @author tyn
 * @version 1.0, 2014-9-2 下午6:46:59
 */

public class ClearEditTextLeftImg extends RelativeLayout implements
		android.view.View.OnClickListener, TextWatcher {
	public EditText mClearEdit;
	private ImageView mClearImg;
	private Button mSearchBtn;
	private ClearEditDescriptor descriptor;
	private InputMethodManager inputMethodManager;
	private OnTextChangedListener listener;
	private OnTopTitleEditBtnClickListener titleBtnClickListener;
	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public ClearEditTextLeftImg(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	/**
	 * @param context
	 * @param attrs
	 */
	public ClearEditTextLeftImg(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * @param context
	 */
	public ClearEditTextLeftImg(Context context) {
		super(context);
		initView(context);
	}

	public OnTopTitleEditBtnClickListener getTitleBtnClickListener() {
		return titleBtnClickListener;
	}

	public void setTitleBtnClickListener(
			OnTopTitleEditBtnClickListener titleBtnClickListener) {
		this.titleBtnClickListener = titleBtnClickListener;
	}

	/**
	 *
	 */
	private void initView(Context context) {
		inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		LayoutInflater.from(context)
				.inflate(R.layout.view_clear_edit_btn, this);
		mClearEdit = (EditText) findViewById(R.id.mClearText);
		mClearImg = (ImageView) findViewById(R.id.mClearImg);
		mSearchBtn = (Button) findViewById(R.id.mSearchBtn);
		mClearImg.setOnClickListener(this);
		mSearchBtn.setOnClickListener(this);
		chargeText();
		mClearEdit.addTextChangedListener(this);
	}

	public void initData(ClearEditDescriptor descriptor,
			OnTextChangedListener listener) {
		this.descriptor = descriptor;
		this.listener = listener;
	}

	public void initData(ClearEditDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public void setBtnGone() {
		mSearchBtn.setVisibility(View.GONE);
	}

	public void setEditTextFocus() {
		mClearEdit.requestFocus();
	}

	public boolean notifyDataSetChanged() {
		if (descriptor == null) {
			setVisibility(View.GONE);
			return false;
		}
		if (!TextUtils.isEmpty(descriptor.getHint())) {
			mClearEdit.setHint(descriptor.getHint());
		}
		if (!TextUtils.isEmpty(descriptor.getBtnLabel())) {
			mSearchBtn.setHint(descriptor.getBtnLabel());
		}
		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.mClearImg:
			mClearEdit.setText("");
			break;
		case R.id.mSearchBtn:
			if (titleBtnClickListener != null) {
				inputMethodManager.hideSoftInputFromWindow(
						mClearEdit.getWindowToken(), 0);
				if (TextUtils.isEmpty(mClearEdit.getText().toString())) {
					titleBtnClickListener.onTopTitleEditBtnClick(
							ChangeButtonType.IsCancel, getText());
				} else {
					titleBtnClickListener.onTopTitleEditBtnClick(
							ChangeButtonType.IsSearch, getText());
				}

			}
			break;
		default:
			break;
		}
	}

	public String getText() {
		return mClearEdit.getText().toString().trim();
	}

	public void setText(String content) {
		mClearEdit.setText(content);
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		chargeText();
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	private void chargeText() {
		if (TextUtils.isEmpty(mClearEdit.getText().toString())) {
			mClearImg.setVisibility(View.INVISIBLE);
			mSearchBtn.setText("取消");
			if (listener != null) {
				listener.onTextChanged(mClearEdit.getText().toString());
			}
		} else {
			mClearImg.setVisibility(View.VISIBLE);
			mSearchBtn.setText("搜索");
			if (listener != null) {
				listener.onTextChanged(mClearEdit.getText().toString());
			}
		}
	}

	public enum ChangeButtonType {
		IsCancel, IsSearch
	}

	public interface OnTopTitleEditBtnClickListener {
		void onTopTitleEditBtnClick(ChangeButtonType changeButtonType,
				String keyword);
	}
}
