package com.ysp.houge.utility.volley;

/**
 * 响应码
 */
public class ResponseCode {
	/**
	 * @字段：SUCCESS
	 * @功能描述：网络返回成功
	 * @创建人：tyn
	 * @创建时间：2015年7月2日下午5:26:44
	 */
	public static final int SUCCESS = 1;

	/**
	 * @字段：TIP_ERROR
	 * @功能描述：网络返回提示
	 * @创建人：tyn
	 * @创建时间：2015年7月2日下午5:26:51
	 */
	public static final int TIP_ERROR = 0;

	/**
	 * @字段：SYSTEM_ERROR
	 * @功能描述：服务器错误
	 * @创建人：tyn
	 * @创建时间：2015年7月2日下午5:27:05
	 */
	public static final int SYSTEM_ERROR = 2;

	/**
	 * @字段：INVALID_ERROR
	 * @功能描述：token失效,要重新登录
	 * @创建人：tyn
	 * @创建时间：2015年7月18日下午9:19:23
	 */
	public static final int INVALID_ERROR = 3;

	/**
	 * @字段：VOLLEY_ERROR
	 * @功能描述：请求框架返回错误
	 * @创建人：tyn
	 * @创建时间：2015年7月2日下午5:27:31
	 */
	public static final int VOLLEY_ERROR = -1;

	/**
	 * @字段：PARSER_ERROR
	 * @功能描述：解析错误
	 * @创建人：tyn
	 * @创建时间：2015年7月2日下午11:06:38
	 */
	public static final int PARSER_ERROR = -2;

	/**
	 * @字段：SUCCESS_BACK_PARSER_ERROR
	 * @功能描述：返回成功后解析错误
	 * @创建人：tyn
	 * @创建时间：2015年7月4日下午6:50:11
	 */
	public static final int SUCCESS_BACK_PARSER_ERROR = -3;

	/**
	 * @字段：FILE_NOT_FOUND
	 * @功能描述：上传文件时，文件未找到
	 * @创建人：tyn
	 * @创建时间：2015年9月25日下午2:37:51
	 */
	public static final int FILE_NOT_FOUND = -4;
}
