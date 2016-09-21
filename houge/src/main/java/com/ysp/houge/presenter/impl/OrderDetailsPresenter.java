package com.ysp.houge.presenter.impl;

import com.google.gson.Gson;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IOrderDetailsPresenter;
import com.ysp.houge.ui.iview.IOrderDetailsPageView;

import android.text.TextUtils;

/**
 * 描述： 订单详情Presenter层
 *
 * @ClassName: OrderDetailsPresenter
 * 
 * @author: hx
 * 
 * @date: 2015年12月15日 下午3:07:07
 * 
 *        版本: 1.0
 */
public class OrderDetailsPresenter extends BasePresenter<IOrderDetailsPageView> implements IOrderDetailsPresenter {

	public OrderDetailsPresenter(IOrderDetailsPageView view) {
		super(view);
	}

	@Override
	public void initModel() {
	}

	@Override
	public void onESQClick() {
		// 先检查是否有客服电话
		String initInfoJson = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
		if (!TextUtils.isEmpty(initInfoJson)) {
			AppInitEntity initEntity = new Gson().fromJson(initInfoJson, AppInitEntity.class);
			mView.showCallPhoneDialog(initEntity.mobile);
		} else {
			mView.showToast("获取客服电话失败!");
		}
	}
}
