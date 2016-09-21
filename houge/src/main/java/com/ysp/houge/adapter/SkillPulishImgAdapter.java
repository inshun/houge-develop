package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

@SuppressLint("InflateParams")
public class SkillPulishImgAdapter extends BaseAdapter {
	private List<String> list = new ArrayList<String>();
	private LayoutInflater mInflater;
	private LayoutParams params;

	public SkillPulishImgAdapter(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
		// 计算宽高度(左右各15d,水平间距10)
		int size = SizeUtils.getScreenWidth((Activity) context)
				- (SizeUtils.dip2px(context, 15) + SizeUtils.dip2px(context, 10)) * 2;
		size = size / 3;
		params = new AbsListView.LayoutParams(size, size);
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_seller_pulish_img, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			// 设置宽高度(左右各15d,水平间距10)
			holder.img.setLayoutParams(params);

			if (TextUtils.isEmpty(list.get(position))) {
				MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.img,
						"drawable://" + R.drawable.add_img, LoadImageType.NormalImage);
			} else {
				MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.img,
						"file://" + list.get(position), LoadImageType.NormalImage);
			}
		}
		return convertView;
	}

	public List<String> getData() {
		return list;
	}

	public void setData(List<String> data) {
		list.clear();
		list.addAll(data);
	}

	class Holder {
		ImageView img;

		public Holder(View convertView) {
			img = (ImageView) convertView.findViewById(R.id.iv_seller_pulish_imgs);
		}

	}

}
