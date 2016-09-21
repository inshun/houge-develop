package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.util.LogTime;
import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.utility.LogUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class WorkTypeAdapter extends BaseAdapter implements IDataAdapter<List<WorkTypeEntity>> {
	private List<WorkTypeEntity> entities = new ArrayList<WorkTypeEntity>();
	private LayoutInflater mInflater;

	public WorkTypeAdapter(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return entities.size();
	}

	@Override
	public Object getItem(int position) {
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_recom_setting_work_type, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			holder.mWorkType.setText(entities.get(position).getName());
		}
		return convertView;
	}

	@Override
	public void setData(List<WorkTypeEntity> list, boolean isRefresh) {
		this.entities = list;
	}

	@Override
	public List<WorkTypeEntity> getData() {
		return entities;
	}

	class Holder {
		TextView mWorkType;

		public Holder(View convertView) {
			mWorkType = (TextView) convertView.findViewById(R.id.tv_recom_setting_work_type);
		}

	}

}
