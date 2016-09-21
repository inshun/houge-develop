package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

/**
 * @author it_huang
 *
 *         我的足迹View层接口
 */
public interface INeedFootprintPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 跳转需求详情 */
	void jumpToNeedDetails(int id);

	/** 跳转需求详情 */
	void jumpToUserDetails(int id);

	/** 跳转需求详情 */
	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	/** 跳转需求详情 */
	void share(GoodsDetailEntity entity);
}
