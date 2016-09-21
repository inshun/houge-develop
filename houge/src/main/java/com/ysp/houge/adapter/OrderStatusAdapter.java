package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.utility.DateUtil;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class OrderStatusAdapter extends BaseAdapter implements IDataAdapter<List<String>> {
	private List<String> list = new ArrayList<String>();
	private LayoutInflater mInflater;

	public OrderStatusAdapter(Context context) {
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
			convertView = (View) mInflater.inflate(R.layout.item_order_status, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			// 最后一个时候下方的线条隐藏
			if (list.size() - 1 == position) {
				holder.line.setVisibility(View.GONE);
			} else {
				holder.line.setVisibility(View.VISIBLE);
			}

			if (TextUtils.isEmpty(list.get(position))) {
				holder.statusMsg.setText("异常状态");
			} else {
				holder.statusMsg.setText(list.get(position));
			}

			if (TextUtils.isEmpty(list.get(position))) {
				holder.time.setText(DateUtil.getCurrentDateTime());
			} else {
				holder.time.setText(list.get(position));
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<String> data, boolean isRefresh) {
		if (isRefresh) {
			list = data;
		} else {
			list.addAll(data);
		}
	}

	@Override
	public List<String> getData() {
		return list;
	}

	class Holder {
		View line;
		TextView statusMsg;
		TextView time;

		public Holder(View convertView) {
			line = convertView.findViewById(R.id.v_buttom_line);
			statusMsg = (TextView) convertView.findViewById(R.id.tv_status_txt);
			time = (TextView) convertView.findViewById(R.id.tv_time);
		}

	}

}
