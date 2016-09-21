package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.SkillMoneyUnitEntity;

import java.util.List;

/**
 * @描述: 卖家发布页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月2日下午3:33:52
 * @version 1.0
 */
public interface ISkillPulishPagerView extends IBaseView {

	/** 显示添加图片的dialog */
	void showAddPicDialog();

	/** 加载图片 */
	void loadImg(List<String> picList);

	/** 改变免薪实习状态 */
	void changeFreeStatus(boolean isFree);

	/** 改变工作餐状态 */
	void changeWorkLunchStatus(boolean isWorkLunch);
	
	/** 改变住宿状态 */
	void changeStayStatus(boolean isStay);
	
	/** 提交 */
	void submitSucces();
	
	/** 收起 */
	void pickUp();

    /**跳转时间设置*/
	void jumToTimeManager();

    /**跳转获取经纬度，设置常驻地*/
    void jumpToMap();

	//跳转到首页
	void jumpToHomeActivity();

	void progresshide();

	void progress();
	/*获取价格单位*/
	void getMoneyUnit(List<SkillMoneyUnitEntity> entities);
}
