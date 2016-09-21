package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.db.UserInfoEntity;

public interface IMessageItemView extends IBaseView {
	void jumpToUserDetailPage(UserInfoEntity userInfo);

	void jumpToMeInfoPage(UserInfoEntity userInfo);

	void updateItem();

	void showCopyAndDeleteDialog();
}