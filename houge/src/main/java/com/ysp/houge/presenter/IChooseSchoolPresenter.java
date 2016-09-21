package com.ysp.houge.presenter;

import android.view.View;
import android.widget.AdapterView;

/**
 * @描述:选择学校的MVP模式中的presenter层的接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月4日下午8:45:25
 * @version 1.0
 */
public interface IChooseSchoolPresenter {

	/**
	 * @描述:获取学校列表
	 * @方法名: getSchoolListRequest
	 * @param cityId
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年7月18日下午7:19:30
	 * @修改人 tyn
	 * @修改时间 2015年7月18日下午7:19:30
	 * @修改备注
	 * @since
	 * @throws
	 */
	void getSchoolListRequest(int cityId);

	/**
	 * @描述:选择学校
	 * @方法名: clickListItem
	 * @param parent
	 * @param v
	 * @param position
	 * @param id
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月13日上午11:16:45
	 * @修改人 tyn
	 * @修改时间 2015年9月13日上午11:16:45
	 * @修改备注
	 * @since
	 * @throws
	 */
	void clickListItem(AdapterView<?> parent, View v, int position, long id);
}
