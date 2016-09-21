package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.bean.SortTypeEntity;

/**
 * 描述： 附近外层(不包含列表)页面Presenter层接口
 *
 * @ClassName: INearByPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:29:17
 * 
 *        版本: 1.0
 */
public interface INearByPresenter {
	public static final int VIEW_TYPE_MAP = 0;
	public static final int VIEW_TYPE_LIST = 1;

	/** 改变登陆身份弹窗 */
	void showChangeStatusDialog();

	/** 切换登陆身份 */
	void changeLoginStatus();

	/** 切换地图或则列表 */
	void changeMapOrList();

	/** 搜索 */
	void search();

	/** 加载关注点、排序等数据 */
	void loadListDate();

	/** 展开关注列表 */
	void spreadRecommendList();

	/** 展开的关注列表 点击事件 */
	void ListPopCliclk(int index);

	/** 展开排序列表 */
	void spreadRecommendSort();

	/** 排序列表 点击事件 */
	void SortPopCliclk(int position, SortTypeEntity entity);

}
