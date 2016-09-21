package com.ysp.houge.lisenter;

public interface OnItemClickListener {
	/** 用户详情 */
	public static final int CLICK_OPERATION_USER_DETAIL = 0;
	/** 技能详情 */
	public static final int CLICK_OPERATION_SKILL_DETAIL = 1;
	/** 需求详情 */
	public static final int CLICK_OPERATION_NEED_DETAIL = 2;
	/** 订单详情 */
	public static final int CLICK_OPERATION_ORDER_DETAIL = 3;
	/** 聊一聊 */
	public static final int CLICK_OPERATION_HAVE_A_TALK = 4;
	/** 分享 */
	public static final int CLICK_OPERATION_SHARE = 5;
	/** 删除 */
	public static final int CLICK_OPERATION_DELETE = 6;
	/** 订单操作 */
	public static final int CLICK_OPERATION_ORDER_FUNCTION = 7;
    /** 打电话给卖家 */
	public static final int CLICK_OPERATION_CALL_TO_SALE = 8;
    /** 发IM消息给卖家 */
	public static final int CLICK_OPERATION_IM_TO_SALE = 9;
    /** 打电话给买家 */
	public static final int CLICK_OPERATION_CALL_TO_BUY = 10;
    /** 发IM消息给买家 */
	public static final int CLICK_OPERATION_IM_TO_BUY = 11;

	void OnClick(int position, int operation);
}