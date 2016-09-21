package com.ysp.houge.model;

import com.ysp.houge.utility.volley.OnNetResponseCallback;

public interface IRefreshListModel {
	void onListModelRequest(int page, int size,
			OnNetResponseCallback onNetResponseListener);
}
