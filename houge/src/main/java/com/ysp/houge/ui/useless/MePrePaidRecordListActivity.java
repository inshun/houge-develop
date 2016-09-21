package com.ysp.houge.ui.useless;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.ysp.houge.R;
import com.ysp.houge.adapter.PrePaidRecordAdapter;
import com.ysp.houge.model.entity.db.ItemPrePaidRecordEntity;
import com.ysp.houge.presenter.IPriPaidRecordListPagePresenter;
import com.ysp.houge.presenter.impl.PriPaidRecordListPagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IPriPaidRecordListPageView;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:预支纪录列表
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年7月12日上午10:53:40
 * @version 1.0
 */
public class MePrePaidRecordListActivity extends BaseFragmentActivity implements
		OnItemClickListener, IPriPaidRecordListPageView {
	private ListViewHelper<List<ItemPrePaidRecordEntity>> listViewHelper;
	private PullToRefreshListView mRefreshListView;

	private IPriPaidRecordListPagePresenter iPriPaidRecordListPagePresenter;

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_me_prepaid_record);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		listViewHelper = new ListViewHelper<List<ItemPrePaidRecordEntity>>(
				mRefreshListView);
		iPriPaidRecordListPagePresenter = new PriPaidRecordListPagePresenter(
				this);
		listViewHelper.setRefreshPresenter(iPriPaidRecordListPagePresenter);
		listViewHelper.setAdapter(new PrePaidRecordAdapter(this));
		listViewHelper.setOnItemClickListener(this);
		listViewHelper.refresh();

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.advance_record));
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		iPriPaidRecordListPagePresenter.onItemClick(parent, view, position, id);
	}

	@Override
	public void jumpToNextPage(ItemPrePaidRecordEntity prePaidRecordEntity) {
		// TODO jump
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(
			List<ItemPrePaidRecordEntity> itemPrePaidRecordEntities) {
		listViewHelper.refreshComplete(itemPrePaidRecordEntities);
	}

	@Override
	public void loadMoreComplete(
			List<ItemPrePaidRecordEntity> itemPrePaidRecordEntities) {
		listViewHelper.loadMoreDataComplete(itemPrePaidRecordEntities);
	}

}
