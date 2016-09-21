package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.TimeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

import android.os.Bundle;

/**
 * @描述: 买家关注设置View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月4日下午7:58:29
 * @version 1.0
 */
public interface IRecommendSkillSettingPageView extends IBaseView {
	/** 刷新工种列表的数据 */
	void notifyListDate(List<WorkTypeEntity> entities);

	/** 刷新工种列表的数据 */
	void notifyListDate(String string);

	/** 刷新服务时间列表 */
	void notifyServiceTimeDate(List<TimeEntity> entities);

	/** 设置完成,返回首页 */
	void jumpToHome(boolean isRefresh);

	/** 前往能力列表页面 */
	void jumpToSkillList();

	/** 跳转到服务时间页面 */
	void jumpToTimeManager(Bundle bundle);

}
