package com.ysp.houge.ui.me.address;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.adapter.AddressAdapter;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.eventbus.AddAddressSuccessEventBusEntity;
import com.ysp.houge.presenter.IAddressManagerPresenter;
import com.ysp.houge.presenter.impl.AddressManagerPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IAddressManagerPageView;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.widget.MyActionBar;

import java.util.List;

/**
 * @描述: 地址管理
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 *
 * @author hx
 * @date 2015年10月23日上午10:28:02
 * @version 1.0
 */
public class
AddressManagerActivity extends BaseFragmentActivity
		implements IAddressManagerPageView, OnClickListener, OnItemClickListener, OnItemLongClickListener {
	public final static String ENTER_PAGE_KEY = "ENTER_PAGER";
	public final static int ENTER_PAGE_ME = 0;
	public final static int ENTER_PAGE_CHOOSE_ADDRESS = 1;

	MyActionBar actionBar;
	private TextView setDefaultAddress;
	private TextView add_address;

	/** 下拉刷新listView */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<AddressEntity>> listViewHelper;
	private IAddressManagerPresenter<List<AddressEntity>> iAddressManagerPagePresenter;

	private AddressAdapter mAddressAdapter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, AddressManagerActivity.class);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}

	@Override
	public ListViewHelper<List<AddressEntity>> getListViewHelper() {
		return listViewHelper;
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_address_manager);
	}

	@Override
	protected void initActionbar() {
		actionBar = new MyActionBar(this);
		actionBar.setTitle("常用地址管理");
		actionBar.setLeftEnable(true);
//		actionBar.setRightText("添加");
//		actionBar.setRightClickListenner(this);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);
		setDefaultAddress = (TextView) findViewById(R.id.tv_buttom_text);
		setDefaultAddress.setOnClickListener(this);
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
		add_address = (TextView) findViewById(R.id.tv_add_address);

		add_address.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iAddressManagerPagePresenter = new AddressManagerPresenter(this);

		Intent intent = getIntent();
		if (intent.hasExtra(ENTER_PAGE_KEY)) {
			iAddressManagerPagePresenter.initEnterPage(intent.getIntExtra(ENTER_PAGE_KEY, -1));
		} else {
			LogUtil.setLogWithE("AddressManagerActivity", "not key ENTER_PAGE_KEY");
			finish();
		}

		listViewHelper = new ListViewHelper<List<AddressEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iAddressManagerPagePresenter);
		mAddressAdapter = new AddressAdapter(this);
		listViewHelper.setAdapter(mAddressAdapter);
		mRefreshListView.setOnItemClickListener(this);
		mRefreshListView.getRefreshableView().setOnItemLongClickListener(this);


		listViewHelper.refresh();
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<AddressEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<AddressEntity> t) {
		listViewHelper.loadMoreDataComplete(t);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
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
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		iAddressManagerPagePresenter.clickListItem(parent, view, position, id);
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		List<AddressEntity> data = mAddressAdapter.getData();
		iAddressManagerPagePresenter.longClickListItem((int) id, position, data);
		return true;
	}

	@Override
	public void jumpToAddAdress(Bundle bundle) {
		AddAddressActivity.jumpIn(this, bundle);
	}

	@Override
	public void showDeleteDialog() {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity("确定删除该地址吗?", "", getString(R.string.cancel),
				getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(this, dialogEntity, new OnYesOrNoDialogClickListener() {

			@Override
			public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
				switch (yesOrNoType) {
					case BtnOk:
						iAddressManagerPagePresenter.delete();
						AddressAdapter addressAdapter = new AddressAdapter(AddressManagerActivity.this);
						break;

					default:
						break;
				}
			}
		});

		if (!dialog.isShowing()) {
			dialog.show();
		}
	}

	@Override
	public void finishPage() {
		finish();
	}

	/** 接收添加地址页面刷新消息 */
	public void onEventMainThread(AddAddressSuccessEventBusEntity entity) {
		listViewHelper.refresh();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 菜单添加
//			case R.id.menu_head_right:
			case R.id.tv_add_address:
				iAddressManagerPagePresenter.addAdress();
				break;

			// 设为默认
			case R.id.tv_buttom_text:
				iAddressManagerPagePresenter.setDefaultAddress();
				break;


		}
	}
}
