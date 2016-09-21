package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author it_huang
 * 
 * 
 *
 */
@SuppressLint("InflateParams")
public class UserInfoAdapter extends BaseAdapter implements IDataAdapter<List<UserInfoEntity>> {
	private List<UserInfoEntity> list = new ArrayList<UserInfoEntity>();
	private Context context;
	private LayoutInflater mInflater;

	public UserInfoAdapter(Context context) {
		super();
		this.context = context;
		mInflater = LayoutInflater.from(context);
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

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		UserInfoEntity info = list.get(position);
		// 获得操作对象
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_user_info, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (null != holder && null != info) {
			// 头像背景
			switch (info.sex) {
			case UserInfoEntity.SEX_DEF:
				holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_def));
				break;
			case UserInfoEntity.SEX_FEMAL:
				holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_femal));
				break;
			case UserInfoEntity.SEX_MAL:
				holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_mal));
				break;
			}

			if (TextUtils.isEmpty(info.avatar)) {
				holder.avatar.setImageResource(R.drawable.user_small);
			} else {
				// 加载头像
				MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.avatar, info.avatar,
						LoadImageType.RoundAvatar);
			}

			if (TextUtils.isEmpty(info.nick)) {
				holder.nick.setText("无名氏");
			} else {
				holder.nick.setText(info.nick);
			}

			// holder.distance.setText("20km");

			holder.start.setText(String.valueOf(info.star));
		}
		return convertView;
	}

	@Override
	public void setData(List<UserInfoEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);
	}

	@Override
	public List<UserInfoEntity> getData() {
		return list;
	}

	class Holder {
		ImageView avatar;
		TextView nick;
		TextView serviceSafeguard;
		TextView studentAuth;
		TextView distance;
		TextView start;

		public Holder(View convertView) {
			avatar = (ImageView) convertView.findViewById(R.id.iv_user_avatar);
			nick = (TextView) convertView.findViewById(R.id.tv_user_nick);
			serviceSafeguard = (TextView) convertView.findViewById(R.id.tv_service_safeguard);
			studentAuth = (TextView) convertView.findViewById(R.id.tv_student_auth);
			distance = (TextView) convertView.findViewById(R.id.tv_userinfo_distance);
			start = (TextView) convertView.findViewById(R.id.tv_userinf_start);
		}
	}

}
