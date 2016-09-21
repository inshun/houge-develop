package com.ysp.houge.presenter;

import java.util.List;

import com.ysp.houge.model.entity.bean.TimeEntity;

/**
 * @author it_hu
 * 
 *         时间管理页面presenter层接口
 *
 */
public interface ITimeManagerPagePresenter {

	/** 设置进入页面 */
	void setEnterPage(String enterPage);

	/** 根据数据初始化页面 */
	void setData(List<TimeEntity> list);
	
	void getMySettingTime();

	/** 改变对应位置的属性值 */
	void changeValue(int i, int j);

	/** 提交 */
	void submit();
}
