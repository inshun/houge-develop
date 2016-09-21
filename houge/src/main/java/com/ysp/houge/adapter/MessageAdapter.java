package com.ysp.houge.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.easemob.chat.EMMessage;
import com.easemob.easeui.utils.EaseCommonUtils;
import com.easemob.easeui.utils.EaseSmileUtils;
import com.easemob.util.DateUtils;
import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.db.MessageEntity;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @描述:会话列表适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日上午11:11:30
 * @version 1.0
 */
@SuppressLint("InflateParams")
public class MessageAdapter extends BaseAdapter implements IDataAdapter<List<MessageEntity>> {
	// private SimpleDateFormat format = new SimpleDateFormat("HH:mm");
	private ArrayList<MessageEntity> list = new ArrayList<MessageEntity>();
	private LayoutInflater mInflater;
	@SuppressWarnings("unused")
	private Context context;

	public MessageAdapter(Context context) {
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
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		final MessageEntity messageEntity = list.get(position);
		if (convertView == null) {
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.item_message, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.title = (TextView) convertView.findViewById(R.id.tv_title);
			holder.message = (TextView) convertView.findViewById(R.id.tv_message);
			holder.unreadCount = (TextView) convertView.findViewById(R.id.tv_unreader_count);
			holder.time = (TextView) convertView.findViewById(R.id.tv_time);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		if (null != holder && null != messageEntity) {

			// 消息标题
			if (TextUtils.isEmpty(messageEntity.title)) {
				holder.title.setText("无名氏");
			} else {
				holder.title.setText(messageEntity.title);
			}

			// 消息内容
			if (null!=messageEntity.conversation){
				EMMessage lastMessage = messageEntity.conversation.getLastMessage();
				holder.message.setText(EaseSmileUtils.getSmiledText(context, EaseCommonUtils.getMessageDigest(lastMessage, (context))), TextView.BufferType.SPANNABLE);
			    MyApplication.getInstance().getImageLoaderManager().loadNormalImage(holder.icon, messageEntity.icon,
					    LoadImageType.RoundAvatar);
			}else {
				if (TextUtils.isEmpty(messageEntity.message)) {
					holder.message.setText("未知消息");
				} else {
					holder.message.setText(Html.fromHtml(messageEntity.message));
				}

                //图标
                switch (messageEntity.type){
                    //订单消息
                    case 1:
                        holder.icon.setImageResource(R.drawable.defaultpic);
                        break;

                    //2系统消息
                    case 2:
                        holder.icon.setImageResource(R.drawable.official);
                        break;

                    //3活动消息
                    case 3:
                        holder.icon.setImageResource(R.drawable.activities);
                        break;

                    //4评论消息
                    case 4:
                        holder.icon.setImageResource(R.drawable.comment);
                        break;

                    //5喜欢消息
                    case 5:
                        holder.icon.setImageResource(R.drawable.love);
                        break;
                    default:
                        holder.icon.setImageResource(R.drawable.defaultpic);
                        break;
                }
			}

			// 时间
            if (messageEntity.time == -1l)
                holder.time.setText("");
            else
			    holder.time.setText(DateUtils.getTimestampString(new Date(messageEntity.time)));

			// 未读消息数量
			holder.unreadCount.setVisibility(View.VISIBLE);
			if (TextUtils.isEmpty(messageEntity.unReadCount)) {
				holder.unreadCount.setVisibility(View.INVISIBLE);
			}else {
                int count = 0;
                try{count = Integer.parseInt(messageEntity.unReadCount);}catch (Exception e){}
                if (count > 100)
				    holder.unreadCount.setText("99+");
                else if (count > 0)
                    holder.unreadCount.setText(String.valueOf(count));
                else
                    holder.unreadCount.setVisibility(View.INVISIBLE);

			}
		}
		return convertView;
	}

	@Override
	public void setData(List<MessageEntity> data, boolean isRefresh) {
		if (isRefresh) {
			list.clear();
		}
		list.addAll(data);

	}

	@Override
	public List<MessageEntity> getData() {
		return list;
	}

	class Holder {
		public ImageView icon;
		public TextView title;
		public TextView message;
		public TextView unreadCount;
		public TextView time;
	}
}
