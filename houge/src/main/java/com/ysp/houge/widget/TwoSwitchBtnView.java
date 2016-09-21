package com.ysp.houge.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.houge.R;

/**
 * @描述:两个切换按钮的控件
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月16日下午2:57:07
 * @version 1.0
 */
@SuppressLint("NewApi")
public class TwoSwitchBtnView extends RelativeLayout implements android.view.View.OnClickListener {
	private TextView mOneText;
	private TextView mTwoText;
	private ImageView mCursorImgLeft;
	private ImageView mCursorImgRight;
	private OnTwoSwitchBtnClickListener onTwoSwitchBtnClickListener;
	private int textChooseColor = getResources().getColor(R.color.color_app_theme_orange_bg);

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	public TwoSwitchBtnView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initViews(context);
	}

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public TwoSwitchBtnView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initViews(context);
	}

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 */
	public TwoSwitchBtnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initViews(context);
	}

	/**
	 * @描述
	 * @param context
	 */
	public TwoSwitchBtnView(Context context) {
		super(context);
		initViews(context);
	}

	/**
	 * @return the onTwoSwitchBtnClickListener
	 */
	public OnTwoSwitchBtnClickListener getOnTwoSwitchBtnClickListener() {
		return onTwoSwitchBtnClickListener;
	}

	/**
	 * @param onTwoSwitchBtnClickListener
	 *            the onTwoSwitchBtnClickListener to set
	 */
	public void setOnTwoSwitchBtnClickListener(OnTwoSwitchBtnClickListener onTwoSwitchBtnClickListener) {
		this.onTwoSwitchBtnClickListener = onTwoSwitchBtnClickListener;
	}

	private void initViews(Context context) {
		LayoutInflater.from(context).inflate(R.layout.view_two_switch_btn, this);
		mOneText = (TextView) findViewById(R.id.mOneText);
		mTwoText = (TextView) findViewById(R.id.mTwoText);
		mCursorImgLeft = (ImageView) findViewById(R.id.mCursorImgLeft);
		mCursorImgRight = (ImageView) findViewById(R.id.mCursorImgRight);
		mOneText.setOnClickListener(this);
		mTwoText.setOnClickListener(this);
		switchToBtn(0);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mOneText:
			switchToBtn(0);
			if (onTwoSwitchBtnClickListener != null) {
				onTwoSwitchBtnClickListener.clickLeftBtn();
			}
			break;
		case R.id.mTwoText:
			switchToBtn(1);
			if (onTwoSwitchBtnClickListener != null) {
				onTwoSwitchBtnClickListener.clickRightBtn();
			}
			break;

		default:
			break;
		}
	}

	private void switchToBtn(int currentTab) {
		switch (currentTab) {
		case 0:
			mOneText.setTextColor(textChooseColor);
			mTwoText.setTextColor(getContext().getResources().getColor(R.color.color_555555));

			mCursorImgLeft.setVisibility(View.VISIBLE);
			mCursorImgRight.setVisibility(View.INVISIBLE);
			break;
		case 1:
			mOneText.setTextColor(getContext().getResources().getColor(R.color.color_555555));
			mTwoText.setTextColor(textChooseColor);

			mCursorImgLeft.setVisibility(View.INVISIBLE);
			mCursorImgRight.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}

	public void setLeftBtnText(String text) {
		mOneText.setText(text);
	}

	public void setRightBtnText(String text) {
		mTwoText.setText(text);
	}

	public void setCursorColor(int color) {
		mCursorImgLeft.setBackgroundColor(color);
		mCursorImgRight.setBackgroundColor(color);
	}

	public void setChooseTextColor(int color) {
		this.textChooseColor = color;
	}

	public interface OnTwoSwitchBtnClickListener {
		void clickLeftBtn();

		void clickRightBtn();
	}
}
