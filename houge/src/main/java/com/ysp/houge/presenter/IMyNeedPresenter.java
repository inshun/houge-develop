package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.lisenter.OnItemClickListener;

/**
 * @author it_huang
 * 
 *         我的需求prsenter层接口
 *
 * @param <DATA>
 */
public interface IMyNeedPresenter<DATA> extends IRefreshPresenter<DATA>,OnItemClickListener {

	void serviceClick();
}
