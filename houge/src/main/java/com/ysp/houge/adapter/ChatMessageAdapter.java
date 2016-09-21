package com.ysp.houge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tyn.view.listviewhelper.IDataAdapter;
import com.ysp.houge.R;
import com.ysp.houge.app.Constants;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.db.ItemMessageEntity;
import com.ysp.houge.model.entity.db.ItemMessageEntity.MessageStatus;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述:聊天消息列表适配器
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日下午8:05:49
 * @version 1.0
 */
public class ChatMessageAdapter extends BaseAdapter implements
		IDataAdapter<List<ItemMessageEntity>> {
	private ArrayList<ItemMessageEntity> itemMessageEntities = new ArrayList<ItemMessageEntity>();
	private LayoutInflater mInflater;

	public ChatMessageAdapter(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return itemMessageEntities.size();
	}

	@Override
	public Object getItem(int position) {
		return itemMessageEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		ItemMessageEntity message = itemMessageEntities.get(position);
		switch (message.type) {
		case Txt:
			if (message.receiverId == MyApplication.getInstance()
					.getCurrentUid()) {
				return Constants.CHAT_TYPE_PLAIN_FROM;
			} else {
				return Constants.CHAT_TYPE_PLAIN_TO;
			}
		default:
			return 0;
		}
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int type = getItemViewType(position);
		if (convertView == null || convertView.getTag() == null) {
			switch (type) {
			case Constants.CHAT_TYPE_PLAIN_FROM:
				holder = new TxtFromHolder();
				convertView = mInflater.inflate(
						R.layout.chatting_item_txt_from, null);
				break;
			case Constants.CHAT_TYPE_PLAIN_TO:
				holder = new TxtToHolder();
				convertView = mInflater.inflate(R.layout.chatting_item_txt_to,
						null);
				break;
			default:
				break;
			}
			holder.initializeView(convertView);
			convertView.setTag(holder);
		} else {
			switch (type) {
			case Constants.CHAT_TYPE_PLAIN_FROM:
				holder = (TxtFromHolder) convertView.getTag();
				break;
			case Constants.CHAT_TYPE_PLAIN_TO:
				holder = (TxtToHolder) convertView.getTag();
				break;
			default:
				break;
			}
		}
		// 时间显示
		ItemMessageEntity messageEntity = itemMessageEntities.get(position);
		holder.initializeData(messageEntity);
		long currentTime = messageEntity.time.getTime();
		long previewTime = (position - 1) >= 0 ? itemMessageEntities
				.get(position - 1).time.getTime() : 0;
		if (holder.mChatItemTime != null) {
			if (currentTime - previewTime > 3 * 60 * 1000L) {
				holder.mChatItemTime.setVisibility(View.VISIBLE);
//				holder.mChatItemTime.setText(TimeUtility
//						.getListTime(messageEntity.time.getTime()));
			} else {
				holder.mChatItemTime.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	@Override
	public void setData(List<ItemMessageEntity> data, boolean isRefresh) {
		if (isRefresh) {
			itemMessageEntities.clear();
		}
		itemMessageEntities.addAll(data);

	}

	@Override
	public List<ItemMessageEntity> getData() {
		return itemMessageEntities;
	}

	abstract class ViewHolder {
		public TextView mChatItemTime;

		public abstract void initializeData(ItemMessageEntity message);

		public abstract void initializeView(View convertView);
	}

	class TxtFromHolder extends ViewHolder {

		private TextView mChatItemContent;
		private ImageView mChatItemAvatar;

		@Override
		public void initializeData(final ItemMessageEntity message) {
			mChatItemContent.setText(message.content);
			com.ysp.houge.app.MyApplication
					.getInstance()
					.getImageLoaderManager()
					.loadNormalImage(mChatItemAvatar,
							"drawable://" + R.drawable.official_custom_service,
							LoadImageType.RoundAvatar);
			mChatItemAvatar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

				}
			});
		}

		@Override
		public void initializeView(View convertView) {
			mChatItemTime = (TextView) convertView
					.findViewById(R.id.mChatItemTime);
			mChatItemContent = (TextView) convertView
					.findViewById(R.id.mChatItemContent);
			mChatItemAvatar = (ImageView) convertView
					.findViewById(R.id.mChatItemAvatar);

		}
	}

	class TxtToHolder extends ViewHolder {
		private TextView mChatItemContent;
		private ImageView mChatItemAvatar;
		private ImageView mChatItemSendStatus;
		private ProgressBar mChatItemProgress;

		@Override
		public void initializeData(final ItemMessageEntity message) {
			mChatItemContent.setText(message.content);
			com.ysp.houge.app.MyApplication
					.getInstance()
					.getImageLoaderManager()
					.loadNormalImage(
							mChatItemAvatar,
							HttpApi.getPictureUrl(com.ysp.houge.app.MyApplication
									.getInstance().getUserInfo().avatar),
							LoadImageType.RoundAvatar);
			mChatItemAvatar.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
				}
			});
			if (message.status == MessageStatus.Sending) {
				mChatItemProgress.setVisibility(View.VISIBLE);
				mChatItemSendStatus.setVisibility(View.GONE);
			} else {
				mChatItemProgress.setVisibility(View.GONE);
				if (message.status == MessageStatus.Failed) {
					mChatItemSendStatus.setVisibility(View.VISIBLE);
					mChatItemSendStatus
							.setBackgroundResource(R.drawable.chat_send_failed);
					mChatItemSendStatus
							.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {

								}
							});
				} else {
					mChatItemSendStatus.setVisibility(View.GONE);
				}
			}
		}

		@Override
		public void initializeView(View convertView) {
			mChatItemTime = (TextView) convertView
					.findViewById(R.id.mChatItemTime);
			mChatItemContent = (TextView) convertView
					.findViewById(R.id.mChatItemContent);
			mChatItemAvatar = (ImageView) convertView
					.findViewById(R.id.mChatItemAvatar);
			mChatItemSendStatus = (ImageView) convertView
					.findViewById(R.id.mChatItemSendStatus);
			mChatItemProgress = (ProgressBar) convertView
					.findViewById(R.id.mChatItemProgress);
		}
	}

}
