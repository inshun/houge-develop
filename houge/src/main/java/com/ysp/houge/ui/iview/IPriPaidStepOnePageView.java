package com.ysp.houge.ui.iview;

/**
 * @描述:预支工资页面的MVP模式中的view层的接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月12日下午2:08:52
 * @version 1.0
 */
public interface IPriPaidStepOnePageView extends IBaseView {

	void jumpToNextPage(String advanceMoney, String collectionAccount);

	void showNeedAuthDialog();
}
