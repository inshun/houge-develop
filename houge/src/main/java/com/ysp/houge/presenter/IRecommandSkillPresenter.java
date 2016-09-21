package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * @描述: 买家推荐页面presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月11日下午5:26:26
 * @version 1.0
 */
public interface IRecommandSkillPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {
	/** 设置当前页面的关注点 */
	void setRecommendAndSortTypeEntity(WorkTypeEntity workTypeEntity,SortTypeEntity sortTypeEntity);
	void loadData();
}
