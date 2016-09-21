package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;

/**
 * 描述： 搜索用户Presenter称接口
 *
 * @ClassName: ISearchUserPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月9日 上午10:29:37
 * 
 *        版本: 1.0
 */
public interface ISearchUserPresenter<DATA> extends IRefreshPresenter<DATA> {
	
	void setSearchText(String text);

}
