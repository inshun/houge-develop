package com.ysp.houge.adapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.db.ItemPartTimeJobEntity;

public class PartJobAdatper extends BaseAdapter implements
		IDataAdapter<List<ItemPartTimeJobEntity>> {
	private List<ItemPartTimeJobEntity> itemPartJobEntities = new ArrayList<ItemPartTimeJobEntity>();
	private LayoutInflater mInflater;

	public PartJobAdatper(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itemPartJobEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return itemPartJobEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		ItemPartTimeJobEntity itemPartJobEntity = itemPartJobEntities
				.get(position);
		if (convertView == null) {
			convertView = (View) mInflater
					.inflate(R.layout.item_part_job, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			holder.mPartJobMoney.setText(itemPartJobEntity.price + "/"
					+ itemPartJobEntity.priceUnit);
			if (TextUtils.isDigitsOnly(itemPartJobEntity.range)) {
				if (Long.parseLong(itemPartJobEntity.range) >= 0
						&& Long.parseLong(itemPartJobEntity.range) < 1000) {
					holder.mPartJobDistance.setText("距您"
							+ itemPartJobEntity.range + "米");
				} else if (Long.parseLong(itemPartJobEntity.range) >= 1000) {
					float size = (float) Long
							.parseLong(itemPartJobEntity.range) / 1000;
					DecimalFormat df = new DecimalFormat("0.0");// 格式化小数，不足的补0
					String filesize = df.format(size);// 返回的是String类型的
					holder.mPartJobDistance.setText("距您" + filesize + "公里");
				}
			}
			holder.mPartJobTime.setText(itemPartJobEntity.time);
			holder.mPartJobName.setText(itemPartJobEntity.title);
		}
		return convertView;
	}

	@Override
	public void setData(List<ItemPartTimeJobEntity> data, boolean isRefresh) {
		if (isRefresh) {
			itemPartJobEntities.clear();
		}
		itemPartJobEntities.addAll(data);
	}

	@Override
	public List<ItemPartTimeJobEntity> getData() {
		return itemPartJobEntities;
	}

	class Holder {
		TextView mPartJobMoney;
		TextView mPartJobDistance;
		TextView mPartJobTime;
		TextView mPartJobName;

		public Holder(View convertView) {
			mPartJobMoney = (TextView) convertView
					.findViewById(R.id.mPartJobMoney);
			mPartJobDistance = (TextView) convertView
					.findViewById(R.id.mPartJobDistance);
			mPartJobTime = (TextView) convertView
					.findViewById(R.id.mPartJobTime);
			mPartJobName = (TextView) convertView
					.findViewById(R.id.mPartJobName);
		}

	}

}
