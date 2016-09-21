package com.ysp.houge.presenter.impl;

import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ICoupomPagerPresenter;
import com.ysp.houge.ui.iview.ICouponPaperView;
import com.ysp.houge.ui.me.CouponListActivity;

/**
 * @描述: 优惠券页面presneter层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author hx
 * @date 2015年10月5日下午1:05:58
 * @version 1.0
 */
public class CoupomPagerPresenter extends BasePresenter<ICouponPaperView>implements ICoupomPagerPresenter {
	private ICouponPaperView iCouponPaperView;

	public CoupomPagerPresenter(ICouponPaperView view) {
		super(view);
		iCouponPaperView = view;
	}

	@Override
	public void setHeadIndex(int index) {
		switch (index) {
		// 可用
		case CouponListActivity.INDEX_VALID:
			iCouponPaperView.changeButtomStatus(true);
			iCouponPaperView.onValidChoise();
			break;
		// 过期
		case CouponListActivity.INDEX_PAST_DATE:
			iCouponPaperView.changeButtomStatus(false);
			iCouponPaperView.onPastDateChoise();
			break;
		default:
			break;
		}
	}

	@Override
	public void use(String id) {
		iCouponPaperView.use();
	}

	@Override
	public void cancle() {
		iCouponPaperView.cancle();
	}

	@Override
	public void initModel() {
	}

}
