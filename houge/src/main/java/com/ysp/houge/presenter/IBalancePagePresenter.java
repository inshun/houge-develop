package com.ysp.houge.presenter;

import com.tyn.view.IRefreshPresenter;

/**
 * @author it_hu
 *
 *         余额页面Presenter层
 * @param <DATA>
 */
public interface IBalancePagePresenter<DATA> extends IRefreshPresenter<DATA> {
	void loadBalance();

	void clickWithdrawDeposit();

	void clickTopUp();

	void onItemClick(int position);
}
