package com.ysp.houge.presenter.impl;

import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.db.ItemMessageEntity;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IMessageItemPresenter;
import com.ysp.houge.ui.iview.IMessageItemView;

/**
 * @描述:
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月8日下午12:45:27
 * @version 1.0
 */
public class MessageItemPresenter extends BasePresenter<IMessageItemView>
		implements IMessageItemPresenter {

	public MessageItemPresenter(IMessageItemView view) {
		super(view);
	}

	@Override
	public void initModel() {

	}

	@Override
	public void clickUserAvatar(ItemMessageEntity messageEntity) {

	}

	@Override
	public void clickMeAvatar() {
		if (MyApplication.getInstance().getUserInfo() != null) {
			mView.jumpToMeInfoPage(MyApplication.getInstance().getUserInfo());
		}
	}

	@Override
	public void clickTextMessage(ItemMessageEntity messageEntity) {

	}

	@Override
	public void clickResendBtn(ItemMessageEntity messageEntity) {

	}

}
