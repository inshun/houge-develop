package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @author it_hu
 *
 *         余额模块Model层
 */
public interface IBalanceModel {
	
	/** 获取收支记录 */
	void getBalanceList(OnNetResponseCallback onNetResponseCallback);

	/** 提现 */
	void withdrawDeposit(double price, String alipay_id, String name,String password,
			OnNetResponseCallback onNetResponseCallback);

	/** 充值 */
	void recharge(int pay_type, double price, OnNetResponseCallback onNetResponseCallback);
}
