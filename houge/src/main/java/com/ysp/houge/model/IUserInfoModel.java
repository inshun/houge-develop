package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

public interface IUserInfoModel {
	/** 获取个人信息 */
	void meInfoRequest(boolean needSave, OnNetResponseCallback onNetResponseCallback);

	/** 修改头像、昵称、性别等 */
	void reviseUserInfo(String avatar, String nick, String sex, OnNetResponseCallback onNetResponseCallback);

	/** 其他用户信息 */
	void getOtherUserInfo(int uid, OnNetResponseCallback onNetResponseCallback);

	// 这两个接口换到了 技能详情里面
	// /** 获取喜欢我的人列表 */
	// void loveMeList(OnNetResponseCallback onNetResponseCallback);
	//
	// /** 所有评论列表 */
	// void levelList(OnNetResponseCallback onNetResponseCallback);

	/** 我购买的 */
	void myBuyer(int page, OnNetResponseCallback onNetResponseCallback);

	/** 需求列表 */
	void getNeedList(int page,int id, String goods_info_ids, OnNetResponseCallback onNetResponseCallback);

	/** 我卖出的 */
	void meSeller(int page,OnNetResponseCallback onNetResponseCallback);

	/** 技能列表 */
	void getSkillList(int page,int id, String goods_info_ids,OnNetResponseCallback onNetResponseCallback);

	/** 提交服务保障金 */
	void submitCashDeposit(OnNetResponseCallback onNetResponseCallback);

	void getProtectedfee(OnNetResponseCallback onNetResponseCallback);

	/** 解冻服务保障金 */
	void unfreezeCashDeposit(OnNetResponseCallback onNetResponseCallback);

	/** 我的足迹(技能) */
	void myFootprintSkill(int page, OnNetResponseCallback onNetResponseCallback);

	/** 我的足迹(需求) */
	void myFootprintNeed(int page, OnNetResponseCallback onNetResponseCallback);

	/** 我的足迹(用户) */
	void myFootprintUser(int page, OnNetResponseCallback onNetResponseCallback);

	/** 获取邀请码 */
	void getShareInfo(OnNetResponseCallback onNetResponseCallback);

    //我的计数接口废弃
    //void getUserCount(OnNetResponseCallback onNetResponseCallback);
}
