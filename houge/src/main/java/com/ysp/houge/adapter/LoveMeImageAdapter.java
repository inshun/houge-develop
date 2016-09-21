package com.ysp.houge.adapter;

import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class LoveMeImageAdapter extends BaseAdapter {
	private int width;
	private Context context;
	private List<UserInfoEntity> list;

	public LoveMeImageAdapter(int width, Context context, List<UserInfoEntity> list) {
		super();
		this.width = width;
		this.context = context;
		this.list = list;
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		// 获得操作对象
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_love_me, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			LayoutParams params = new LayoutParams(width, width);
			holder.avatar.setLayoutParams(params);
			UserInfoEntity info = list.get(position);
			// 边框
			switch (info.sex) {
			case UserInfoEntity.SEX_MAL:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_mal));
				}
				break;
			case UserInfoEntity.SEX_FEMAL:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_femal));
				}
				break;
			default:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_def));
				}
				break;
			}

			// 图片
			MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.avatar, info.avatar,
					LoadImageType.RoundAvatar);
		}
		return convertView;
	}

	class Holder {
		ImageView avatar;

		public Holder(View convertView) {
			avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
		}

	}
}
