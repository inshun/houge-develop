package com.ysp.houge.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.ProgressTitleDescriptor;

public class TopTitleProgressView extends RelativeLayout implements
		OnClickListener {
	private LinearLayout backButton;
	private ImageView rightButton;
	private TextView topTitleTextView;
	private ProgressBar bar;
	private ProgressTitleDescriptor descriptor;
	private OnTopTitleClickListener listener;
	private TextView mTopLeftBtn;
	public TopTitleProgressView(Context context) {
		super(context);
		initializeView(context);
	}
	public TopTitleProgressView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initializeView(context);
	}

	public TopTitleProgressView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeView(context);
	}

	private void initializeView(Context context) {
		LayoutInflater.from(context).inflate(
				R.layout.include_toptitle_withprogress, this);
		backButton = (LinearLayout) findViewById(R.id.public_toptitle_leftbutton);
		mTopLeftBtn = (TextView) findViewById(R.id.mTopLeftBtn);
		rightButton = (ImageView) findViewById(R.id.public_toptitle_rightbutton);
		topTitleTextView = (TextView) findViewById(R.id.public_toptitle_centertext);
		bar = (ProgressBar) findViewById(R.id.public_toptitle_progress);
	}

	public void initializeData(ProgressTitleDescriptor descriptor,
			OnTopTitleClickListener listener) {
		this.descriptor = descriptor;
		this.listener = listener;
		notifyDataChanged();
	}

	public void notifyDataChanged() {
		if (!TextUtils.isEmpty(descriptor.getCenterLable())) {
			topTitleTextView.setText(descriptor.getCenterLable());
		}
		if (!TextUtils.isEmpty(descriptor.getLeftLable())) {
			mTopLeftBtn.setText(descriptor.getLeftLable());
			backButton.setVisibility(View.VISIBLE);
			backButton.setOnClickListener(this);
		} else {
			backButton.setVisibility(View.GONE);
		}
		if (descriptor.getImageId() != 0) {
			rightButton.setImageResource(descriptor.getImageId());
		}
		rightButton.setOnClickListener(this);
	}

	public void hideProgressBar() {
		bar.setVisibility(View.GONE);
		rightButton.setVisibility(View.VISIBLE);
	}

	public void showProgressBar() {
		bar.setVisibility(View.VISIBLE);
		rightButton.setVisibility(View.INVISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.public_toptitle_leftbutton:
			this.listener.onTopTitleClick(TopTitleClickType.LEFT);
			break;
		case R.id.public_toptitle_rightbutton:
			this.listener.onTopTitleClick(TopTitleClickType.RIGHT);
			break;
		default:
			break;
		}
	}

	public enum TopTitleClickType {
		LEFT, RIGHT, Center
	}

	public interface OnTopTitleClickListener {
		void onTopTitleClick(TopTitleClickType clickType);
	}
}
