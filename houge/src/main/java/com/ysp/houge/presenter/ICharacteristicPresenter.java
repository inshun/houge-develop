package com.ysp.houge.presenter;

import java.util.List;

import com.ysp.houge.model.entity.bean.CharacteristicEntity;

/**
 * @描述:特点列表页面presenter层接口
 * @Copyright Copyright (c) 2015
 * @Company 杭州传送门网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年9月25日下午3:23:07
 * @version 2.4
 */
public interface ICharacteristicPresenter {
	/**
	 * @描述:获取特点列表
	 * @方法名: getCharacteristicList
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月25日下午3:23:36
	 * @修改人 tyn
	 * @修改时间 2015年9月25日下午3:23:36
	 * @修改备注
	 * @since
	 * @throws
	 */
	void getCharacteristicList();

	/**
	 * @描述:点击下一步按钮
	 * @方法名: clickNextStepBtn
	 * @param characteristicEntities
	 * @返回类型 void
	 * @创建人 tyn
	 * @创建时间 2015年9月25日下午3:23:44
	 * @修改人 tyn
	 * @修改时间 2015年9月25日下午3:23:44
	 * @修改备注
	 * @since
	 * @throws
	 */
	void clickNextStepBtn(List<CharacteristicEntity> characteristicEntities);
}
