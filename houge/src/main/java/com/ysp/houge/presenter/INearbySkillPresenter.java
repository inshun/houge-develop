package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * 描述： 附近技能列表Presenter层接口
 *
 * @ClassName: INearbySkillPagePresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:34:13
 * 
 * 版本: 1.0
 */
public interface INearbySkillPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {

	/** 加载头部数据 */
	void loadHeadData();

	/** 设置当前页面关注点对象 */
	void setNearbyEntity(WorkTypeEntity workTypeEntity,SortTypeEntity sortTypeEntity);

	/** 最新加入点击 */
	void newJion();

	/** 牛人Banner点击 */
	void genius(int index);
	
	/** 牛人Banner点击 */
	void recommned(int index);

}
