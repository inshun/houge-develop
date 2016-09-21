package com.ysp.houge.ui.message;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SystemMessageAdatper;
import com.ysp.houge.model.entity.bean.SystemMessageEntity;
import com.ysp.houge.presenter.ISystemMessagePresenter;
import com.ysp.houge.presenter.impl.SystemMessagePresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.ISystemMessagePageView;
import com.ysp.houge.widget.MyActionBar;

/**
 * @描述:系统消息列表页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 *
 * @author tyn
 * @date 2015年8月2日下午4:01:58
 * @version 1.0
 */
public class SystemMessageActivity extends BaseFragmentActivity implements
		ISystemMessagePageView {
	private ListViewHelper<List<SystemMessageEntity>> listViewHelper;
	private PullToRefreshListView mRefreshListView;
	private SystemMessageAdatper mAdapter;
	private ISystemMessagePresenter iSystemMessagePresenter;

    public static void jumpIn(Context context,Bundle bundle){
        Intent intent = new Intent(context,SystemMessageActivity.class);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_system_message);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.system_message));
		actionBar.setLeftEnable(true);
		RelativeLayout actionbar = (RelativeLayout) this
				.findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) this
				.findViewById(R.id.mRefreshListView);
		mAdapter = new SystemMessageAdatper(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
        iSystemMessagePresenter = new SystemMessagePresenter(this);
		listViewHelper = new ListViewHelper<List<SystemMessageEntity>>(
				mRefreshListView);
		listViewHelper.setRefreshPresenter(iSystemMessagePresenter);
		listViewHelper.setAdapter(mAdapter);
        listViewHelper.refresh();
	}

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<SystemMessageEntity> t) {
		if (isActivityLive) {
			listViewHelper.refreshComplete(t);
		}
	}

	@Override
	public void loadMoreComplete(List<SystemMessageEntity> t) {
        if (isActivityLive) {
            listViewHelper.loadMoreDataComplete(t);
        }
	}
}
