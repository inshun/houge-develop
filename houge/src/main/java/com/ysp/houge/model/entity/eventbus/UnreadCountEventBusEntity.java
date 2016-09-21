package com.ysp.houge.model.entity.eventbus;

/**
 * @描述:首页切换栏tab未读数量通知实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月25日下午3:12:29
 * @version 2.4
 */
public class UnreadCountEventBusEntity {
	public int unreadCount;

	public CountType countType;

	/**
	 * @描述
	 * @param unreadCount
	 * @param countType
	 */
	public UnreadCountEventBusEntity(int unreadCount, CountType countType) {
		super();
		this.unreadCount = unreadCount;
		this.countType = countType;
	}

	public enum CountType {
		/**
		 * @字段：ConversationUnreadCount
		 * @功能描述：会话页面的未读消息数量
		 * @创建人：tyn
		 * @创建时间：2015年9月25日下午3:12:58
		 */
		ConversationUnreadCount
	}
}
