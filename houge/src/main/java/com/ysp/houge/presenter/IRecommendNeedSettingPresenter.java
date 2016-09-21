package com.ysp.houge.presenter;

import com.baidu.location.BDLocation;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;

/**
 * @描述:工种偏好设置的P层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月22日上午11:59:15
 * @version 1.0
 */
public interface IRecommendNeedSettingPresenter {

	/** 获取之前的设置 */
	void getSetting();

	/** 添加工种 */
	void addSkillList();

	/** 添加完工种列表返回 */
	void finishAddSkillList(WorkTypeSetFinishEventBusEntity busEntity);

	/** 获得已经设置的地点坐标 */
	void getAddress();

	/** 定位回掉 */
	void onReceiveLocation(BDLocation location);

	/** 点击地图 */
	void onMapClick();

	/** 获得已经设置的地点坐标 */
	void getServiceDistance();

	/** 地图取点返回信息 */
	void chooseMapFinish(LocationChooseEventBusEntity locationChooseEventBusEntity);
	
	/**修改服务范围*/
	void setServiceArea(int serviceArea);

	/** 设置完成 */
	String setFinish();
}
