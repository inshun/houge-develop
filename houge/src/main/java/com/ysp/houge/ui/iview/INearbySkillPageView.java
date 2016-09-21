package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.BannerEntity;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.SortTypeEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;

/**
 * 描述： 附近技能列表页面View层接口
 *
 * @ClassName: INearbySkillPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月4日 下午2:33:19
 * 
 *        版本: 1.0
 */
public interface INearbySkillPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 设置页面实体 */
	void setDate(WorkTypeEntity workTypeEntity, SortTypeEntity sortTypeEntity);

	/** 初始化最新加入 */
	void initNewJion(int count);

	/** 初始化Banner */
	void initBanner(List<BannerEntity> bannerEntities);

	/** 初始化附近推荐技能 */
	void initNearby(List<GoodsDetailEntity> list);

	/** 跳转到详情页面 */
	void jumpToSkillDetailPage(int id);

	/** 跳转到最新加入 */
	void jumpToNewJoin(WorkTypeEntity entity);

	/** 跳转到个人详情 */
	void jumpToUserInfo(int id);

	/** 跳转聊一聊 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 分享 */
	void share(GoodsDetailEntity detailEntity);

	/** banner点击*/
	void jumpToBannerInfo(BannerEntity bannerEntity);

}
