package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.BalanceEntity;

import java.util.ArrayList;
import java.util.List;

@SuppressLint({ "ViewHolder", "InflateParams" })
public class BalanceAdapter extends BaseAdapter implements IDataAdapter<List<BalanceEntity>> {
	private Context context;
    private int color;
	private List<BalanceEntity> list = new ArrayList<BalanceEntity>();

	public BalanceAdapter(Context context) {
		super();
		this.context = context;
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_BUYER:
                color = context.getResources().getColor(R.color.color_app_theme_orange_bg);
                break;
            case MyApplication.LOG_STATUS_SELLER:
                color = context.getResources().getColor(R.color.color_app_theme_blue_bg);
                break;
        }
	}

	@Override
	public int getCount() {
		return list.size() == 0 ? 1 : list.size();
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
			convertView = (View) LayoutInflater.from(context).inflate(R.layout.item_balance, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (list.size() == 0) {
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
			convertView.setLayoutParams(params);
		}
		
		if (position==0&&list.size()!=0) {
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			convertView.setLayoutParams(params);
		}

		if (list.size() > position) {
			BalanceEntity entity = list.get(position);
			if (holder != null && entity != null) {
				holder.price.setText(String.valueOf(entity.getPrice()));
                holder.price.setTextColor(color);
				if (TextUtils.isEmpty(entity.getExplain())) {
					holder.explain.setText("");
				} else {
					holder.explain.setText(entity.getExplain());
				}

				if (TextUtils.isEmpty(entity.getGood_name())) {
					holder.good_name.setText("");
				} else {
					holder.good_name.setText(entity.getGood_name());
				}

				if (TextUtils.isEmpty(entity.getAction_time())) {
					holder.action_time.setText("");
				} else {
					holder.action_time.setText(String.valueOf(entity.getAction_time()));
				}

				if (!TextUtils.isEmpty(entity.getOrder_id()) && (entity.getPrice()) > 0 ) {
						holder.tv_recommended_commission.setVisibility(View.VISIBLE);
						if(!TextUtils.isEmpty(entity.getCommission())){
							holder.commission.setText(entity.getCommission());
						}

				}else{
					holder.tv_recommended_commission.setVisibility(View.GONE);
					holder.commission.setVisibility(View.GONE);

				}

				if (TextUtils.isEmpty(entity.getOrder_id())) {
					holder.order_id.setText("");
				} else {
					holder.order_id.setText((String.valueOf(entity.getOrder_id())));
				}

				// 详情部分处理
				holder.details.removeAllViews();
				if (entity.getSub_detail() != null && entity.getSub_detail().size() > 0) {
					for (int i = 0; i < entity.getSub_detail().size(); i++) {
						BalanceEntity.Detais detais = entity.getSub_detail().get(i);
						if (detais != null) {
							View view = LayoutInflater.from(context).inflate(R.layout.view_balance_details,
									holder.details);

							TextView type = (TextView) view.findViewById(R.id.tv_balance_detail_type);
							TextView explain = (TextView) view.findViewById(R.id.tv_balance_detail_explain);
							TextView price = (TextView) view.findViewById(R.id.tv_balance_detail_price);

							int count = 0;
							if (!TextUtils.isEmpty(detais.type)) {
								type.setText(detais.type);
							} else {
								count++;
							}

							if (!TextUtils.isEmpty(detais.explain)) {
								explain.setText(detais.explain);
							} else {
								count++;
							}

							if (!TextUtils.isEmpty(detais.price)) {
								price.setText(detais.price);
							} else {
								count++;
							}

							if (count == 3) {
								holder.details.removeView(view);
							}
						}
					}
				} else {
					holder.details.setVisibility(View.GONE);
				}
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<BalanceEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
			this.list = data;
		} else {
			this.list.addAll(data);
		}
	}

	@Override
	public List<BalanceEntity> getData() {
		return this.list;
	}



	class Holder {
		TextView price;
		TextView explain;
		TextView good_name;
		TextView commission;
		LinearLayout details;

		TextView action_time;
		TextView order_id;
		TextView tv_recommended_commission;

		public Holder(View convertView) {
			price = (TextView) convertView.findViewById(R.id.tv_balance_price);
			explain = (TextView) convertView.findViewById(R.id.tv_example);
			good_name = (TextView) convertView.findViewById(R.id.tv_good_name);

			details = (LinearLayout) convertView.findViewById(R.id.line_balance_detail);

			action_time = (TextView) convertView.findViewById(R.id.tv_action_time);
			order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
			tv_recommended_commission = (TextView) convertView.findViewById(R.id.tv_recommended_commission);
			commission = (TextView) convertView.findViewById(R.id.commission);
		}

	}

}
