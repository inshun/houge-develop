package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ysp.houge.R;
import com.ysp.houge.lisenter.OnMapHaveATalkClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.utility.DateUtil;

import java.util.List;

@SuppressLint("InflateParams")
public class MapNeedAdapter extends BaseAdapter {
	private OnMapHaveATalkClickListener listener;
	private List<GoodsDetailEntity> list;
	private Context context;

	public MapNeedAdapter(OnMapHaveATalkClickListener listener, List<GoodsDetailEntity> list, Context context) {
		super();
		this.listener = listener;
		this.list = list;
		this.context = context;
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
		return list.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		// 获得操作对象
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_map_need, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final GoodsDetailEntity detailEntity = list.get(position);
		if (holder != null && null != detailEntity) {
			// 标题
			if (TextUtils.isEmpty(detailEntity.title)) {
				holder.title.setText("需要 · 未知名称");
			} else {
				holder.title.setText("需要 · " + detailEntity.title);
			}

			// 需求时间(这个地方如果为空就new一个当前时间出来)
			String date = null == detailEntity.start_time ? DateUtil.getCurrentDateTime() : detailEntity.start_time;
			StringBuilder sb = new StringBuilder();
			sb.append("时间 · ");
			// 星期几
			switch (DateUtil.getDiffDays(DateUtil.getCurrentDate(), date)) {
			case 0:
				sb.append("今天");
				break;
			case 1:
				sb.append("明天");
				break;
			case 2:
				sb.append("后天");
				break;
			default:
				sb.append(DateUtil.WEEKS[DateUtil.getDayOfWeek(date.substring(0, 10)) - 1]);
				break;
			}

			// 几点
			sb.append(" ");
			sb.append(date.subSequence(date.indexOf(" "), date.length() - 3));
			sb.append(" ");
			sb.append("开始");

			holder.time.setText(sb.toString());

			// 价格
			holder.price.setText("报酬 · ￥" + detailEntity.price);

			// 聊一聊
			holder.haveATalk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					listener.onHaveATalkClick(detailEntity);
				}
			});
		}
		return convertView;
	}

	@SuppressLint("CutPasteId")
	class Holder {
		TextView title;
		TextView time;
		TextView price;
		TextView haveATalk;

		public Holder(View convertView) {
			title = (TextView) convertView.findViewById(R.id.tv_title);
			time = (TextView) convertView.findViewById(R.id.tv_time);
			price = (TextView) convertView.findViewById(R.id.tv_price);
			haveATalk = (TextView) convertView.findViewById(R.id.tv_have_a_talk);
		}
	}
}
