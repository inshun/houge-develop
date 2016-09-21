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

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ysp.houge.R;

/**
 * @描述: 有列表的基础popupWindow
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月5日下午8:27:33
 * @version 1.0
 */
@SuppressLint("InflateParams")
public abstract class ListViewWithAnimTopPopupWindow<T> extends BasePopupWindow
		implements OnItemClickListener {
	protected Context context;
	protected ListView mPopupListView;
	protected ListAdapter mAdapter;
	protected List<T> mDatas = new ArrayList<T>();
	private RelativeLayout mPopupListLayout;

	public ListViewWithAnimTopPopupWindow(Context context) {
		super(LayoutInflater.from(context).inflate(R.layout.popup_list_top_tanslusent,
				null), LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.context = context;

	}

	public ListViewWithAnimTopPopupWindow(Context context, View view) {
		super(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		this.context = context;

	}

	@Override
	public void initViews() {
		mPopupListView = (ListView) contentView
				.findViewById(R.id.mPopupListView);
		mPopupListLayout = (RelativeLayout) contentView
				.findViewById(R.id.mPopupListLayout);
	}

	@Override
	public void initEvents() {
		mPopupListView.setOnItemClickListener(this);
		mPopupListLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
			}
		});
	}

	@Override
	public void initData() {
	}

	/**
	 * 由子类重写，实现子类的列表布局
	 *
	 * @param position
	 * @param contentView
	 * @param viewGroup
	 */
	public abstract View setDataAtPositon(int position, View convertView,
			ViewGroup viewGroup);

	public abstract void initViewData();

	public class ListAdapter extends BaseAdapter {
		private List<T> mDatas;

		public ListAdapter(List<T> mDatas) {
			this.mDatas = mDatas;
		}

		@Override
		public int getCount() {
			return this.mDatas != null ? this.mDatas.size() : 0;
		}

		@Override
		public Object getItem(int arg0) {
			return this.mDatas.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup viewGroup) {
			return setDataAtPositon(position, convertView, viewGroup);
		}

	}

}
