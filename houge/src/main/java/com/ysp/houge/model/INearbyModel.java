package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * 描述： 附近model层接口
 *
 * @ClassName: INearbyModel
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午3:54:26
 * 
 *        版本: 1.0
 */
public interface INearbyModel {
	/** 获取附近分类 */
	void getWorkType(OnNetResponseCallback onNetResponseCallback);

	/** 获取附近技能数据 */
	void getNearbySkillList(int page, int id, int order, String gps, OnNetResponseCallback onNetResponseCallback);

	/** 获取附近需求数据 */
	void getNearbyNeedList(int page, int id, int order, String gps, OnNetResponseCallback onNetResponseCallback);

	/** 获取地图附近技能数据 */
	void getMapNearbySkillList(int id, String gps, OnNetResponseCallback onNetResponseCallback);

	/** 获取地图附近需求数据 */
	void getMapNearbyNeedList(int id, String gps, OnNetResponseCallback onNetResponseCallback);

	/** 获取最新加入技能数量 */
	void getNewJoinSkillCount(int type, OnNetResponseCallback onNetResponseCallback);

	/** 获取最新加入技能数据 */
	void getNewJoinSkillList(int page,int type, OnNetResponseCallback onNetResponseCallback);
}
