package com.ysp.houge.model.entity.bean;

import com.google.gson.annotations.SerializedName;
import com.ysp.houge.model.entity.db.UserInfoEntity;

import java.io.Serializable;

/**
 * @描述: 收到的推送实体类
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月2日下午5:38:37
 * @version 1.0
 */
public class PushReceiveEntity implements Serializable{
	/**
	 * @字段：msgid
	 * @功能描述：消息ID
	 * @创建人：tyn
	 * @创建时间：2015年8月2日下午5:44:28
	 */
	@SerializedName(value = "msgid")
	private int msgid;

	/**
	 * @字段：senduser
	 * @功能描述：发送者的信息
	 * @创建人：tyn
	 * @创建时间：2015年8月2日下午5:44:39
	 */
	@SerializedName(value = "senduser")
	private UserInfoEntity senduser;

	/**
	 * @字段：receiver
	 * @功能描述：接收者的信息
	 * @创建人：tyn
	 * @创建时间：2015年8月2日下午5:44:49
	 */
	@SerializedName(value = "receiver")
	private UserInfoEntity receiver;

	/**
	 * @字段：msgType
	 * @功能描述：消息的类型，1表示文本消息，2表示图片消息，3表示语音消息，4表示视频消息
	 * @创建人：tyn
	 * @创建时间：2015年8月2日下午5:45:09
	 */
	@SerializedName(value = "msgType")
	private int msgType;

	/**
	 * @字段：content
	 * @功能描述：消息内容
	 * @创建人：tyn
	 * @创建时间：2015年8月2日下午5:45:16
	 */
	@SerializedName(value = "content")
	private String content;

	/**
	 * @字段：created
	 * @功能描述：消息的发送时间
	 * @创建人：tyn
	 * @创建时间：2015年8月2日下午5:45:25
	 */
	@SerializedName(value = "created")
	private String created;

	/**
	 * @字段：pushType
	 * @功能描述：推送的消息类型。0表示推送的是系统消息，1表示客服消息或者在线聊天消息，2表示有新的兼职信息更新
	 * @创建人：tyn
	 * @创建时间：2015年8月2日下午5:46:06
	 */
	@SerializedName(value = "pushType")
	private int pushType;

	/**
	 * @return the msgid
	 */
	public int getMsgid() {
		return msgid;
	}

	/**
	 * @param msgid
	 *            the msgid to set
	 */
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	/**
	 * @return the senduser
	 */
	public UserInfoEntity getSenduser() {
		return senduser;
	}

	/**
	 * @param senduser
	 *            the senduser to set
	 */
	public void setSenduser(UserInfoEntity senduser) {
		this.senduser = senduser;
	}

	/**
	 * @return the receiver
	 */
	public UserInfoEntity getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(UserInfoEntity receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return the msgType
	 */
	public int getMsgType() {
		return msgType;
	}

	/**
	 * @param msgType
	 *            the msgType to set
	 */
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the created
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the pushType
	 */
	public int getPushType() {
		return pushType;
	}

	/**
	 * @param pushType
	 *            the pushType to set
	 */
	public void setPushType(int pushType) {
		this.pushType = pushType;
	}

}
