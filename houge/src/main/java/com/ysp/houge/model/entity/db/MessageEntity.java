/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 * 
 */
package com.ysp.houge.model.entity.db;

import com.easemob.chat.EMConversation;

/**
 * @描述:聊天列表实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日上午11:54:16
 * @version 1.0
 */
public class MessageEntity {
	public EMConversation conversation;

	public String icon;
	public String unReadCount;
	public String title;
	public String message;
	public long time;

    public String id;
    public String user_type;
    public int type;
    public String created_at;
    public String content;
    public String is_new;
    public String pic;
    public String url;
    public String count;


	public MessageEntity() {
	}

	public MessageEntity(String icon, String unReadCount, String title, String message, long time) {
		super();
		this.icon = icon;
		this.unReadCount = unReadCount;
		this.title = title;
		this.message = message;
		this.time = time;
	}

	public MessageEntity(EMConversation conversation) {
		super();
		this.conversation = conversation;
	}
}
