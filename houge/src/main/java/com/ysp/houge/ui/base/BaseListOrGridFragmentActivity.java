package com.ysp.houge.ui.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月13日下午3:18:04
 * @version 1.0
 * @param <T>
 */
public abstract class BaseListOrGridFragmentActivity<T> extends
		BaseFragmentActivity {
	protected ListAdapter mAdapter;
	protected List<T> mDatas = new ArrayList<T>();

	protected LayoutInflater mInflater;

	@Override
	protected void setContentView() {
		mInflater = LayoutInflater.from(this);
	}

	/**
	 * @param position
	 * @param convertView
	 * @param viewGroup
	 */
	public abstract View setDataAtPositon(int position, View convertView,
			ViewGroup viewGroup);

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
