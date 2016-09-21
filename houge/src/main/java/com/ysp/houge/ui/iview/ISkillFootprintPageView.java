package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

/**
 * @author it_hu
 *
 *         我的足迹技能列表
 */
public interface ISkillFootprintPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {
	/** 跳转到技能详情页面 */
	void jumpToSkillDetailPage(int id);

	/** 跳转到个人详情 */
	void jumpToUserInfo(int id);

	/** 跳转聊一聊 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 分享 */
	void share(GoodsDetailEntity detailEntity);
}
