package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;

import java.util.List;

/**
 * @描述: 工种列表页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月22日下午8:35:47
 * @version 1.0
 */
public interface IWorkTypeListPageView extends IBaseView {
	/** 刷新页面数据 */
	void setList(List<WorkTypeEntity> entities);

	/** 提交 */
	void submit(WorkTypeSetFinishEventBusEntity busEntity);

	/**第一次进来跳转到发布技能页面*/
	void jumpToSkillPulishActivity();

	void jumpToNeedPulishActivity();
}
