package com.ysp.houge.utility.volley;

import com.android.volley.Request.Method;

import java.io.Serializable;
import java.util.Map;

/**
 * @描述:网络请求时的参数配置
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月8日上午10:07:57
 * @version 1.0
 */
public class Request<T> implements Serializable, Comparable<Request<T>> {
	/**
	 * @字段：serialVersionUID
	 * @功能描述：
	 * @创建人：tyn
	 * @创建时间：2015年3月16日下午1:20:09
	 */
	private static final long serialVersionUID = -3884193219892207245L;
	/**
	 * @字段：needDecryption
	 * @功能描述：返回数据是否需要解密
	 * @创建人：tyn
	 * @创建时间：2015年7月8日上午10:11:58
	 */
	private boolean needDecryption;

	/**
	 * @字段：needEncryption
	 * @功能描述：请求参数是否需要加密
	 * @创建人：tyn
	 * @创建时间：2015年7月8日上午10:12:54
	 */
	private boolean needEncryption;

	/**
	 * @字段：needToken
	 * @功能描述：是否需要加入token字段
	 * @创建人：tyn
	 * @创建时间：2015年7月13日下午6:49:25
	 */
	private boolean needToken;

	/**
	 * @字段：url
	 * @功能描述：请求的URL
	 * @创建人：tyn
	 * @创建时间：2015年7月8日上午10:08:10
	 */
	private String url;

	/**
	 * @字段：type
	 * @功能描述：请求类型，1表示post请求，0表示get请求
	 * @创建人：tyn
	 * @创建时间：2015年3月16日下午1:20:28
	 */
	private int type;

	/**
	 * @字段：clazz
	 * @功能描述：返回的数据类型
	 * @创建人：tyn
	 * @创建时间：2015年3月16日下午1:21:40
	 */
	private Class<T> clazz;
	/**
	 * @字段：backType
	 * @功能描述：响应数据的返回类型
	 * @创建人：tyn
	 * @创建时间：2015年3月16日下午1:21:55
	 */
	private BackType backType;

	/**
	 * @字段：map
	 * @功能描述：请求参数
	 * @创建人：tyn
	 * @创建时间：2015年7月8日上午10:09:30
	 */
	private Map<String, String> map;

	/**
	 * @字段：timeout
	 * @功能描述：超时时间
	 * @创建人：tyn
	 * @创建时间：2015年7月8日上午10:09:39
	 */
	private int timeout;

	private Priority priority;

	/**
	 * @字段：needCache
	 * @功能描述：是否需要缓存
	 * @创建人：tyn
	 * @创建时间：2015年9月13日上午10:42:24
	 */
	private boolean needCache;

	private String cachePath;

	public Request() {

	}

	/**
	 * @描述
	 * @param url
	 *            请求的地址
	 * @param map
	 *            请求的参数
	 * @param clazz
	 *            返回数据转换类
	 * @param backType
	 *            返回数据的类型(BEAN, LIST, STRING, JSONELEMENT)
	 * @param needToken
	 *            请求时是否需要加入token
	 */
	public Request(String url, Map<String, String> map, Class<T> clazz,
			BackType backType, boolean needToken) {
		this(Method.GET, url, map, clazz, backType, needToken);
	}

	public Request(String url, Map<String, String> map, Class<T> clazz,
			BackType backType, boolean needCache, String cachePath,
			boolean needToken) {
		this(Method.GET, url, map, clazz, backType, needCache, cachePath,
				needToken);
	}

	public Request(int type, String url, Map<String, String> map,
			Class<T> clazz, BackType backType, boolean needToken) {
		this.type = type;
		this.url = url;
		this.clazz = clazz;
		this.map = map;
		this.backType = backType;
		this.needToken = needToken;
		this.priority = Priority.NORMAL;
		this.timeout = 30 * 1000;
	}

