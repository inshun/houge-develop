package com.ysp.houge.app;

import android.annotation.SuppressLint;

public class Constants {
	/**
	 * @字段：REQUEST_SIZE
	 * @功能描述：网络请求列表项的数量
	 * @创建人：tyn
	 * @创建时间：2015年6月26日下午1:34:27
	 */
	public static final int REQUEST_SIZE = 20;

	/**
	 * @字段：DB_PATH
	 * @功能描述：缓存路径
	 * @创建人：tyn
	 * @创建时间：2015年6月30日下午7:58:48
	 */
	@SuppressLint("SdCardPath")
	public static final String DB_PATH = "/data/data/com.ysp.houge/databases";
	
	public static final int CHAT_TYPE_PLAIN_FROM = 0;
	public static final int CHAT_TYPE_PLAIN_TO = 1;
	public static final int CHAT_TYPE_IMG_FROM = 2;
	public static final int CHAT_TYPE_IMG_TO = 3;

	/*Intent 传值KEY*/
	public static final String KEY_MUST_UP_DATA = "must_updata";//0代表不必须升级 1 代表必须升级
	public static final String KEY_UP_DATA_INFO = "updatainfo";
	public static final String KEY_IS_SHOW_UP_DATA_DIALOG ="vertion";
}
