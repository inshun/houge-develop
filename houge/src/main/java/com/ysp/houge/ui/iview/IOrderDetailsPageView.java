package com.ysp.houge.ui.iview;

/**
 * 描述： 订单详情View层接口
 *
 * @ClassName: IOrderDetailsPageView
 * 
 * @author: hx
 * 
 * @date: 2015年12月15日 下午3:03:08
 * 
 *        版本: 1.0
 */
public interface IOrderDetailsPageView extends IBaseView {
	
	/**显示拨打客服电话提示框*/
	void showCallPhoneDialog(String esqNum);
	
	void serIndex(int index);
}
