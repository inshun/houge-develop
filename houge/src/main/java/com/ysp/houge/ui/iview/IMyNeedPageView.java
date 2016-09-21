package com.ysp.houge.ui.iview;

import java.util.List;

import com.ysp.houge.model.entity.bean.GoodsDetailEntity;

/**
 * @author it_huang
 *
 *         我的需求View层接口
 */
public interface IMyNeedPageView extends IBaseRefreshListView<List<GoodsDetailEntity>> {

	/** 创建是否拨打的弹窗 */
	void showCallPhonePop();

    void  jumpToNeedDetails(int id);
}
