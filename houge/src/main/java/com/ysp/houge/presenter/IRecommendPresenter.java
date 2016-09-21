package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.bean.SortTypeEntity;

/**
 * 描述： 关注外层页面(不包含列表的部分)presenter层接口
 *
 * @ClassName: IRecommendPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:26:42
 * 
 *        版本: 1.0
 */
public interface IRecommendPresenter {

	/** 显示切换身份提升 */
	void showChangeStatusPop();

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

	/** 改变登陆身份 */
	void changeLoginStutus();

	/** 设置偏好 */
	void settingRecommend();
}
