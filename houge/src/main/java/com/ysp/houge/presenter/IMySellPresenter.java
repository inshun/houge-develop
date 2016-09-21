package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;

/**
 * 描述： 我卖出的Presenter层接口
 *
 * @ClassName: IMySellerPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月17日 下午3:27:49
 * 
 *        版本: 1.0
 */
public interface IMySellPresenter<DATA> extends IRefreshPresenter<DATA> {
	void serviceClick();
}
