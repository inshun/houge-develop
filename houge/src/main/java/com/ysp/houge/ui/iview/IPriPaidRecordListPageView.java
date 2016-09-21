package com.ysp.houge.ui.iview;

import com.ysp.houge.model.entity.db.ItemPrePaidRecordEntity;

import java.util.List;

public interface IPriPaidRecordListPageView extends
		IBaseRefreshListView<List<ItemPrePaidRecordEntity>> {
	void jumpToNextPage(ItemPrePaidRecordEntity prePaidRecordEntity);
}
