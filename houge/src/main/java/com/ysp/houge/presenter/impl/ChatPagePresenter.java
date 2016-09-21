package com.ysp.houge.presenter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.app.Constants;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IMessageModel;
import com.ysp.houge.model.entity.db.ItemMessageEntity;
import com.ysp.houge.model.entity.db.ItemMessageEntity.MessageStatus;
import com.ysp.houge.model.entity.db.ItemMessageEntity.MessageType;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.MessageModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IChatPagePresenter;
import com.ysp.houge.ui.iview.IChatPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import android.text.TextUtils;

/**
 * @描述:聊天页面presenter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日下午9:04:56
 * @version 1.0
 */
public class ChatPagePresenter extends BasePresenter<IChatPageView> implements
		IChatPagePresenter {
	private int page = 0;
	private IMessageModel iMessageInfoModel;
	private UserInfoEntity selfUserInfo;
	private UserInfoEntity friendUserInfo;

	public ChatPagePresenter(IChatPageView view, UserInfoEntity friendUserInfo) {
		super(view);
		this.friendUserInfo = friendUserInfo;
	}

	@Override
	public void initModel() {
		iMessageInfoModel = new MessageModelImpl();
		selfUserInfo = MyApplication.getInstance().getUserInfo();
	}

	@Override
	public void sendTxtMessage(String content) {
		if (TextUtils.isEmpty(content)) {
			mView.showToast(R.string.please_input_content);
			return;
		}
		final ItemMessageEntity itemMessageEntity = createItemMessageEntity(content);
		// 加入数据库
//		MessageController.addOrUpdate(itemMessageEntity, MyApplication
//				.getInstance().getCurrentUid());
//		mView.addOneItemToList(itemMessageEntity);
//		mView.clearInputEdit();
//		iMessageInfoModel.sendTxtMessage(content, new OnNetResponseCallback() {
//
//			@Override
//			public void onSuccess(Object data) {
//				itemMessageEntity.status = MessageStatus.Successed;
//				// 更新数据库
////				MessageController.addOrUpdate(itemMessageEntity, MyApplication
////						.getInstance().getCurrentUid());
//				mView.updateOneSentMessage(itemMessageEntity);
//			}

//			@Override
//			public void onError(int errorCode, String message) {
//				itemMessageEntity.status = MessageStatus.Failed;
//				// 更新数据库
////				MessageController.addOrUpdate(itemMessageEntity, MyApplication
////						.getInstance().getCurrentUid());
//				mView.updateOneSentMessage(itemMessageEntity);
//			}
//		});
	}

	@Override
	public ItemMessageEntity createItemMessageEntity(String content) {
		ItemMessageEntity itemMessageEntity = new ItemMessageEntity();
		if (!TextUtils.isEmpty(selfUserInfo.avatar)) {
			itemMessageEntity.avatarUrl = HttpApi
					.getPictureUrl(selfUserInfo.avatar);
		}
		itemMessageEntity.userName = selfUserInfo.nick;
		itemMessageEntity.content = content;
		itemMessageEntity.loginUid = selfUserInfo.id;
		itemMessageEntity.receiverId = -1;// 官方客服
		itemMessageEntity.senderId = selfUserInfo.id;
		itemMessageEntity.status = MessageStatus.Sending;
		itemMessageEntity.time = new Date();
		itemMessageEntity.type = MessageType.Txt;
		return itemMessageEntity;
	}

	@Override
	public void getMessageList() {
//		iMessageInfoModel.getChatMessageListFromDB(selfUserInfo.id,
//				friendUserInfo.id, page * Constants.REQUEST_SIZE,
//				Constants.REQUEST_SIZE, new OnNetResponseCallback() {
//
//					@Override
//					public void onSuccess(Object data) {
//						page++;
//						List<ItemMessageEntity> entities = (List<ItemMessageEntity>) data;
//						if (entities != null) {
//							mView.loadMore(entities);
//						} else {
//							mView.loadMore(new ArrayList<ItemMessageEntity>());
//						}
//					}
//
//					@Override
//					public void onError(int errorCode, String message) {
//
//					}
//				});
	}

	@Override
	public void getChatMessageFromPush(ItemMessageEntity itemMessageEntity) {
//		mView.addOneItemToList(itemMessageEntity);
	}

	@Override
	public void clearConversationUnreadCount() {
//		ConversationController.markAsRead(selfUserInfo.id, friendUserInfo.id);
	}

}
