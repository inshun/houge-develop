package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;

/**
 * 描述： 搜索需求Prenter层接口
 *
 * @ClassName: ISearchNeedPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月23日 下午10:50:28
 * 
 *        版本: 1.0
 */
public interface ISearchNeedPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {

	void setSearchText(String string);
}
