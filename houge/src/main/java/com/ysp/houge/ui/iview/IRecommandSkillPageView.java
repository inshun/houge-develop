package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @描述: 买家推荐列表页面的view层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月16日下午3:37:18
 * @version 1.0
 */
public interface IRecommandSkillPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 设置页面的信息 */
	void setDate(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity);

	/** 跳转到用户详情页面 */
	void jumpToUserInfoDetailPage(int id);

	/** 跳转到技能详情页面 */
	void jumpToSkillDetailPage(int id);

	/** 聊一聊 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 分享 */
	void share(GoodsDetailEntity detailEntity);
}
