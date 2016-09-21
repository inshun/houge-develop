package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * 描述： 附近需求的Presenter层接口
 *
 * @ClassName: INearbyNeedPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月18日 下午6:57:50
 * 
 * 版本: 1.0
 */
public interface INearbyNeedPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {

	/** 加载头部数据 */
	void loadHeadData();

	/** 设置当前页面关注点对象 */
	void setNearbyEntity(WorkTypeEntity workTypeEntity,SortTypeEntity sortTypeEntity);
	
	/** 最新加入点击 */
	void newJion();
	
	/** 牛人Banner点击 */
	void bannerClick(int index);

}
