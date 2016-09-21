package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.List;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.CommentEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("InflateParams")
public class CommentAdapter extends BaseAdapter implements IDataAdapter<List<CommentEntity>> {
	private List<CommentEntity> list = new ArrayList<CommentEntity>();
	private Context context;

	public CommentAdapter(Context context) {
		super();
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
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		// 获得操作对象
		if (convertView == null) {
			convertView = (View) LayoutInflater.from(context).inflate(R.layout.item_comment, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (holder != null) {
			// 性別,边框
			switch (list.get(position).sex) {
			case UserInfoEntity.SEX_DEF:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_def));
				}
				break;
			case UserInfoEntity.SEX_FEMAL:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_femal));
				}
				break;
			case UserInfoEntity.SEX_MAL:
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
					holder.avatar.setBackground(context.getResources().getDrawable(R.drawable.shapa_sex_mal));
				}
				break;
			}

			// 头像
			MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.avatar,
					list.get(position).avatar, LoadImageType.RoundAvatar);

			// 昵称
			if (TextUtils.isEmpty(list.get(position).nick)) {
				holder.title.setText("无名氏");
			} else {
				holder.title.setText(list.get(position).nick);
			}

			// 评论内容
			if (TextUtils.isEmpty(list.get(position).content)) {
				holder.body.setText("无名氏");
			} else {
				holder.body.setText(list.get(position).content);
			}

			// 时间
			if (TextUtils.isEmpty(list.get(position).created_at)) {
				holder.date.setText("刚刚");
			} else {
				holder.date.setText(list.get(position).created_at);
			}

            final int index = position;
            holder.avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(UserDetailsActivity.KEY, (int) list.get(index).user_id);
                    UserDetailsActivity.jumpIn(context, bundle);
                }
            });
		}
		return convertView;
	}

	@Override
	public void setData(List<CommentEntity> data, boolean isRefresh) {
		if (isRefresh) {
			this.list.clear();
		}
        list.addAll(data);
	}

	@Override
	public List<CommentEntity> getData() {
		return list;
	}

	class Holder {
		ImageView avatar;
		TextView title;
		TextView body;
		TextView date;

		public Holder(View convertView) {
			avatar = (ImageView) convertView.findViewById(R.id.iv_user_avatar);
			title = (TextView) convertView.findViewById(R.id.tv_commnet_title);
			body = (TextView) convertView.findViewById(R.id.tv_comment_body);
			date = (TextView) convertView.findViewById(R.id.tv_comment_date);
		}

	}

}
