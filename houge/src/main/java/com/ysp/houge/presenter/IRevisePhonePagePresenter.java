package com.ysp.houge.presenter;

public interface IRevisePhonePagePresenter {
	void requstSubmit();

	void checkCanSubmitState(String password, String newTelephone);
}
