package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * @描述: 技能详情页面的Model层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年11月7日下午2:04:11
 * @version 1.0
 */
public interface IGoodsDetailsModel {
	public static final int REPORT_TYPE_NEED = 1;
	public static final int REPORT_TYPE_SKILL = 2;
	public static final int REPORT_TYPE_USER = 3;

	/** 技能详情请求 */
	void requestSkillDetaisl(int id, OnNetResponseCallback onNetResponseCallback);

	/** 需求详情请求 */
	void requestNeedDetaisl(int id, OnNetResponseCallback onNetResponseCallback);

	/** 赞 */
	void requestLoveAdd(int id, OnNetResponseCallback onNetResponseCallback);

	/** 获得赞的列表 */
	void requestLoveList(int id, int page, OnNetResponseCallback onNetResponseCallback);

	/** 发布留言 */
	void requestMessageAdd(int id, String content, String[] at, OnNetResponseCallback onNetResponseCallback);

	/** 获得留言的列表 */
	void requestCommentList(int id, int page, OnNetResponseCallback onNetResponseCallback);

	/** 获得其他技能的列表 */
	void requestOtherList(int page, int user_id, int id, OnNetResponseCallback onNetResponseCallback);

	/** 举报 */
	void requestReport(int type, int type_id, int ReportType, String title, OnNetResponseCallback onNetResponseCallback);

	/** 删除商品 */
	void requestDeleteGoods(int id, OnNetResponseCallback onNetResponseCallback);
}
