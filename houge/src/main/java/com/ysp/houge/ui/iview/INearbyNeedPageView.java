package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.BannerEntity;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * 描述： 附近需求View层接口
 *
 * @ClassName: INearbyNeedPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月18日 下午6:55:13
 * 
 *        版本: 1.0
 */
public interface INearbyNeedPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 定位 */
	void setDate(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity);

	/** 初始化最新加入 */
	void initNewJion(int count);

	/** 初始化Banner */
	void initBanner(List<BannerEntity> bannerEntities);

	/** 跳转到详情页面 */
	void jumpToNeedDetailPage(int id);

	/** 跳转到最新加入 */
	void jumpToNewJoin(WorkTypeEntity entity);

	/** 跳转到个人详情 */
	void jumpToUserInfo(int id);

	/** 跳转聊一聊 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 分享 */
	void share(GoodsDetailEntity detailEntity);

	/** 加载网页 */
	void jumpToBannerInfo(BannerEntity bannerEntity);

}
