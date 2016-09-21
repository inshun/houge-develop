package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

import java.util.List;

/**
 * 描述： 新加入技能View层接口
 *
 * @ClassName: INewJoinSkillPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月5日 上午10:30:24
 * 
 *        版本: 1.0
 */
public interface INewJoinSkillPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {
	void jumpToUserInfo(int id);

	void jumpToSkillDetails(int id);
	
	void jumpToNeedDetails(int id);

	void jumpToHaveATalk(ChatPageEntity chatPageEntity);

	void share(GoodsDetailEntity goodsDetailEntity);
}
