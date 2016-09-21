package com.tyn.view;

public interface OnNetResponseListener<DATA> {
	void onSuccess(DATA data);

	void onError();
}
