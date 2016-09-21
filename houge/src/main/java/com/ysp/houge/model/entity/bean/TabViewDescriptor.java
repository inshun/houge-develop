package com.ysp.houge.model.entity.bean;

/**
 * @描述:主页面底部切换栏单个view
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月9日下午2:50:13
 * @version 1.0
 */
public class TabViewDescriptor {
	/**
	 * @字段：tabName
	 * @功能描述：tab名称
	 * @创建人：tyn
	 * @创建时间：2015年8月9日下午2:50:50
	 */
	public String tabName;

	public int tabNameColor;

	/**
	 * @字段：resTabImgId
	 * @功能描述：tab的图标
	 * @创建人：tyn
	 * @创建时间：2015年8月9日下午2:51:10
	 */
	public int resTabImgId;

	/**
	 * @字段：unreadCount
	 * @功能描述：未读数量
	 * @创建人：tyn
	 * @创建时间：2015年8月9日下午2:51:18
	 */
	public int unreadCount;

	/**
	 * @字段：showRedCount
	 * @功能描述：是否显示红点
	 * @创建人：tyn
	 * @创建时间：2015年8月9日下午2:51:35
	 */
	public boolean showRedCount;

	public ShowFirstType showFirstType = ShowFirstType.ShowUnreadCountFirst;

	/**
	 * @描述
	 * @param tabName
	 * @param resTabImgId
	 * @param unreadCount
	 * @param showRedCount
	 */
	public TabViewDescriptor(String tabName, int resTabImgId, int unreadCount,
			boolean showRedCount) {
		super();
		this.tabName = tabName;
		this.resTabImgId = resTabImgId;
		this.unreadCount = unreadCount;
		this.showRedCount = showRedCount;
	}

	public enum ShowFirstType {
		/**
		 * @字段：ShowUnreadCountFirst
		 * @功能描述：优先展示未读数量
		 * @创建人：tyn
		 * @创建时间：2015年8月9日下午2:53:16
		 */
		ShowUnreadCountFirst,
		/**
		 * @字段：ShowRedCountFirst
		 * @功能描述：优先展示红点
		 * @创建人：tyn
		 * @创建时间：2015年8月9日下午2:53:31
		 */
		ShowRedPointFirst
	}
}
