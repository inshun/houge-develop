/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.ysp.houge.model.entity.db;

import java.io.Serializable;
import java.util.Date;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @描述: 聊天列表项实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日上午11:47:36
 * @version 1.0
 */
@DatabaseTable(tableName = "chat_message")
public class ItemMessageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 此条聊天纪录的ID */
	@DatabaseField(generatedId = true, columnName = "msg_id")
	public int msgId;

	/** 登录者的ID,用来解决多账号登录时,聊天纪录依旧显示的问题 */
	@DatabaseField(columnName = "login_uid")
	public int loginUid;

	/** 发送者的ID */
	@DatabaseField(columnName = "sender_id")
	public int senderId;

	/** 接受者的ID */
	@DatabaseField(columnName = "receiver_id")
	public int receiverId;

	/** 此条聊天内容发送的状态 */
	@DatabaseField(columnName = "content")
	public String content;

	/** 时间 */
	@DatabaseField(columnName = "time")
	public Date time;

	/** 聊天消息的状态 */
	@DatabaseField(columnName = "status")
	public MessageStatus status;

	/** 消息的类型 */
	@DatabaseField(columnName = "type")
	public MessageType type = MessageType.Txt;

	/** 聊天接受者的姓名 */
	public String userName;

	/** 聊天接受者的头像地址 */
	public String avatarUrl;

	// 用ormlite建立关系型数据库时必须要有一个无参的构造方法
	public ItemMessageEntity() {
		super();
	}

	public enum MessageStatus {
		/** 发送中 */
		Sending,
		/** 发送失败 */
		Failed,
		/** 发送成功 */
		Successed
	}

	public enum MessageType {
		/**
		 * @字段：Txt
		 * @功能描述：文本类型
		 * @创建人：tyn
		 * @创建时间：2015年5月26日下午2:11:51
		 */
		Txt,
		/**
		 * @字段：Picture
		 * @功能描述：图片类型
		 * @创建人：tyn
		 * @创建时间：2015年5月26日下午2:11:53
		 */
		Picture,
		/**
		 * @字段：Voice
		 * @功能描述：语音类型
		 * @创建人：tyn
		 * @创建时间：2015年5月26日下午2:11:55
		 */
		Voice, Location,
		/**
		 * @字段：Audio
		 * @功能描述：视频类型
		 * @创建人：tyn
		 * @创建时间：2015年5月26日下午2:12:06
		 */
		Audio,
	}

}
