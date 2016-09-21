package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.SchoolEntity;

/**
 * @描述:选择学校页面的MVP模式中的view层的接口
 * @Copyright Copyright (c) 2015
 * @Company .
 * 
 * @author tyn
 * @date 2015年7月4日下午8:35:47
 * @version 1.0
 */
public interface IChooseSchoolView extends IBaseView {

	/**
	 * @描述:将所有的学校的数据加入列表
	 * @方法名: setAllSchoolList
	 * @param schoolEntities
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年7月13日下午10:44:22
	 * @修改人 tyn
	 * @修改时间 2015年7月13日下午10:44:22
	 * @修改备注
	 * @since
	 * @throws
	 */
	void setAllSchoolList(List<SchoolEntity> schoolEntities);

}
