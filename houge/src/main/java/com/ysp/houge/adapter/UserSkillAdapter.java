package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("InflateParams")
public class UserSkillAdapter extends BaseAdapter implements IDataAdapter<List<GoodsDetailEntity>> {
	private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();
	private OnItemClickListener listener;
	private LayoutInflater mInflater;
	@SuppressWarnings("unused")
	private Context context;

	private LinearLayout.LayoutParams params;

	public UserSkillAdapter(Context context, OnItemClickListener listener) {
		this.context = context;
		mInflater = LayoutInflater.from(context);
		this.listener = listener;
		int imgSize = (SizeUtils.getScreenWidth((Activity) context) - SizeUtils.dip2px(context, 20) * 2) / 3;
		params = new LinearLayout.LayoutParams(imgSize, imgSize);
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
			convertView = (View) mInflater.inflate(R.layout.item_user_skills, null);
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

		GoodsDetailEntity detailEntity = null;
		if (list.size() > position) {
			detailEntity = list.get(position);
		}
		if (null != holder && null != detailEntity) {
			// 技能名称
			if (TextUtils.isEmpty(detailEntity.title)) {
				holder.name.setText("我能·未知技能");
			} else {
				holder.name.setText("我能·" + detailEntity.title);
			}

			// 等级
			if (TextUtils.isEmpty(detailEntity.level)) {
				holder.level.setVisibility(View.GONE);
			} else {
				holder.level.setVisibility(View.VISIBLE);
				if (TextUtils.equals("1", detailEntity.level)) {
					holder.level.setText("初级");
				} else if (TextUtils.equals("2", detailEntity.level)) {
					holder.level.setText("中级");
				} else if (TextUtils.equals("3", detailEntity.level)) {
					holder.level.setText("高级");
				} else if (TextUtils.equals("4", detailEntity.level)) {
					holder.level.setText("资深");
				} else if (TextUtils.equals("5", detailEntity.level)) {
					holder.level.setText("专家");
				} else {
					holder.level.setVisibility(View.GONE);
				}
			}

			holder.distance.setText(detailEntity.distance);
			// 距离
            if (TextUtils.isEmpty(detailEntity.distance)){
                holder.distance.setVisibility(View.GONE);
            }else {
                holder.distance.setVisibility(View.VISIBLE);
            }

			// 三张图片(计算宽高度并赋值)
			if (null == detailEntity.image || !(detailEntity.image.size() > 0)) {
				holder.imgLayout.setVisibility(View.GONE);
			} else {
				holder.imgLayout.setVisibility(View.VISIBLE);
				for (int i = 0; i < 3; i++) {
					holder.imgs[i].setLayoutParams(params);

					if (detailEntity.image.size() > i) {
						MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.imgs[i],
								detailEntity.image.get(i), LoadImageType.NormalImage);
					} else {
						holder.imgs[i].setVisibility(View.INVISIBLE);
					}
				}
			}

			// 拼接销售价格
			StringBuilder sb = new StringBuilder();
			sb.append(detailEntity.price);
			sb.append("/");
			sb.append(detailEntity.unit);
			// 显示售价以及单位
				holder.unit.setText(sb.toString());

            if (TextUtils.isEmpty(detailEntity.desc))
                holder.desc.setVisibility(View.GONE);
            else
                holder.desc.setText(detailEntity.desc);

			holder.loveCount.setText(String.valueOf(detailEntity.view_count));

			holder.commentCount.setText(String.valueOf(detailEntity.comment_count));

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
		TextView name; // 技能名称
		TextView level;// 技能等级
		TextView distance;// 技能等级
		LinearLayout imgLayout;// 图片外层布局
		ImageView[] imgs;
		TextView desc; // 技能描述
		TextView unit; // 价格和单位
		TextView haveATalk; // 聊一聊
		ImageView share;// 分享
		TextView loveCount; // 喜欢的人
		TextView commentCount; // 评论

		public Holder(View convertView) {
			name = (TextView) convertView.findViewById(R.id.tv_skill_name);
			level = (TextView) convertView.findViewById(R.id.tv_level);
			distance = (TextView) convertView.findViewById(R.id.tv_distance);

			imgLayout = (LinearLayout) convertView.findViewById(R.id.line_user_skill_img);

			imgs = new ImageView[] { (ImageView) convertView.findViewById(R.id.iv_skill_img_one),
					(ImageView) convertView.findViewById(R.id.iv_skill_img_two),
					(ImageView) convertView.findViewById(R.id.iv_skill_img_three) };
			desc = (TextView) convertView.findViewById(R.id.tv_skill_description);
			unit = (TextView) convertView.findViewById(R.id.tv_skill_price_and_unit);
			haveATalk = (TextView) convertView.findViewById(R.id.tv_have_a_talk);
			share = (ImageView) convertView.findViewById(R.id.iv_share);
			loveCount = (TextView) convertView.findViewById(R.id.tv_praise);
			commentCount = (TextView) convertView.findViewById(R.id.tv_level_msg);
		}
	}
}
