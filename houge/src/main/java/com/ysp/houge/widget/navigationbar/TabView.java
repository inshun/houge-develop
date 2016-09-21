package com.ysp.houge.widget.navigationbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.TabViewDescriptor;

/**
 * @描述:切换栏子项
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月5日下午5:51:29
 * @version 1.0
 */
public class TabView extends RelativeLayout {

	private ImageView mTabsImg;
	private TextView mTabsLabel;
	private TextView mTabsUnreadCount;
	private ImageView mTabsNewRemind;

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 * @param defStyleRes
	 */
	@SuppressLint("NewApi")
	public TabView(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		initView(context);
	}

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context);
	}

	/**
	 * @描述
	 * @param context
	 * @param attrs
	 */
	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * @描述
	 * @param context
	 */
	public TabView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater.from(context).inflate(R.layout.view_tab, this);
		mTabsImg = (ImageView) findViewById(R.id.mTabsImg);
		mTabsLabel = (TextView) findViewById(R.id.mTabsLabel);
		mTabsUnreadCount = (TextView) findViewById(R.id.mTabsUnreadCount);
		mTabsNewRemind = (ImageView) findViewById(R.id.mTabsNewRemind);
	}

	public void initData(TabViewDescriptor tabViewDescriptor) {
		mTabsImg.setImageResource(tabViewDescriptor.resTabImgId);
		mTabsLabel.setText(tabViewDescriptor.tabName);
		if (tabViewDescriptor.tabNameColor != 0) {
			mTabsLabel.setTextColor(getContext().getResources().getColor(
					tabViewDescriptor.tabNameColor));
		}
		switch (tabViewDescriptor.showFirstType) {
		case ShowUnreadCountFirst:
			if (tabViewDescriptor.unreadCount > 0) {
				mTabsUnreadCount.setVisibility(View.VISIBLE);
				mTabsNewRemind.setVisibility(View.GONE);
				if (tabViewDescriptor.unreadCount > 99) {
					mTabsUnreadCount.setText("99+");
				} else {
					mTabsUnreadCount
							.setText(tabViewDescriptor.unreadCount + "");
				}
			} else {
				mTabsUnreadCount.setVisibility(View.GONE);
				if (tabViewDescriptor.showRedCount) {
					mTabsNewRemind.setVisibility(View.VISIBLE);
				} else {
					mTabsNewRemind.setVisibility(View.GONE);
				}
			}
			break;
		case ShowRedPointFirst:
			if (tabViewDescriptor.showRedCount) {
				mTabsNewRemind.setVisibility(View.VISIBLE);
				mTabsUnreadCount.setVisibility(View.GONE);
			} else {
				mTabsNewRemind.setVisibility(View.GONE);
				if (tabViewDescriptor.unreadCount > 0) {
					mTabsUnreadCount.setVisibility(View.VISIBLE);
					if (tabViewDescriptor.unreadCount > 99) {
						mTabsUnreadCount.setText("99+");
					} else {
						mTabsUnreadCount.setText(tabViewDescriptor.unreadCount
								+ "");
					}
				} else {
					mTabsUnreadCount.setVisibility(View.GONE);
				}
			}
			break;

		default:
			break;
		}
	}
}
