package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;

/**
 * @描述: 我的 买家页面View层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月12日下午1:33:42
 * @version 1.0
 */
public interface IMePageView extends IBaseView {

    /**设置未读消息实体*/
    void setMessageCount(MessageCountEntity entity);

	/** 显示更换身份的对话框 */
	void showChangeLoginStatusDialog();

	/** 改变买卖家角色 */
	void changeLoginStatus();

	/** 跳转到系统 */
	void jumpToSystem();

	/** 显示用户 信息 */
	void showUserInfo(UserInfoEntity info);

	/** 账户信息 */
	void jumpToAccountInfo();

	/** 余额页面 */
	void jumpToBalance();

	/** 喜欢我的页面 */
	void jumpToLoveMe();

	/** 评论列表页面 */
	void jumpToAllComment();

	/** 我的足迹页面 */
	void jumpToMyFootprint();

	/** 分享赚钱页面 */
	void jumpToMakeMoneyByShare();

	/** 我购买的 */
	void jumpToMeBuy();

	/** 我的需求 */
	void jumpToMeNeed();

	/** 地址管理页面 */
	void jumpToAddressManager();

	/** 我卖出的 */
	void jumpToMeSeller();

	/** 我的技能页面 */
	void jumpToMeSkill();

	/** 时间管理页面 */
	void jumpToTimeManager();

    /** 时间管理页面 */
	void jumpToResidentAddress();

	/** 预支工资 */
	void jumpToAdvanceSalary();
	
	/** 提交服务保障金 */
	void jumpToApplyServiceSafeguard();
	
	/** 申请解冻 */
	void jumpToUnfreezeServiceSafeguard();

}
