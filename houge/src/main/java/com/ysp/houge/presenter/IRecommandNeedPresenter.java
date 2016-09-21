package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @描述: 卖家推荐页面presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月4日下午4:00:12
 * @version 1.0
 */
public interface IRecommandNeedPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {
	/** 设置当前页面的关注点 */
	void setRecommendAndSortTypeEntity(WorkTypeEntity workTypeEntity,SortTypeEntity sortTypeEntity);
}
