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
package com.ysp.houge.popwindow;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.AreaEntity;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月5日下午8:26:55
 * @version 1.0
 */
public class JobAreaListPopupWindow extends
		ListViewWithAnimTopPopupWindow<AreaEntity> {
	protected TranslateAnimation animation;
	private OnClickJobAreaItemListener listener;
	public JobAreaListPopupWindow(Context context,
			List<AreaEntity> areaEntities, OnClickJobAreaItemListener listener) {
		super(context);
		this.listener = listener;
		this.mDatas.addAll(areaEntities);
		initViewData();
	}

	@Override
	public void initViewData() {
		mAdapter = new ListAdapter(mDatas);
		mPopupListView.setAdapter(mAdapter);
		Animation animation = AnimationUtils.loadAnimation(context,
				R.anim.popup_top_in);
		mPopupListView.setAnimation(animation);
		mPopupListView.startAnimation(animation);
		// animation = new TranslateAnimation(0, 0, 0, 0);
		// animation.setDuration(500);
		// contentView.setAnimation(animation);
		// contentView.startAnimation(animation);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

	}

	@Override
	public View setDataAtPositon(final int position, View convertView,
			ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		final AreaEntity AreaEntity = mDatas.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_popup_job_area_type, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mItemName.setText(AreaEntity.getAreaName());
		if (AreaEntity.isChoosed()) {
			viewHolder.mItemChoosed.setVisibility(View.VISIBLE);
		} else {
			viewHolder.mItemChoosed.setVisibility(View.GONE);
		}
		viewHolder.mItemLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				if (AreaEntity.isChoosed()) {
					if (listener != null) {
						listener.clickItem(position, AreaEntity);
					}
				} else {
					AreaEntity AreaEntity1;
					for (int i = 0, count = mDatas.size(); i < count; i++) {
						AreaEntity1 = mDatas.get(i);
						AreaEntity1.setChoosed(false);
					}
					AreaEntity.setChoosed(true);
					mAdapter.notifyDataSetChanged();
					if (listener != null) {
						listener.clickItem(position, AreaEntity);
					}
				}
			}
		});
		return convertView;
	}

	public interface OnClickJobAreaItemListener {
		void clickItem(int position, AreaEntity areaEntity);
	}

	class ViewHolder {
		TextView mItemName;
		ImageView mItemChoosed;
		RelativeLayout mItemLayout;

		public ViewHolder(View convertView) {
			mItemName = (TextView) convertView.findViewById(R.id.mItemName);
			mItemChoosed = (ImageView) convertView
					.findViewById(R.id.mItemChoosed);
			mItemLayout = (RelativeLayout) convertView
					.findViewById(R.id.mItemLayout);
		}
	}

}
