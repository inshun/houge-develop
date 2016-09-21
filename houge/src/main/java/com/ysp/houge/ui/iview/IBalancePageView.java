package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.BalanceEntity;

/**
 * @author it_hu
 * 
 *         余额View层接口
 *
 */
public interface IBalancePageView extends IBaseRefreshListView<List<BalanceEntity>> {
	void showBalance(String balance);

	void jumpToWithdrawDeposit();
	
	void jumpToTopUp();
}
