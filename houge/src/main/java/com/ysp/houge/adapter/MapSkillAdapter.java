package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnMapHaveATalkClickListener;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 描述： 地图页面技能适配器
 *
 * @ClassName: MapSkillAdapter
 * 
 * @author: hx
 * 
 * @date: 2015年12月19日 下午5:08:06
 * 
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class MapSkillAdapter extends BaseAdapter {
	private OnMapHaveATalkClickListener listener;
	private List<GoodsDetailEntity> list;
	private Context context;

	public MapSkillAdapter(OnMapHaveATalkClickListener listener, List<GoodsDetailEntity> list, Context context) {
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_map_skill, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		final GoodsDetailEntity detailEntity = list.get(position);
		if (holder != null && null != detailEntity) {
			// 标题
			if (TextUtils.isEmpty(detailEntity.title)) {
				holder.title.setText("我能 · 未知名称");
			} else {
				holder.title.setText("我能 · " + detailEntity.title);
			}

            //图片
            if (null != detailEntity.image && !detailEntity.image.isEmpty())
                MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.img,detailEntity.image.get(0), ImageLoaderManager.LoadImageType.NormalImage);

			// 等级
			if (TextUtils.isEmpty(detailEntity.level)) {
				holder.level.setVisibility(View.GONE);
			} else {
				holder.level.setVisibility(View.VISIBLE);
				if (TextUtils.equals("2", detailEntity.level)) {
					holder.level.setText("初级");
				} else if (TextUtils.equals("3", detailEntity.level)) {
					holder.level.setText("中级");
				} else if (TextUtils.equals("4", detailEntity.level)) {
					holder.level.setText("高级");
				} else if (TextUtils.equals("5", detailEntity.level)) {
					holder.level.setText("资深");
				} else if (TextUtils.equals("6", detailEntity.level)) {
					holder.level.setText("专家");
				} else {
					holder.level.setVisibility(View.GONE);
				}
			}

			// 预约次数
			StringBuilder sb = new StringBuilder();
			sb.append("被预约");
			sb.append(detailEntity.order_count);
			sb.append("次");
			holder.orderCount.setText(sb.toString());

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
		ImageView img;
		TextView title;
		TextView level;
		TextView orderCount;
		TextView haveATalk;

		public Holder(View convertView) {
			img = (ImageView) convertView.findViewById(R.id.iv_img);
			title = (TextView) convertView.findViewById(R.id.tv_title);
			level = (TextView) convertView.findViewById(R.id.tv_level);
			orderCount = (TextView) convertView.findViewById(R.id.tv_order_count);
			haveATalk = (TextView) convertView.findViewById(R.id.tv_have_a_talk);
		}
	}
}
