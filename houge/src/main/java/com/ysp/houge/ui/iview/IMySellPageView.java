package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.MySellEntity;

/**
 * 描述： 我卖出的View层接口
 *
 * @ClassName: IMySellPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月17日 下午3:29:24
 * 
 * 版本: 1.0
 */
public interface IMySellPageView extends IBaseRefreshListView<List<MySellEntity>> {

	void showCallPhonePop();

	/**跳转订单详情*/
	void jumpToOrderDetails(int position,int operation);
	
	/**跳转用户详情*/
	void jumpToUserDetails(int position);
	
	/**跳转技能详情*/
	void jumpToSkillDetails(int position);
	
	/**跳转技能详情*/
	void jumpToNeedDetails(int position);

	/*从我卖出的列表跳转到聊天页面*/
	void jumpToCharPageTwo(int position);
}
