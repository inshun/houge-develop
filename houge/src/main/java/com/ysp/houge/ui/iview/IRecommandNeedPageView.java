package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @描述: 卖家推荐列表页面的view层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月4日下午1:14:22
 * @version 1.0
 */
public interface IRecommandNeedPageView extends IBaseRefreshListView<List<GoodsDetailEntity>>  {

	/** 设置页面实体 */
	void setDate(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity);

	/** 跳转到用户详情页面 */
	void jumpToUserInfoDetailPage(int id);

	/** 跳转到需求详情页面 */
	void jumpToNeedDetailPage(int id);

	/** 聊一聊 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 分享 */
	void share(GoodsDetailEntity detailEntity);
}
