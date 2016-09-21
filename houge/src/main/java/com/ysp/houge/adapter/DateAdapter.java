package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.DateTimeEntity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class DateAdapter extends BaseAdapter {
	private int srceenWidth;
	private Context context;
	private List<DateTimeEntity> list;

	private int index = 0;

	public DateAdapter(int srceenWidth, Context context, List<DateTimeEntity> list) {
		super();
		this.srceenWidth = srceenWidth;
		this.context = context;
		this.list = list;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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

	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_service_date, null);
			LayoutParams params = new ViewGroup.LayoutParams(srceenWidth / 4, LayoutParams.MATCH_PARENT);
			convertView.setLayoutParams(params);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.week.setText(list.get(position).week);
		viewHolder.date.setText(list.get(position).date);
		if (index == position) {
			convertView.setBackgroundColor(Color.parseColor("#FE6A00"));
		}else {
			
			convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shapa_square_border_orange_padding_white));
		}
		
		return convertView;
	}

	class ViewHolder {
		TextView week;
		TextView date;

		public ViewHolder(View convertView) {
			week = (TextView) convertView.findViewById(R.id.tv_week);
			date = (TextView) convertView.findViewById(R.id.tv_date);
		}
	}

}