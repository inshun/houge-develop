package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * 描述： 关注外层页面(不包含列表的部分)View层接口
 *
 * @ClassName: IRecommandPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:25:51
 * 
 *        版本: 1.0
 */
public interface IRecommandPageView extends IBaseView {
	/** 更换身份的pop */
	void showChangeLoginStatusDialog();

	/** 更换身份 */
	void changeLoginStatus();

	/** 跳转到收藏设置页面 */
	void jumpToRecommendSettingPage();

	/** 显示其他的关注选项 */
	void showMoreRecommendPop();

	/** 显示排序类型选项 */
	void showSortTypePop(SortTypeEntity chooseSortType);

	/** 加载列表完成后的操作 */
	void loadListFinish(List<WorkTypeEntity> list, List<SortTypeEntity> sort);

	/** 设置下标 这里只适用于下拉展开的时候 */
	void setIndex(int index);
}
