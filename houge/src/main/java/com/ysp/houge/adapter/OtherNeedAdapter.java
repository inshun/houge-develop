package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.utility.DateUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class OtherNeedAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>> {
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
	private LayoutInflater mInflater;

	public OtherNeedAdapter(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
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
		return list.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_other_need, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		if (list.size() == 0) {
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
			convertView.setLayoutParams(params);
		}

		if (position == 0 && list.size() != 0) {
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			convertView.setLayoutParams(params);
		}

		if (holder != null && list.size() > position) {
			GoodsDetailEntity detailEntity = list.get(position);
			if (null != detailEntity) {

				// 标题
				switch (MyApplication.getInstance().getLoginStaus()){
					case  MyApplication.LOG_STATUS_BUYER:
						holder.title.setText("我能 · " + detailEntity.title);
						break;
					case  MyApplication.LOG_STATUS_SELLER:
						holder.title.setText("需要 · " + detailEntity.title);
						break;
				}

				// 时间
				try {
					// 需求时间
					String date = detailEntity.start_time;
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


                    if (TextUtils.isEmpty(date))
                        holder.time.setText("时间字段未返回");
                    else
					    holder.time.setText(sb.toString());
				} catch (Exception e) {
					holder.time.setText("时间 · 未知时间");
				}
				
				// 当面付图标
//				switch (new Random().nextInt(2)) {
//				case 0:
//					holder.faceToFace.setVisibility(View.VISIBLE);
//					break;
//				default:
//					holder.faceToFace.setVisibility(View.GONE);
//					break;
//				}

				// 价格
				holder.price.setText("￥" + detailEntity.price + "元");
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<GoodsDetailEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);
	}

	@Override
	public List<GoodsDetailEntity> getData() {
		// TODO Auto-generated method stub
		return list;
	}

	class Holder {
		TextView title;
		TextView time;
		ImageView faceToFace;
		TextView price;

		public Holder(View convertView) {
			title = (TextView) convertView.findViewById(R.id.tv_title);
			time = (TextView) convertView.findViewById(R.id.tv_time);
			faceToFace = (ImageView) convertView.findViewById(R.id.iv_face_to_face);
			price = (TextView) convertView.findViewById(R.id.tv_price);
		}
	}

}
