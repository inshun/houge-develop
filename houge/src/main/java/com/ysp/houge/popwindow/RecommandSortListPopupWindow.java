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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.SortTypeEntity;

import java.util.List;

/**
 * @描述:排序选择popupwindow
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月5日下午8:27:49
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class RecommandSortListPopupWindow extends ListViewWithAnimTopPopupWindow<SortTypeEntity> {
	private OnClickItemListener listener;

	public RecommandSortListPopupWindow(Context context, List<SortTypeEntity> entities) {
		super(context, LayoutInflater.from(context).inflate(R.layout.popup_list_top, null));
		this.mDatas.addAll(entities);
		setBackgroundDrawable(new ColorDrawable(0xe0000000));
		initViewData();
	}

	public void setListener(OnClickItemListener listener) {
		this.listener = listener;
	}

	@Override
	public void initViewData() {
		contentView.findViewById(R.id.rela_pick_up).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		mAdapter = new ListAdapter(mDatas);
		mPopupListView.setAdapter(mAdapter);
	}

	@Override
	public View setDataAtPositon(final int position, View convertView, ViewGroup viewGroup) {
		ViewHolder viewHolder = null;
		final SortTypeEntity entity = mDatas.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_popup_recommend_sort, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mSortStr.setText(entity.sortStr);
		viewHolder.mSortItemLayout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				listener.clickItem(position, entity);
			}
		});
		return convertView;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

	}

	public interface OnClickItemListener {
		void clickItem(int position, SortTypeEntity entity);
	}

	class ViewHolder {
		TextView mSortStr;
		LinearLayout mSortItemLayout;
		public ViewHolder(View convertView) {
			mSortStr = (TextView) convertView.findViewById(R.id.tv_recommend_sort_txt);
			mSortItemLayout = (LinearLayout) convertView.findViewById(R.id.lien_sort_item_layout);
		}
	}

}
