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

@SuppressLint("InflateParams")
public class SearchUserAdapter extends BaseAdapter implements IDataAdapter<List<UserInfoEntity>> {
	private List<UserInfoEntity> list = new ArrayList<UserInfoEntity>();
	private LayoutInflater mInflater;
	private Context context;

	public SearchUserAdapter(Context context) {
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
		// 获得操作对象
		if (convertView == null) {
			convertView = (View) mInflater.inflate(R.layout.item_search_user, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		UserInfoEntity entity = list.get(position);
		if (holder != null && entity != null) {
			if (!TextUtils.isEmpty(entity.avatar)) {
				MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.avatar, entity.avatar,
						LoadImageType.RoundAvatar);
			}

			switch (entity.sex) {
			case UserInfoEntity.SEX_MAL:
				holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_mal));
				break;
			case UserInfoEntity.SEX_FEMAL:
				holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_femal));
				break;
			default:
				holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_def));
				break;
			}

			if (!TextUtils.isEmpty(entity.nick)) {
				holder.nick.setText(entity.nick);
			} else {
				holder.nick.setText("无名氏");
			}

			holder.start.setText(String.valueOf(entity.star) + "★");

            //服务保障
            double serviceSafeguardg = -1;
            try{serviceSafeguardg = Double.parseDouble(entity.serviceSafeguardg);}catch (Exception e){}
            if (serviceSafeguardg > 0){
                holder.serviceSafeguard.setVisibility(View.VISIBLE);
            }else {
                holder.serviceSafeguard.setVisibility(View.GONE);
            }

            //认证
            if (TextUtils.isEmpty(entity.verfied)) {
                holder.auth.setVisibility(View.GONE);
            } else {
                holder.auth.setVisibility(View.VISIBLE);
                if (TextUtils.equals("1", entity.verfied)) {
                    holder.auth.setText("学生认证");
                } else if (TextUtils.equals("2", entity.verfied)) {
                    holder.auth.setText("个人认证");
                } else  if (TextUtils.equals("3", entity.verfied)){
                    holder.auth.setText("企业认证");
                }else {
                    holder.auth.setVisibility(View.GONE);
                }
            }
		}
		return convertView;
	}

	@Override
	public void setData(List<UserInfoEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list = data;
		} else {
			list.addAll(data);
		}
	}

	@Override
	public List<UserInfoEntity> getData() {
		return list;
	}

	class Holder {
		ImageView avatar;
		TextView nick;
		TextView start;
		TextView serviceSafeguard;
		TextView auth;

		public Holder(View convertView) {
			avatar = (ImageView) convertView.findViewById(R.id.iv_user_avatar);

			nick = (TextView) convertView.findViewById(R.id.tv_user_nick);
			start = (TextView) convertView.findViewById(R.id.tv_user_rate);
			serviceSafeguard = (TextView) convertView.findViewById(R.id.iv_service_safeguard);
            auth = (TextView) convertView.findViewById(R.id.iv_user_auth);
		}

	}

}
