package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

/**
 * @author it_huang
 * 
 *         我的技能View层接口
 *
 */
public interface IMySkillPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {
	/**跳转发布技能页面*/
	void jumpToSkillPulish(GoodsDetailEntity detailEntity);
}
