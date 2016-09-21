package com.ysp.houge.app;

import android.text.TextUtils;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年6月24日上午10:18:39
 * @version 1.0
 */
public class HttpApi {
	/** 登录 */
	public static final String MOBILE_LOGIN = "/site/login";
	/** 注册 */
	public static final String REGISTER = "/site/register";
	/** 环信注册 */
	public static final String IM_REGISTER = "/im/register";
    /** 环信重置密码 */
    public static final String IM_RESETPASS = "/im/changepassword";
	/**环信用户聊天密码重置*/
	public static final String IM_TALK_RESETPASS = "/im/changepassword";

	// 登录和注册流程 start
	/** 找回密码 */
	public static final String FIND_PWD = "/site/findpass";
	/** 获取验证码 */
	public static final String GET_CODE = "/sms/getcode";
	/** 通用文件上传 */
	public static final String UPLOAD_IMAGE = "/upload";
	/** 推送信息收集接口 */
	public static final String PUSH_INFO = "/apns/add"; // 推送信息收集接口
	/** 获取个人信息 */
	public static final String GET_USER_INFO = "/user/home";
	/** 获取其他信息 */
	public static final String GET_OTHER_INFO = "/user/profile";
	// 登录和注册流程 end
	/** 认证 */
	public static final String AUTH = "/user/addverify";
	/** 收支记录 */
	public static final String BALANCE = "/user/changelog";

	// 我的 相关 start
	/** 提现 */
	public static final String WITHDRAW_DEPOSIT = "/cash/add";
	/** 充值 */
	public static final String RECHARGE = "/order/createcharge";
	/** 我购买的 */
	public static final String MY_BUY = "/order/ibought";
	/** 我的需求 */
	public static final String NEED_LIST = "/user/work";
	/** 提现(废弃) */
	// public static final String WITHDRAW_DEPOSIT = "/user/withdrawdo";
	/** 地址列表 */
	public static final String ADDRESS_LIST = "/address/list";
	/** 新增(修改)地址 */
	public static final String EDITE_ADD = "/address/add";
	/** 设置默认地址 */
	public static final String SET_ADDRESS_DEFAULT = "/address/setdefault";
	/** 获取默认地址 */
	public static final String GET_ADDRESS_DEFAULT = "/address/getdefault";
	/** 删除地址 */
	public static final String ADDRESS_DELETE = "/address/delete";
	/** 我卖出的 */
	public static final String MY_SELL = "/order/isell";
	/** 我的技能 */
	public static final String SKILL_LIST = "/user/service";
	/** 时间获取 */
	public static final String TIME_GET = "/user/mytime";
	/** 时间设置 */
	public static final String TIME_CREATE = "/user/my_time_create";
	/** 服务保障介绍 */
	public static final String SERVICE_SAFEGUARD_DESC = "/site/protected";
	/**保证金*/
	public static final String PROTECTEDFEE_MONEY = "/site/protectedfee";
	/**提现协议*/
	public static final String SITE_WITHDRAW = "/site/withdraw";
	/**注册协议*/
	public static final String SITE_ABOUTUSER = "/site/aboutuser";
	/** 提交保证金 */
	public static final String SUBMIT_CASH_DEPOSIT = "/order/createprotect";
	/** 解冻保证金 */
	public static final String UNFREEZE_CASH_DEPOSIT = "/user/unprotect";
	/** 足迹 */
	public static final String MY_FOOTPRINT = "/iview/list";
	/** 推荐码 */
	public static final String INVITE_CODE = "/user/invite";
	///** 我的计数 */
	//public static final String USER_COUNT = "/user/count";
    /** 获取消息列表 */
    public static final String MESSAGE_LIST_INDEX = "/notice/index";
    /** 获取某个具体类型的消息 */
    public static final String MESSAGE_LIST_LIST = "/notice/list";
    /** 新消息计数 */
    public static final String MESSAGE_NEW_COUNT = "/notice/countnew";
	/** 买家发布接口 */
	public static final String PUBLISH_BUYER = "/publish/work";

	// 我的 相关 end

    //消息相关 start
	/** 卖家发布接口 */
	public static final String PUBLISH_SELLER = "/publish/service";
	/** 技能详情 */
	public static final String SKILL_DETAIL = "/service/detail";

	/**技能价格单位*/
	public static final String SKILL_MONEY_UNIT = "/unit/getlists";
	/** 需求详情 */
	public static final String NEED_DETAIL = "/work/detail";
    //消息相关 end

