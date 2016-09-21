package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.db.ItemMessageEntity;

/**
 * @描述:聊天页面的presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日下午9:03:37
 * @version 1.0
 */
public interface IChatPagePresenter {
	/**
	 * @描述:发送文本消息
	 * @方法名: sendTxtMessage
	 * @param content
	 *            文本消息内容
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月16日下午2:54:16
	 * @修改人 tyn
	 * @修改时间 2015年8月16日下午2:54:16
	 * @修改备注
	 * @since
	 * @throws
	 */
	void sendTxtMessage(String content);

	/**
	 * @描述:创建一条文本聊天消息
	 * @方法名: createItemMessageEntity
	 * @param content
	 *            聊天消息内容
	 * @return
	 * @返回类型 ItemMessageEntity
	 * @创建人 tyn
	 * @创建时间 2015年8月16日下午2:53:49
	 * @修改人 tyn
	 * @修改时间 2015年8月16日下午2:53:49
	 * @修改备注
	 * @since
	 * @throws
	 */
	ItemMessageEntity createItemMessageEntity(String content);

	/**
	 * @描述:获取消息列表
	 * @方法名: getMessageList
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月16日下午2:54:54
	 * @修改人 tyn
	 * @修改时间 2015年8月16日下午2:54:54
	 * @修改备注
	 * @since
	 * @throws
	 */
	void getMessageList();

	/**
	 * @描述:从推送获取聊天消息
	 * @方法名: getMessageFromPush
	 * @param itemMessageEntity
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月2日上午11:25:06
	 * @修改人 tyn
	 * @修改时间 2015年8月2日上午11:25:06
	 * @修改备注
	 * @since
	 * @throws
	 */
	void getChatMessageFromPush(ItemMessageEntity itemMessageEntity);

	/**
	 * @描述:清除会话消息未读数量
	 * @方法名: clearConversationUnreadCount
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年8月16日下午2:55:03
	 * @修改人 tyn
	 * @修改时间 2015年8月16日下午2:55:03
	 * @修改备注
	 * @since
	 * @throws
	 */
	void clearConversationUnreadCount();

}
