package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;

/**
 * @描述: 买家关注设置presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月4日下午8:47:00
 * @version 1.0
 */
public interface IRecommendSkillSttingPresneter {

	/** 获取设置的服务时间 */
	void getServiceTime();

	/** 设置完成 */
	void setFinish();

	/** 能力列表 */
	void addSkillList();

	/** 添加关注点完成后 */
	void finishAddSkillList(WorkTypeSetFinishEventBusEntity busEntity);

	/** 设置服务时间 */
	void setServiceTime();

	void setServiceTimeFinish(String string);

}
