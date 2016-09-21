package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @author it_hu
 * 
 *         时间model层接口
 *
 */
public interface ITimeModel {

	/** 获取设置的时间 */
	void getTime(int apt, OnNetResponseCallback onNetResponseCallback);
	
	/** 创建或更新 */
	void createOrUpdateTime(int apt,String time, OnNetResponseCallback onNetResponseCallback);
}
