package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class SkilltAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>> {
	private Context context;
	private LayoutParams params;
	private OnMoreCliclk moreCliclk;
	private LayoutInflater mInflater;
	private List<GoodsDetailEntity> entities = new ArrayList<GoodsDetailEntity>();
	public SkilltAdapter(Context context) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		int imgSize = (SizeUtils.getScreenWidth((Activity) context) - SizeUtils.dip2px(context, 12) * 2
				- SizeUtils.dip2px(context, 8) * 2) / 3;
		params = new LayoutParams(imgSize, imgSize);
	}

	public void setMoreClicl(OnMoreCliclk moreClicl) {
		this.moreCliclk = moreClicl;
	}

	@Override
	public int getCount() {
        return entities.size() == 0 ? 1 : entities.size();
	}

	@Override
	public Object getItem(int position) {
		return entities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return entities.get(position).id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_skill, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

        if (entities.size() == 0) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 1);
            convertView.setLayoutParams(params);
        }

        if (position == 0 && entities.size() != 0) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
            convertView.setLayoutParams(params);
        }

		if (holder != null && entities.size() > position) {
		    final GoodsDetailEntity entity = entities.get(position);
			// 名字
			if (TextUtils.isEmpty(entity.title)) {
				holder.name.setText("未知");
			} else {
				holder.name.setText(entity.title);
			}

			// 拼接销售价格
			StringBuilder sb = new StringBuilder();
			sb.append("￥");
			sb.append(entity.price);
			sb.append("/");
			sb.append(entity.unit);
			// 显示售价以及单位
//			holder.unit.setText("无售价");
			if (sb.length() > 1) {
				holder.unit.setText(sb.toString());
			}

			holder.sellCount.setText("已售" + entity.order_count);

			//
			holder.layout.removeAllViews();
			if (null == entity.image || entity.image.isEmpty()) {
				holder.hsc.setVisibility(View.GONE);
			} else {
				holder.hsc.setVisibility(View.VISIBLE);
				for (int i = 0; i < entity.image.size(); i++) {
					if (TextUtils.isEmpty(entity.image.get(i)) || entity.image.get(i).indexOf(".arm") > 0) {
						continue;
					}
					ImageView view = new ImageView(context);
					view.setLayoutParams(params);
					view.setScaleType(ScaleType.CENTER_CROP);
					MyApplication.getInstance().getImageLoaderManager().loadNormalImage(view, entity.image.get(i),
							LoadImageType.NormalImage);
					holder.layout.addView(view);
				}
			}

			if (!TextUtils.isEmpty(entity.desc)) {
				holder.desc.setText(entity.desc);
			}

            holder.audit.setVisibility(View.GONE);

			// status
			switch (entity.status) {
			case GoodsDetailEntity.STATUS_PASS:
				holder.status.setText("出售中");
				holder.more.setVisibility(View.VISIBLE);
				break;
			case GoodsDetailEntity.STATUS_UN_PASS:
				holder.status.setText("未通过");
                if (!TextUtils.isEmpty(entity.reason))
                    holder.audit.setText(entity.reason);
				holder.more.setVisibility(View.VISIBLE);
				break;
			case GoodsDetailEntity.STATUS_UNDER_REVIEW:
				holder.status.setText("审核中");
				holder.more.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}

//			holder.leaveCount.setText(String.valueOf(entity.comment_count));
//			holder.loveCount.setText(String.valueOf(entity.view_count));

			holder.more.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (null != moreCliclk) {
                        moreCliclk.onClick(entity);
                    }
                }
            });

            convertView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(SkillDetailsActivity.KEY, entity.id);
                    SkillDetailsActivity.jumpIn(context, bundle);
                }
            });

		}
		return convertView;
	}

	@Override
	public void setData(List<GoodsDetailEntity> data, boolean isRefresh) {
		if (isRefresh) {
			entities.clear();
		}
		this.entities.addAll(data);
	}

	@Override
	public List<GoodsDetailEntity> getData() {
		return entities;
	}

	public interface OnMoreCliclk {
		void onClick(GoodsDetailEntity entity);
	}

	class Holder {
		TextView name;
		ImageView more;
		TextView unit;
		TextView sellCount;

		HorizontalScrollView hsc;
		LinearLayout layout;

		TextView desc;
		TextView status;
        TextView audit;
//		TextView leaveCount;
//		TextView loveCount;

		public Holder(View convertView) {
			name = (TextView) convertView.findViewById(R.id.tv_skill_name);
			more = (ImageView) convertView.findViewById(R.id.iv_more);

			unit = (TextView) convertView.findViewById(R.id.tv_skill_unit);
			sellCount = (TextView) convertView.findViewById(R.id.tv_sell_count);

			hsc = (HorizontalScrollView) convertView.findViewById(R.id.hsv_img);
			layout = (LinearLayout) convertView.findViewById(R.id.line_img_layout);

			desc = (TextView) convertView.findViewById(R.id.tv_skill_desc);
			status = (TextView) convertView.findViewById(R.id.tv_skill_status);
            audit = (TextView) convertView.findViewById(R.id.tv_audit_mes);
//			leaveCount = (TextView) convertView.findViewById(R.id.tv_leave_count);
//			loveCount = (TextView) convertView.findViewById(R.id.tv_zan_count);
		}
	}

}