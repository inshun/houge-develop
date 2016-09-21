package com.ysp.houge.presenter;

/**
 * @描述: 我的买家页面 Presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月12日下午1:34:24
 * @version 1.0
 */
public interface IMePresenter {
	/** 显示更换身份对话框 */
	void showChangeStatusPop();

	/** 更换买卖家身份 */
	void changeLoginStutus();

	/** 系统 */
	void onSystemClick();

	/** 获取用户信息 */
	void getUserInfo();

	/** 个人信息点击 */
	void onUserInfoCliclk();

	/** 余额点击 */
	void onBalanceClick();

	/** 喜欢我的人本点击 */
	void onLoveMeClick();

	/** 评论被点击 */
	void onAllCommentClick();

	/** 我的足迹 */
	void onMyFootprintClick();

	/** 分享赚钱 */
	void onMakeMoneyByShareClick();
	
	/** 我购买的 */
	void onMeBuyerClick();
	
	/** 我的需求 */
	void onMyDemandClick();

	/** 地址管理 */
	void onAddressManagerClick();

	/** 我卖出的 */
	void onMeSellerClick();

	/** 我的技能 */
	void onMySkillClick();

	/** 时间管理 */
	void onTimeManagerClick();

    /** 设置常驻地 */
	void onResidentAddressClick();

	/** 服务保障 */
	void onServiceSafeguardClick();

	/** 预支工资  */
	void onAdvanceOfWagesClick();
}
