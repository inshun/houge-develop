package com.ysp.houge.presenter;

import com.ysp.houge.model.entity.db.ItemMessageEntity;

public interface IMessageItemPresenter {
	void clickUserAvatar(ItemMessageEntity messageEntity);

	void clickMeAvatar();

	void clickTextMessage(ItemMessageEntity messageEntity);

	void clickResendBtn(ItemMessageEntity messageEntity);
}
