package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;

/**
 * @描述: 卖家发布页面Presenter 层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月3日上午11:06:48
 * @version 1.0
 */
public interface ISkillPulishPresenter {
	/** 加载设置的时间 */
	void getSetTime();

    /**加载设置的地点*/
    void getSetAddress();

	/** 添加图片返回 */
	void choosePicBack(String picPath);
	
	void delImage(int position);

	/** 改变免薪状态 */
	void changeFreeStatus();

	/** 更改工作餐状态 */
	void changeWorkLunchStatus();

	/** 更改住宿状态 */
	void changeStayStatus();

	/** 提交 */
	void submit(String skillTitle, String skillDesc, String price, String unit);

	/** 收起 */
	void pickUp();
	
	/**设置时间完成*/
	void setTimeFinish();

    /**添加默认地址*/
    void addAddress(LocationChooseEventBusEntity locationEntity);


	/*技能金额单位*/
	void getSkillMoneyUnit();
}
