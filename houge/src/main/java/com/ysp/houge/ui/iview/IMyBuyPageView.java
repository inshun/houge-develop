package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.MyBuyEntity;

import java.util.List;

/**
 * 描述： 我购买的View层接口
 *
 * @ClassName: IMeBuyPageView
 *
 * @author: hx
 *
 * @date: 2015年12月13日 上午10:51:33
 *
 * 版本: 1.0
 */
public interface IMyBuyPageView extends IBaseRefreshListView<List<MyBuyEntity>> {

	/**跳转订单详情*/
	void jumpToOrderDetails(int position,int operation);

	/**跳转用户详情*/
	void jumpToUserDetails(int position);

	/**跳转技能详情*/
	void jumpToSkillDetails(int position);

	/**跳转技能详情*/
	void jumpToNeedDetails(int position);

	/*在技能列表跳转到聊天页面*/
	void jumpToCharPageOne(int position);
}
