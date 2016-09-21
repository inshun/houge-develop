package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @描述: 关注Model层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月4日下午1:43:28
 * @version 1.0
 */
public interface IRecommendModel {
	/** 获取关注的工种列表 */
	void getWorkTypeList(OnNetResponseCallback onNetResponseCallback);

	/** 获取排序类型 */
	void getSorTypetList(OnNetResponseCallback onNetResponseCallback);

	/** 获取关注商品列表 */

	void getRecommendList(int id,int page
			, int skil_id, int order,String gps
			, OnNetResponseCallback onNetResponseCallback);
	void getRecommendListFromLocal(int id,int page
			, int skil_id, int order,String gps
			, OnNetResponseCallback onNetResponseCallback);
}
