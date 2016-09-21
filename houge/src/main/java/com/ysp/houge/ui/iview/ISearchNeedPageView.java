package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

/**
 * 描述： 搜索需求View层接口
 *
 * @ClassName: ISearchNeedPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月23日 下午10:45:48
 * 
 *        版本: 1.0
 */
public interface ISearchNeedPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

    void setSearchText(String searchText);

	/** 跳转到用户详情页面 */
	void jumpToUserInfoDetailPage(int id);

	/** 跳转到需求详情页面 */
	void jumpToNeedDetailPage(int id);

	/** 聊一聊 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 分享 */
	void share(GoodsDetailEntity goodsDetailEntity);
}
