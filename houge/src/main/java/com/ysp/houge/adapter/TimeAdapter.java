package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint({ "InflateParams", "ClickableViewAccessibility" })
public class TimeAdapter extends BaseAdapter {
	private List<String> listTime;
	private Context context;
	private int index = 0;

	public TimeAdapter(List<String> listTime, Context context) {
		super();
		this.listTime = listTime;
		this.context = context;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int getCount() {
		return listTime.size();
	}

	@Override
	public Object getItem(int position) {
		return listTime.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_service_time, null);
			viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.time.setText(listTime.get(position));
		viewHolder.time.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					v.setBackgroundColor(Color.RED);
					break;
				case MotionEvent.ACTION_UP:
					v.setBackgroundColor(Color.WHITE);
					break;
				default:
					v.setBackgroundColor(Color.WHITE);
					break;
				}
				return false;
			}
		});

		if (index == position) {
			viewHolder.time.setBackgroundColor(Color.RED);
		} else {
			viewHolder.time.setBackgroundColor(Color.WHITE);
		}
		return convertView;
	}

	class ViewHolder {
		TextView time;

		public ViewHolder(View convertView) {
			time = (TextView) convertView.findViewById(R.id.tv_time);
		}
	}

}