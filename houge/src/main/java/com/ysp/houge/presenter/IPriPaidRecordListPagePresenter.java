package com.ysp.houge.presenter;

import java.util.List;

import android.view.View;
import android.widget.AdapterView;

import com.tyn.view.IRefreshPresenter;
import com.ysp.houge.model.entity.db.ItemPrePaidRecordEntity;

public interface IPriPaidRecordListPagePresenter extends
		IRefreshPresenter<List<ItemPrePaidRecordEntity>> {
	void onItemClick(AdapterView<?> parent, View view, int position, long id);
}
