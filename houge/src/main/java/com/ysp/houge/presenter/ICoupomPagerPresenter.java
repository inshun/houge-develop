package com.ysp.houge.presenter;

/** 
 * @描述:优惠券页面presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月5日下午12:45:39
 * @version 1.0
 */
public interface ICoupomPagerPresenter {
	/**
	 * @描述: 设置头部选中的下表
	 * @方法名: setHeadIndex
	 * @param index
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年10月5日下午12:57:29
	 * @修改人 tyn
	 * @修改时间 2015年10月5日下午12:57:29
	 * @修改备注
	 * @since
	 * @throws
	 */
	void setHeadIndex(int index);
	
	/**
	 * @描述:  使用
	 * @方法名: use
	 * @param id 使用的优惠券编号
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年10月5日下午1:04:46
	 * @修改人 tyn
	 * @修改时间 2015年10月5日下午1:04:46
	 * @修改备注
	 * @since
	 * @throws
	 */
	void use(String id);
	
	/**
	 * @描述: 取消
	 * @方法名: cancle
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年10月5日下午1:05:18
	 * @修改人 tyn
	 * @修改时间 2015年10月5日下午1:05:18
	 * @修改备注
	 * @since
	 * @throws
	 */
	void cancle();
}
