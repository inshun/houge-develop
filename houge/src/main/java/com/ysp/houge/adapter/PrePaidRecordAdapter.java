package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.db.ItemPrePaidRecordEntity;

/**
 * @描述:预支纪录列表适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月12日上午10:59:27
 * @version 1.0
 */
public class PrePaidRecordAdapter extends BaseAdapter implements
		IDataAdapter<List<ItemPrePaidRecordEntity>> {
	private List<ItemPrePaidRecordEntity> prePaidRecordEntitites = new ArrayList<ItemPrePaidRecordEntity>();
	private LayoutInflater mInflater;
	private Context mContext;

	public PrePaidRecordAdapter(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
	}

	@Override
	public int getCount() {
		return prePaidRecordEntitites.size();
	}

	@Override
	public Object getItem(int position) {
		return prePaidRecordEntitites.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		ItemPrePaidRecordEntity prePaidRecordEntity = prePaidRecordEntitites
				.get(position);
		if (convertView == null) {
			convertView = (View) mInflater.inflate(
					R.layout.item_prepaid_record, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			switch (prePaidRecordEntity.getState()) {
			case 0:
				holder.iv_state.setVisibility(View.VISIBLE);
				holder.iv_state.setImageResource(R.drawable.examine_success);
				holder.tv_state.setText(mContext
						.getString(R.string.examine_successed));
				holder.tv_suggest.setText(mContext
						.getString(R.string.examine_success_tip));
				break;
			case 1:
				holder.iv_state.setVisibility(View.VISIBLE);
				holder.iv_state.setImageResource(R.drawable.examine_error);
				holder.tv_state.setText(mContext
						.getString(R.string.examine_error));
				holder.tv_suggest.setText(mContext
						.getString(R.string.examine_error_tip));
				break;
			case 2:
				holder.iv_state.setVisibility(View.VISIBLE);
				holder.iv_state.setImageResource(R.drawable.examining);
				holder.tv_state.setText(mContext.getString(R.string.examining));
				holder.tv_suggest.setText(mContext
						.getString(R.string.examine_error_tip));
				break;
			default:
				holder.iv_state.setVisibility(View.GONE);
				holder.tv_state.setText(mContext
						.getString(R.string.please_update_app));
				holder.tv_suggest.setText(mContext
						.getString(R.string.please_update_app));
				break;
			}
			holder.tv_num.setText(mContext.getString(R.string.apply_money)
					+ prePaidRecordEntity.getMoney());
			holder.tv_time.setText(mContext.getString(R.string.apply_time)
					+ prePaidRecordEntity.getCreated());
		}
		return convertView;
	}

	@Override
	public void setData(List<ItemPrePaidRecordEntity> data, boolean isRefresh) {
		if (isRefresh) {
			prePaidRecordEntitites.clear();
		}
		prePaidRecordEntitites.addAll(data);

	}

	@Override
	public List<ItemPrePaidRecordEntity> getData() {
		return prePaidRecordEntitites;
	}

	class Holder {
		ImageView iv_state;
		TextView tv_state;
		TextView tv_suggest;
		TextView tv_num;
		TextView tv_time;

		public Holder(View convertView) {
			iv_state = (ImageView) convertView.findViewById(R.id.iv_state);
			tv_state = (TextView) convertView.findViewById(R.id.tv_state);
			tv_suggest = (TextView) convertView.findViewById(R.id.tv_suggest);
			tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			tv_time = (TextView) convertView.findViewById(R.id.tv_time);
		}

	}

}
