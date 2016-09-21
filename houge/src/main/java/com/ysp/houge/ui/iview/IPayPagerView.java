package com.ysp.houge.ui.iview;

/**
 * @描述: 支付页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年9月29日下午5:09:22
 * @version 1.0
 */
public interface IPayPagerView extends IBaseView {
	/** 客服电话 */
	void showEQSPop();

	/** 显示信息 */
	void showPayInfo(String money, String title, String name);

	/** 支付宝 */
	void aliPay(String sign);
}
