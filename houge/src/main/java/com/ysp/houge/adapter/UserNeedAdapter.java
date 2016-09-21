package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.utility.DateUtil;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class UserNeedAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>>, OnClickListener {
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
	private OnItemClickListener listener;
	private LayoutInflater mInflater;
	@SuppressWarnings("unused")
	private Context context;

	public UserNeedAdapter(Context context, OnItemClickListener listener) {
		this.context = context;
		this.listener = listener;
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
		final Holder holder;
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_user_need, null);
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

		GoodsDetailEntity entity = null;
		if (list.size() > position) {
			entity = list.get(position);
		}
		if (null != holder && null != entity) {
			// 技能名称
			if (TextUtils.isEmpty(entity.title)) {
				holder.title.setText("需要·未知标题");
			} else {
				holder.title.setText("需要·" + entity.title);
			}

            try {
                // 需求时间(这个地方如果为空就new一个当前时间出来)
                String date = entity.start_time;
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

                if (TextUtils.isEmpty(date)){
                    holder.time.setText("时间 · 未知时间");
                }else {
                    holder.time.setText(sb.toString());
                }
            } catch (Exception e) {
                holder.time.setText("时间 · 未知时间");
            }

//			// 当面付图标
//			switch (new Random().nextInt(2)) {
//			case 0:
//				holder.faceToFace.setVisibility(View.GONE);
//				break;
//			default:
//				holder.faceToFace.setVisibility(View.VISIBLE);
//				break;
//			}

			// 价格
			holder.price.setText("￥" + entity.price);

			// 留言数量
			holder.loveCount.setText(String.valueOf(entity.comment_count));

			// 赞数量
			holder.commentCount.setText(String.valueOf(entity.view_count));


			final int index = position;
			holder.haveATalk.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK);
					}
				}
			});

			holder.share.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (null != listener) {
						listener.OnClick(index, OnItemClickListener.CLICK_OPERATION_SHARE);
					}
				}
			});
		}
		return convertView;
	}

	@Override
	public void onClick(View v) {
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
		return list;
	}

	class Holder {
		LinearLayout need;
		TextView title; // 标题
		TextView time;// 时间
//		ImageView faceToFace;// 当面付
		TextView price;// 技能图片
		TextView haveATalk; // 聊一聊
		ImageView share;// 分享
		TextView loveCount; // 喜欢的人
		TextView commentCount; // 评论

		public Holder(View convertView) {
			title = (TextView) convertView.findViewById(R.id.tv_title);
			time = (TextView) convertView.findViewById(R.id.tv_time);
//			faceToFace = (ImageView) convertView.findViewById(R.id.iv_face_to_face);
			price = (TextView) convertView.findViewById(R.id.tv_price);
			haveATalk = (TextView) convertView.findViewById(R.id.tv_have_a_talk);
			share = (ImageView) convertView.findViewById(R.id.iv_share);
			loveCount = (TextView) convertView.findViewById(R.id.tv_praise);
			commentCount = (TextView) convertView.findViewById(R.id.tv_level_msg);
		}
	}
}
