package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @描述: 附近地图页面Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月30日下午7:05:25
 * @version 1.0
 */
public interface INearbyMapPresenter {

	/** 获取页面的工种 */
	void setWorkType(WorkTypeEntity entity);

	/** 获取附近的技能 */
	void getNearBySkillList(double latitude, double langtitude);

	/** 获取附近的需求 */
	void getNearByNeedList(double latitude, double langtitude);
}
