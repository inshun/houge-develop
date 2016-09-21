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
import android.text.TextUtils;
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
import com.ysp.houge.model.entity.bean.SalaryTypeEntity;

/**
 * @描述:报酬选择popupwindow
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月5日下午8:27:49
 * @version 1.0
 */
public class SalaryTypeListPopupWindow extends
		ListViewWithAnimTopPopupWindow<SalaryTypeEntity> {
	protected TranslateAnimation animation;
	private OnClickSalaryItemListener listener;
	public SalaryTypeListPopupWindow(Context context,
			List<SalaryTypeEntity> salaryTypeEntities,
			OnClickSalaryItemListener listener) {
		super(context);
		this.listener = listener;
		this.mDatas.addAll(salaryTypeEntities);
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
		final SalaryTypeEntity salaryTypeEntity = mDatas.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_popup_view_type, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (!TextUtils.isEmpty(salaryTypeEntity.getSalaryTypeName())) {
			viewHolder.mItemName.setText(salaryTypeEntity.getSalaryTypeName());
		} else if (salaryTypeEntity.getSalaryTypeNameResId() != 0) {
			viewHolder.mItemName.setText(context.getString(salaryTypeEntity
					.getSalaryTypeNameResId()));
		} else {
			viewHolder.mItemName.setText("");
		}
		if (salaryTypeEntity.isChoosed()) {
			viewHolder.mItemChoosed.setVisibility(View.VISIBLE);
		} else {
			viewHolder.mItemChoosed.setVisibility(View.GONE);
		}
		viewHolder.mItemLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				if (salaryTypeEntity.isChoosed()) {
					if (listener != null) {
						listener.clickItem(position, salaryTypeEntity);
					}
				} else {
					SalaryTypeEntity salaryTypeEntity1;
					for (int i = 0, count = mDatas.size(); i < count; i++) {
						salaryTypeEntity1 = mDatas.get(i);
						salaryTypeEntity1.setChoosed(false);
					}
					salaryTypeEntity.setChoosed(true);
					mAdapter.notifyDataSetChanged();
					if (listener != null) {
						listener.clickItem(position, salaryTypeEntity);
					}
				}
			}
		});
		return convertView;
	}

	public interface OnClickSalaryItemListener {
		void clickItem(int position, SalaryTypeEntity salaryTypeEntity);
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
