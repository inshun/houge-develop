package com.ysp.houge.utility.volley;

/**
 * @描述:网络返回时的回调
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月25日下午2:38:10
 * @version 2.4
 */
public interface OnNetResponseCallback {

	/**
	 * @描述:成功返回
	 * @方法名: onSuccess
	 * @param data
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月25日下午2:38:26
	 * @修改人 tyn
	 * @修改时间 2015年9月25日下午2:38:26
	 * @修改备注
	 * @since
	 * @throws
	 */
	void onSuccess(Object data);

	/**
	 * @描述:失败时返回错误码和错误提示信息
	 * @方法名: onError
	 * @param errorCode
	 * @param message
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月25日下午2:38:31
	 * @修改人 tyn
	 * @修改时间 2015年9月25日下午2:38:31
	 * @修改备注
	 * @since
	 * @throws
	 */
	void onError(int errorCode, String message);
}
