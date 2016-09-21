package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.db.SearchHistoryEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class SearchHistoryAdapter extends BaseAdapter implements IDataAdapter<List<SearchHistoryEntity>> {
	private LayoutInflater mInflater;
	private List<SearchHistoryEntity> list = new ArrayList<SearchHistoryEntity>();

	public SearchHistoryAdapter(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		// 获得操作对象
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_search_history, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		SearchHistoryEntity entity = list.get(position);
		if (holder != null && entity != null) {
			if (!TextUtils.isEmpty(entity.text)) {
				holder.text.setText(entity.text);
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<SearchHistoryEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list = data;
		} else {
			list.addAll(data);
		}
	}

	@Override
	public List<SearchHistoryEntity> getData() {
		return list;
	}

	class Holder {
		TextView text;

		public Holder(View convertView) {
			text = (TextView) convertView.findViewById(R.id.tv_text);
		}

	}

}
