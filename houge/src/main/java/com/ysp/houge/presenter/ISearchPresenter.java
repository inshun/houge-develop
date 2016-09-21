package com.ysp.houge.presenter;

import com.ysp.houge.popwindow.SearchTypePopupWindow;
import com.ysp.houge.ui.base.BaseFragment;

/**
 * 描述： 搜索页面Presenter层接口
 *
 * @ClassName: ISearchPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月5日 下午3:36:29
 * 
 *        版本: 1.0
 */
public interface ISearchPresenter extends SearchTypePopupWindow.onTypeChooseListener{

	/** 点击搜索类型 */
	void clickSearchType();
	
	/**搜索*/
	void search(String str,BaseFragment fragment);
}
