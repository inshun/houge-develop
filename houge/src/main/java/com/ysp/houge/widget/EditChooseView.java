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

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.EditChooseViewDescriptor;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @描述:带有删除按钮的编辑框
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月18日上午11:13:41
 * @version 1.0
 */
public class EditChooseView extends RelativeLayout implements android.view.View.OnClickListener, TextWatcher {

	private View mView;
	private TextView mLeftLabel;
	private ImageView mClearImg;
	private EditText mClearEditText;
	private EditChooseViewDescriptor descriptor;
	private OnTextChangedListener onTextChangedListener;
	private Context context;

	public EditChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews(context);
	}

	public EditChooseView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}

	public EditChooseView(Context context) {
		super(context);
		initViews(context);
	}

	public OnTextChangedListener getOnTextChangedListener() {
		return onTextChangedListener;
	}

	public void setOnTextChangedListener(OnTextChangedListener onTextChangedListener) {
		this.onTextChangedListener = onTextChangedListener;
	}

	/**
	 * @param context
	 */
	private void initViews(Context context) {
		this.context = context;
		LayoutInflater.from(context).inflate(R.layout.view_edit_choose_search, this);
		mClearImg = (ImageView) findViewById(R.id.mClearImg);
		mClearEditText = (EditText) findViewById(R.id.mClearEditText);
		mLeftLabel = (TextView) findViewById(R.id.mLeftLabel);
		mView = (View) findViewById(R.id.mView);
		mClearImg.setOnClickListener(this);
		mClearEditText.addTextChangedListener(this);
	}

	public void initData(EditChooseViewDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	public boolean notifyDataSetChanged() {
		if (descriptor == null) {
			setVisibility(View.GONE);
			return false;
		}
		if (!TextUtils.isEmpty(descriptor.getHintText())) {
			mClearEditText.setHint(descriptor.getHintText());
		} else {
			mClearEditText.setHint("");
		}
		if (!TextUtils.isEmpty(descriptor.getLeftText())) {
			mLeftLabel.setHint(descriptor.getLeftText());
			mLeftLabel.setVisibility(View.VISIBLE);
		} else {
			mLeftLabel.setVisibility(View.GONE);
		}

		if (descriptor.getViewColor() > 0) {
			mView.setBackgroundColor(descriptor.getViewColor());
		}

		return true;
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.mClearImg:
			mClearEditText.setText("");
			mClearEditText.requestFocus();
			mClearImg.setVisibility(View.GONE);
			break;

		default:
			break;
		}
	}

	public String getText() {
		return mClearEditText.getText().toString().trim();
	}

	public void setText(String content) {
		mClearEditText.setText(content);
	}

	public void requestEditFocus() {
		mClearEditText.requestFocus();
	}

	/**
	 * @return the mClearEditText
	 */
	public EditText getmClearEditText() {
		return mClearEditText;
	}

	/**
	 * @param mClearEditText
	 *            the mClearEditText to set
	 */
	public void setmClearEditText(EditText mClearEditText) {
		this.mClearEditText = mClearEditText;
	}

	/**
	 * @描述:设置下划线的颜色值 @方法名: setViewColor @param color @返回类型 void @创建人 tyn @创建时间
	 * 2015年7月18日上午11:23:57 @修改人 tyn @修改时间
	 * 2015年7月18日上午11:23:57 @修改备注 @since @throws
	 */
	public void setViewColor(int color) {
		mView.setBackgroundColor(color);
	}

	public void setEditGarvity(int gravity) {
		mClearEditText.setGravity(gravity);
	}

	/**
	 * @描述:下划线的显示和隐藏 @方法名: setViewVisible @param isVisible @返回类型 void @创建人
	 * tyn @创建时间 2015年7月18日上午11:23:39 @修改人 tyn @修改时间
	 * 2015年7月18日上午11:23:39 @修改备注 @since @throws
	 */
	public void setViewVisible(boolean isVisible) {
		if (isVisible) {
			mView.setVisibility(View.VISIBLE);
		} else {
			mView.setVisibility(View.GONE);
		}

	}

	/**
	 * @描述:设置hint值 @方法名: setHint @param content @返回类型 void @创建人 tyn @创建时间
	 * 2015年7月18日上午11:22:49 @修改人 tyn @修改时间
	 * 2015年7月18日上午11:22:49 @修改备注 @since @throws
	 */
	public void setHint(String content) {
		mClearEditText.setHint(content);
	}

	/**
	 * @描述:设置输入类型 @方法名: setInputType @param inputType @返回类型 void @创建人 tyn @创建时间
	 * 2015年7月18日上午11:22:59 @修改人 tyn @修改时间
	 * 2015年7月18日上午11:22:59 @修改备注 @since @throws
	 */
	public void setInputType(int inputType) {
		mClearEditText.setInputType(inputType);
	}

	/**
	 * @描述:设置编辑框最大输入字数 @方法名: setMaxLength @param count @返回类型 void @创建人 tyn @创建时间
	 * 2015年7月18日上午11:23:17 @修改人 tyn @修改时间
	 * 2015年7月18日上午11:23:17 @修改备注 @since @throws
	 */
	public void setMaxLength(int count) {
		mClearEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(count) });
	}

	@Override
	public void afterTextChanged(Editable arg0) {
		if (TextUtils.isEmpty(mClearEditText.getText().toString())) {
			mClearImg.setVisibility(View.INVISIBLE);
		} else {
			mClearImg.setVisibility(View.VISIBLE);
		}
		if (onTextChangedListener != null) {
			onTextChangedListener.onTextChanged(mClearEditText.getText().toString());
		}
	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

	}

	public interface OnTextChangedListener {
		void onTextChanged(String keyword);
	}
}
