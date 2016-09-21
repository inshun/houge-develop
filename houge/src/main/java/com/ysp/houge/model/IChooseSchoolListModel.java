package com.ysp.houge.model;

import com.ysp.houge.model.entity.httpresponse.SchoolListDataEntity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

public interface IChooseSchoolListModel {
	/**
	 * @描述:网络请求学校列表
	 * @方法名: onRequestSchoolList
	 * @param netResponseCallback
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月13日上午10:34:34
	 * @修改人 tyn
	 * @修改时间 2015年9月13日上午10:34:34
	 * @修改备注
	 * @since
	 * @throws
	 */
	void onRequestSchoolList(OnNetResponseCallback netResponseCallback);

	/**
	 * @描述:从缓存中获取城市列表
	 * @方法名: getSchoolListFromCache
	 * @return
	 * @返回类型 List<SchoolEntity>
	 * @创建人 tyn
	 * @创建时间 2015年9月13日上午10:35:22
	 * @修改人 tyn
	 * @修改时间 2015年9月13日上午10:35:22
	 * @修改备注
	 * @since
	 * @throws
	 */
	SchoolListDataEntity getSchoolListFromCache();
}
