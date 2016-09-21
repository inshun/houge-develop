package com.ysp.houge.presenter;

/**
 * 描述： 支付页面Presenter层接口
 *
 * @ClassName: IPayPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月30日 上午10:15:34
 * 
 *        版本: 1.0
 */
public interface IPayPresenter {
	/** 设置订单号 */
	void setOrderId(String order_id);

	/** 客服电话 */
	void clickEQS();
	
	/**支付*/
	void pay(int payType);
}
