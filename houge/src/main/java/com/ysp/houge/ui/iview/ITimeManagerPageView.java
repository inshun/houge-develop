package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.TimeEntity;

/**
 * @author it_hu
 * 
 *         时间管理View层接口
 *
 */
public interface ITimeManagerPageView extends IBaseView {

	/** 初始化全部选中 */
	void initAllCheck(List<TimeEntity> listDate);

	/** 初始化页面 */
	void initView(List<TimeEntity> list);

	/** 发送数据 */
	void postDate(List<TimeEntity> list);
}
