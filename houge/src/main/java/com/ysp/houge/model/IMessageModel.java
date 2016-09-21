package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * 描述： 消息model层接口
 *
 * @ClassName: IMessageModel
 * 
 * @author: hx
 * 
 * @date: 2016年1月2日 下午2:41:55
 * 
 *        版本: 1.0
 */
public interface IMessageModel {

	/** 获取消息列表 */
	void getMessageList(boolean loadConversation,OnNetResponseCallback onNetResponseCallback);

    /** 获取某个具体类型的消息列表*/
    void getMessageList(int type,int page,OnNetResponseCallback onNetResponseCallback);

}
