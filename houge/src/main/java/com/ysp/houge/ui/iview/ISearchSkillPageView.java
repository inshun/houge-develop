package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

/**
 * 描述： 搜索技能View层
 *
 * @ClassName: ISearchSkillPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 上午11:27:02
 * 
 * 版本: 1.0
 */
public interface ISearchSkillPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

    void setSearchText(String searchText);

	/** 跳转到用户详情页面 */
	void jumpToUserInfoDetailPage(int id);

	/** 跳转到需求详情页面 */
	void jumpToSkillDetailPage(int id);

	/** 聊一聊 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 分享 */
	void share(GoodsDetailEntity goodsDetailEntity);
}
