package com.ysp.houge.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.util.DateUtils;
import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.SystemMessageEntity;

/**
 * @描述:官方客服列表适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月25日上午11:34:38
 * @version 2.2
 */
public class SystemMessageAdatper extends BaseAdapter implements
		IDataAdapter<List<SystemMessageEntity>> {

	private List<SystemMessageEntity> itemConversationEntities = new ArrayList<SystemMessageEntity>();
	private LayoutInflater mInflater;

	public SystemMessageAdatper(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itemConversationEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return itemConversationEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		final SystemMessageEntity systemMessageEntity = itemConversationEntities
				.get(position);
		if (convertView == null) {
			convertView = (View) mInflater.inflate(
					R.layout.item_system_message, null);
			holder = new Holder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (null != holder && null != systemMessageEntity) {
            //图标，暂时不色织
            //MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.icon,"", ImageLoaderManager.LoadImageType.CircleCornerImage);

            //标题
            if (TextUtils.isEmpty(systemMessageEntity.title)){
                holder.title.setText("未知标题");
            }else {
                holder.title.setText(systemMessageEntity.title);
            }

            //时间
            if (TextUtils.isEmpty(systemMessageEntity.created_at)){
                holder.time.setText("");
            }else {
                long t = -1l;
                try{
                    t = Long.parseLong(systemMessageEntity.created_at+"0000");
                    holder.time.setText(DateUtils.getTimestampString(new Date(t)));
                }catch (Exception e){}
            }

            //消息类容
            if (TextUtils.isEmpty(systemMessageEntity.content)){
                holder.message.setText("空的消息");
            }else {
                holder.message.setText(systemMessageEntity.content);
            }
		}
		return convertView;
	}

	@Override
	public void setData(List<SystemMessageEntity> data, boolean isRefresh) {
		if (isRefresh) {
			itemConversationEntities.clear();
		}
		itemConversationEntities.addAll(data);
	}

	@Override
	public List<SystemMessageEntity> getData() {
		return itemConversationEntities;
	}

	class Holder {
        ImageView icon;
        TextView title;
        TextView time;
        TextView message;

		public Holder(View convertView) {
            icon = (ImageView) convertView
					.findViewById(R.id.iv_icon);
            title = (TextView) convertView
                    .findViewById(R.id.tv_title);
            time = (TextView) convertView
                    .findViewById(R.id.tv_time);
            message = (TextView) convertView
                    .findViewById(R.id.tv_message);

		}

	}

}
