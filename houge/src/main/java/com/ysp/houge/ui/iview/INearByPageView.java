package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * 描述： 附近外层(不包含列表)页面View层接口
 *
 * @ClassName: INearByPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:28:26
 * 
 *        版本: 1.0
 */
public interface INearByPageView extends IBaseView {
	/** 更换登陆身份确认提示框 */
	void showChangeLoginStatusDialog();

	/** 改变登陆身份 */
	void changeLoginStatus();

	/** 切换地图或者列表 应用INearByPresenter 中的常量 */
	void changeMapOrList(int viewType);

	/** 跳转到搜索页面 */
	void jumpToSearch();

	/** 显示其他的关注选项 */
	void showMoreRecommendPop();

	/** 显示排序类型选项 */
	void showSortTypePop(SortTypeEntity chooseSortType);

	/** 加载列表完成后的操作 */
	void loadListFinish(List<WorkTypeEntity> list, List<SortTypeEntity> sort);

	/** 设置下标 这里只适用于下拉展开的时候 */
	void setIndex(int index);
}
