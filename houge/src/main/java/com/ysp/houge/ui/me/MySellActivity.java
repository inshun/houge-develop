package com.ysp.houge.ui.me;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tyn.view.listviewhelper.ListViewHelper;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.adapter.SellOrderAdapter;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.MySellEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IMySellPresenter;
import com.ysp.houge.presenter.impl.MySellPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.details.UserDetailsActivity;
import com.ysp.houge.ui.iview.IMySellPageView;
import com.ysp.houge.ui.message.ChatActivity;
import com.ysp.houge.ui.order.OrderDetailsActivity;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.MyActionBar;

import java.util.List;

/**
 * 描述： 我卖出的
 *
 * @ClassName: MySellActivity
 * @author: hx
 * @date: 2015年12月17日 下午3:28:12
 * <p/>
 * 版本: 1.0
 */
public class MySellActivity extends BaseFragmentActivity implements IMySellPageView, OnItemClickListener {
	private SellOrderAdapter adapter;

	/**
	 * 下拉刷新listView
	 */
	private PullToRefreshListView mRefreshListView;
	private ListViewHelper<List<MySellEntity>> listViewHelper;
	private IMySellPresenter<List<MySellEntity>> iMySellerPresenter;
	private UserInfoEntity userInfoEntity;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, MySellActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_my_sell);
		check();
	}

	public void check(){
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
				!= PackageManager.PERMISSION_GRANTED) {
			//申请WRITE_EXTERNAL_STORAGE权限
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
					100);
		}else {
			//已赋予权限
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode==100) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				//有权限
			} else {
				//无权限
				showToast("请打开对应的权限，否者会导致App无法正常运行！");
			}
		}

	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.me_seller));
		actionBar.setRightText("客服");
		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iMySellerPresenter.serviceClick();
			}
		});
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		mRefreshListView = (PullToRefreshListView) findViewById(R.id.mRefreshListView);
		mRefreshListView.getRefreshableView().setDividerHeight(SizeUtils.dip2px(this, 10));
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iMySellerPresenter = new MySellPresenter(this);
		listViewHelper = new ListViewHelper<List<MySellEntity>>(mRefreshListView);
		listViewHelper.setRefreshPresenter(iMySellerPresenter);
		adapter = new SellOrderAdapter(this);
		adapter.setListener(this);
		listViewHelper.setAdapter(adapter);
		listViewHelper.refresh();
	}

	@Override
	public void refresh() {
		mRefreshListView.setRefreshing();
	}

	@Override
	public void refreshComplete(List<MySellEntity> t) {
		listViewHelper.refreshComplete(t);
	}

	@Override
	public void loadMoreComplete(List<MySellEntity> t) {
		listViewHelper.loadMoreDataComplete(t);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (null != mRefreshListView && null != iMySellerPresenter) {
			listViewHelper.refresh();
		}
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void OnClick(int position, int operation) {
		switch (operation) {
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_ORDER_DETAIL:
				jumpToOrderDetails(position, -1);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_ORDER_FUNCTION:
				jumpToOrderDetails(position, OnItemClickListener.CLICK_OPERATION_ORDER_FUNCTION);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
				jumpToUserDetails(position);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL:
				jumpToSkillDetails(position);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_NEED_DETAIL:
				jumpToNeedDetails(position);
				break;
			case com.ysp.houge.lisenter.OnItemClickListener.CLICK_OPERATION_IM_TO_BUY:
				jumpToCharPageTwo(position);
				break;
			default:
				jumpToOrderDetails(position, operation);
				break;
		}
	}

	@Override
	public void showCallPhonePop() {
		String initJson = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
		if (TextUtils.isEmpty(initJson)) {
			showToast("获取客服电话失败");
		} else {
			final String phoneNum = new Gson().fromJson(initJson, AppInitEntity.class).mobile;

			YesOrNoDialogEntity entity = new YesOrNoDialogEntity("确定拨打客服电话?", phoneNum, getString(R.string.cancel),
					getString(R.string.sure));
			YesOrNoDialog dialog = new YesOrNoDialog(this, entity, new OnYesOrNoDialogClickListener() {

				@Override
				public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
					switch (yesOrNoType) {
						case BtnOk:
							GetUri.openCallTelephonePage(MySellActivity.this, phoneNum);
							break;
						case BtnCancel:
							break;
					}
				}

			});

			if (!dialog.isShowing()) {
				dialog.show();
			}
		}
	}

	@Override
	public void jumpToOrderDetails(int position, int operation) {
		MySellEntity sellEntity = adapter.getData().get(position);
		if (null != sellEntity) {
			Bundle bundle = new Bundle();
			bundle.putString(OrderDetailsActivity.TAG, OrderDetailsActivity.SALE);
			bundle.putInt(OrderDetailsActivity.OPERATION, operation);
			bundle.putString(OrderDetailsActivity.KEY, sellEntity.order_id);
			OrderDetailsActivity.jumpIn(this, bundle);
		}
	}

	@Override
	public void jumpToUserDetails(int position) {
		Bundle bundle = new Bundle();
		bundle.putInt(UserDetailsActivity.KEY, adapter.getData().get(position).buyer_user_id);
		UserDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToSkillDetails(int position) {
		Bundle bundle = new Bundle();
		bundle.putInt(SkillDetailsActivity.KEY, adapter.getData().get(position).good_id);
		SkillDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToNeedDetails(int position) {
		Bundle bundle = new Bundle();
		bundle.putInt(NeedDetailsActivity.KEY, adapter.getData().get(position).good_id);
		NeedDetailsActivity.jumpIn(this, bundle);
	}

	@Override
	public void jumpToCharPageTwo(int position) {
		MySellEntity mySellEntity = adapter.getData().get(position);
		userInfoEntity = mySellEntity.buyer_user;
		ChatPageEntity chatPageEntity = new ChatPageEntity(String.valueOf(userInfoEntity.id));
		Bundle bundle = new Bundle();
		bundle.putSerializable(ChatActivity.CHAT_PAGE_ENTITY, chatPageEntity);
		ChatActivity.jumpIn(this, bundle);
	}


}