	// 订单相关 start
	/** 赞 */
	public static final String SERVICE_ZAN_ADD = "/zan/set";
	/** 获得技能赞的列表 */
	public static final String SERVICE_ZAN_LIST = "/zan/list";
	/** 评论 */
	public static final String SERVICE_COMMENT_ADD = "/comment/add";
	/** 获得技能评论的列表 */
	public static final String SERVICE_COMMENT_LIST = "/comment/list";
	/** 获得其他技能的列表 */
	public static final String SERVICE_OTHER_LIST = "/service/other";
	/** 举报 */
	public static final String REPORT_ADD = "/report/add";
	/** 订单创建 */
	public static final String CRETE_ORDER = "/order/create";
	/** 订单详情 */
	public static final String ORDER_DETAILS = "/order/detail";
	/** 根据id获取签名 */
	public static final String ORDER_SIGN = "/order/gensign";
	/** 余额支付 */
	public static final String ORDER_BALANCE_PAY = "/order/paybybalance";
	/** 更改订单状态 */
	public static final String ORDER_CHANGE_STATUS = "/order/changestatus";
	/** 评价 */
	public static final String ORDER_RATE = "/order/rate";
	/** 改价 */
	public static final String ORDER_CHANGE_PRICE = "/order/changeorderfee";
    /** 申请退款 */
    public static final String ORDER_REQUEST_REFUND = "/order/requestrefund";
    /** 同意退款 */
    public static final String ORDER_CONFIRM_REFUND = "/order/confirmrefund";
	/** 删除商品 */
	public static final String GOODS_DELETE = "/goods/delete";
	/** 获取所有工种列表 */
	public static final String WORK_TYPE = "/cate/technique";
	/** 获取关注的工种 */
	public static final String CATE_IWATCH = "/cate/iwatch";
	/** 买家修改关注的工种 */
	public static final String RECOMMEND_SKILL = "/skil/watch";
	/** 获取关注列表 */
	public static final String FEED_WATHC = "/feed/watch";
	// 订单相关 end

	// 关注相关 start
	/** 附近的分类 */
	public static final String NEARBY_TYPE = "/cate/nav";
	/** 附近的技能 */
	public static final String NEARBY_SKILL = "/service/all";
	/** 附近的需求 */
	public static final String NEARBY_NEED = "/work/all";
	/** 地图附近的技能 */
	public static final String MAP_NEARBY_SKILL = "/service/map";
	// 关注相关 end

	// 附近相关 start
	/** 地图的需求 */
	public static final String MAP_NEARBY_NEED = "/work/map";
	/** 最新加入数量 */
	public static final String NEW_JOIN_COUNT = "/goods/newcount";
	/** 最新加入列表 */
	public static final String NEW_JOIN_LIST = "/goods/newlist";
	/** 搜索用户 */
	public static final String SEARCH_USER = "/search/user";
	/** 搜索商品 */
	public static final String SEARCH_GOOD = "/search/good";
	/** 消息设置 */
	public static final String APP_SETTING = "/setting/set";
	/** 获取消息设置 */
	public static final String GET_APP_SETTING = "/setting/get";
	// 附近相关 end

	// 搜索相关 start
	/** 获取设置页面app的联系方式 */
	public static final String GET_APP_CONTACT = "/init";
	/** 获取关于我们信息 */
	public static final String GET_APP_ABOUT_US = "/site/aboutus";
	// 搜索相关 end

	// 设置相关 start
	/** 修改个人信息 */
	public static final String EDIT_USERINFO = "/user/edit";
	/** 修改密码 */
	public static final String EDIT_PASSWORD = "/user/changepassword";
	/** 意见反馈 */
	public static final String FEED_BACK = "/site/feedback";
	/**版本控制*/
	public static final String VERSION = "/init/version";

	// 默认schema
	private static final String DEFAULT_SCHEMA = "http";
	// 默认ip
	//api.jiehuolou.com
	//haohuo.phpyiqiwan.
	private static final String DEFAULT_HOST = "test.jiehuolou.com";
	private static final String DEFAULT_CLIENT = "/app";
	// 默认端口号
	private static final String DEFAULT_PORT = "";
	// 设置相关 end
	// 默认URL
	private static final String DEFAULT_URL = DEFAULT_SCHEMA + "://" + DEFAULT_HOST + DEFAULT_PORT;

	// 默认超时时间
	private static final int DEFAULT_TIME_OUT = 30 * 1000;

	@SuppressWarnings("unused")
	private static int RESPONSE_TIME_OUT = DEFAULT_TIME_OUT;

	public static String getAbsPathUrl(String url) {
		return DEFAULT_URL + DEFAULT_CLIENT + url;
	}

	public static String getPictureUrl(String url) {
		if (TextUtils.isEmpty(url)) {
			return url;
		}
		return DEFAULT_URL + url;
	}
}