	public Request(int type, String url, Map<String, String> map,
			Class<T> clazz, BackType backType, boolean needCache,
			String cachePath, boolean needToken) {
		this.type = type;
		this.url = url;
		this.clazz = clazz;
		this.map = map;
		this.backType = backType;
		this.needToken = needToken;
		this.cachePath = cachePath;
		this.needCache = needCache;
		this.priority = Priority.NORMAL;
		this.timeout = 30 * 1000;
	}

	/**
	 * @return the needDecryption
	 */
	public boolean isNeedDecryption() {
		return needDecryption;
	}

	/**
	 * @param needDecryption
	 *            the needDecryption to set
	 */
	public void setNeedDecryption(boolean needDecryption) {
		this.needDecryption = needDecryption;
	}

	/**
	 * @return the needEncryption
	 */
	public boolean isNeedEncryption() {
		return needEncryption;
	}

	/**
	 * @param needEncryption
	 *            the needEncryption to set
	 */
	public void setNeedEncryption(boolean needEncryption) {
		this.needEncryption = needEncryption;
	}

	/**
	 * @return the needToken
	 */
	public boolean isNeedToken() {
		return needToken;
	}

	/**
	 * @param needToken
	 *            the needToken to set
	 */
	public void setNeedToken(boolean needToken) {
		this.needToken = needToken;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the clazz
	 */
	public Class<T> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz
	 *            the clazz to set
	 */
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the backType
	 */
	public BackType getBackType() {
		return backType;
	}

	/**
	 * @param backType
	 *            the backType to set
	 */
	public void setBackType(BackType backType) {
		this.backType = backType;
	}

	/**
	 * @return the map
	 */
	public Map<String, String> getMap() {
		return map;
	}

	/**
	 * @param map
	 *            the map to set
	 */
	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	/**
	 * @return the timeout
	 */
	public int getTimeout() {
		return timeout;
	}

	/**
	 * @param timeout
	 *            the timeout to set
	 */
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	/**
	 * @return the priority
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * @return the needCache
	 */
	public boolean isNeedCache() {
		return needCache;
	}

	/**
	 * @param needCache
	 *            the needCache to set
	 */
	public void setNeedCache(boolean needCache) {
		this.needCache = needCache;
	}

	/**
	 * @return the cachePath
	 */
	public String getCachePath() {
		return cachePath;
	}

	/**
	 * @param cachePath
	 *            the cachePath to set
	 */
	public void setCachePath(String cachePath) {
		this.cachePath = cachePath;
	}

	@Override
	public int compareTo(Request<T> other) {
		Priority left = this.getPriority();
		Priority right = other.getPriority();

		// High-priority requests are "lesser" so they are sorted to the front.
		// Equal priorities are sorted by sequence number to provide FIFO
		// ordering.
		return 0;
	}

	/**
	 * @描述:解析类型
	 * @Copyright Copyright (c) 2015
	 * 
	 * @author tyn
	 * @date 2015年7月8日上午10:14:16
	 * @version 1.0
	 */
	public enum BackType {
		/**
		 * @字段：BEAN
		 * @功能描述：网络返回bean对象
		 * @创建人：tyn
		 * @创建时间：2015年9月25日下午2:36:07
		 */
		BEAN,
		/**
		 * @字段：LIST
		 * @功能描述：网络返回列表形式
		 * @创建人：tyn
		 * @创建时间：2015年9月25日下午2:36:35
		 */
		LIST,
		/**
		 * @字段：STRING
		 * @功能描述：网络返回string
		 * @创建人：tyn
		 * @创建时间：2015年9月25日下午2:37:05
		 */
		STRING,
		/**
		 * @字段：JSONELEMENT
		 * @功能描述：网络返回json
		 * @创建人：tyn
		 * @创建时间：2015年9月25日下午2:37:19
		 */
		JSONELEMENT;
	}

	public enum Priority {
		LOW, NORMAL, HIGH, IMMEDIATE
	}

}
