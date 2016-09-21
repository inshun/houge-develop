package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.SchoolEntity;

/**
 * @描述:学校的适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月13日下午2:35:30
 * @version 1.0
 */
public class SchoolAdapter extends BaseAdapter implements SectionIndexer,
		IDataAdapter<List<SchoolEntity>> {
	private List<SchoolEntity> list = new ArrayList<SchoolEntity>();
	private Context mContext;

	public SchoolAdapter(Context mContext) {
		this.mContext = mContext;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		View currentFocus = ((Activity) mContext).getCurrentFocus();
		if (currentFocus != null) {
			currentFocus.clearFocus();
		}
		ViewHolder viewHolder = null;
		final SchoolEntity schoolEntity = list.get(position);
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(
					R.layout.item_choose_school, null);
			viewHolder = new ViewHolder(view);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);
		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tv_sortname.setVisibility(View.VISIBLE);
			viewHolder.tv_sortname.setText(schoolEntity.getSortLetter()
					.toUpperCase().charAt(0)
					+ "");
		} else {
			viewHolder.tv_sortname.setVisibility(View.GONE);
		}
		viewHolder.tv_schoolName.setText(schoolEntity.getName());
		return view;

	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetter().toUpperCase().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetter();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	@Override
	public void setData(List<SchoolEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);
		notifyDataSetChanged();
	}

	@Override
	public List<SchoolEntity> getData() {
		return list;
	}

	class ViewHolder {
		public TextView tv_sortname;
		public TextView tv_schoolName;

		public ViewHolder(View view) {
			tv_sortname = (TextView) view.findViewById(R.id.tv_sortname);
			tv_schoolName = (TextView) view.findViewById(R.id.tv_schoolName);
		}
	}
}