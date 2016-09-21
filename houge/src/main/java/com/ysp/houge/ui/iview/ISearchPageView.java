package com.ysp.houge.ui.iview;

import com.ysp.houge.ui.base.BaseFragment;

/**
 * 描述： 搜索页面View层接口
 *
 * @ClassName: ISearchPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月5日 下午3:06:09
 * 
 *        版本: 1.0
 */
public interface ISearchPageView extends IBaseView {

	/** 显示搜索类型的pop */
	void showSearchTypePop();

	/** 修改搜索类型 */
	void changeSearchType(int searchType);

	/** 显示搜索的技能列表 */
	void showHistoryList(BaseFragment fragment);

	/** 显示搜索的技能列表 */
	void showSkillList(BaseFragment fragment, String text);

	/** 显示搜索的需求列表 */
	void showNeedList(BaseFragment fragment, String text);

	/** 显示搜索的用户列表 */
	void showUserList(BaseFragment fragment, String text);
}
