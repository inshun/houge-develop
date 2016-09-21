package com.ysp.houge.ui.order;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.presenter.IOrderDetailsPresenter;
import com.ysp.houge.presenter.impl.OrderDetailsPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IOrderDetailsPageView;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.TwoSwitchBtnView;
import com.ysp.houge.widget.TwoSwitchBtnView.OnTwoSwitchBtnClickListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

/**
 * 描述： 订单详情
 *
 * @ClassName: OrderDetailsActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月15日 下午3:03:35
 * 
 *        版本: 1.0
 */
@SuppressLint("Recycle")
public class OrderDetailsActivity extends BaseFragmentActivity
		implements IOrderDetailsPageView, OnTwoSwitchBtnClickListener {
	public static final String KEY = "order_id";
	public static final String OPERATION = "operation";
	public static final String TAG = "tag";
	public static final String BUY = "buy";
	public static final String SALE = "sale";

	private static final int DETAILS_FRAGMENT = 0;
	private static final int STATUS_FRAGMENT = 1;

	private TwoSwitchBtnView switchBtnView;

	private int index = DETAILS_FRAGMENT;
	private OrderDetailsFragment detailsFragment;
	private OrderStatusFragment statusFragment;
	private IOrderDetailsPresenter iOrderDetailsPresenter;

	/**
	 * 跳转进当前页面
	 */
	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, OrderDetailsActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
			context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_order_details);
	}

	@Override
	protected void initActionbar() {
		iOrderDetailsPresenter = new OrderDetailsPresenter(this);
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.order_details));
		actionBar.setRightText(getString(R.string.esq));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);

		actionBar.setRightClickListenner(new OnClickListener() {

			@Override
			public void onClick(View v) {
				iOrderDetailsPresenter.onESQClick();
			}
		});
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		switchBtnView = (TwoSwitchBtnView) findViewById(R.id.tsbv_details_or_status);
		switchBtnView.setLeftBtnText("订单详情");
		switchBtnView.setRightBtnText("订单状态");
		switchBtnView.setOnTwoSwitchBtnClickListener(this);
		switchBtnView.setChooseTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
		switchBtnView.setCursorColor(getResources().getColor(R.color.color_app_theme_orange_bg));
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		Bundle bundle = null;
		Intent intent = getIntent();
		bundle = intent.getExtras();
		if (null != getIntent().getExtras()) {
			bundle = getIntent().getExtras();
		} else {
			showToast("参数错误");
			close();
			return;
		}

		if (bundle.containsKey(KEY)) {
			String order_id = bundle.getString(KEY);
            String status = "";
            int operation = -1;

            if (bundle.containsKey(TAG))
			    status = bundle.getString(TAG);


            if (bundle.containsKey(OPERATION))
                operation = bundle.getInt(OPERATION);


			detailsFragment = new OrderDetailsFragment();
			statusFragment = new OrderStatusFragment();

			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.add(R.id.line_order_details, detailsFragment);
			transaction.add(R.id.line_order_details, statusFragment);
			transaction.commit();

			serIndex(index);

			detailsFragment.setOrderInfo(order_id, status,operation);
            statusFragment.setOrderId(order_id);
		} else{
			showToast("未知错误");
			close();
			return;
		}
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
	public void showCallPhoneDialog(final String esqNum) {
		YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity("是否拨打?", esqNum, getString(R.string.cancel),
				getString(R.string.sure));
		YesOrNoDialog dialog = new YesOrNoDialog(this, dialogEntity, new OnYesOrNoDialogClickListener() {

			@Override
			public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
				switch (yesOrNoType) {
				case BtnOk:
					GetUri.openCallTelephonePage(OrderDetailsActivity.this, esqNum);
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
	public void clickLeftBtn() {
		if (index != DETAILS_FRAGMENT) {
			serIndex(DETAILS_FRAGMENT);
		}
	}

	@Override
	public void clickRightBtn() {
		if (index != STATUS_FRAGMENT) {
			serIndex(STATUS_FRAGMENT);
		}
	}

	@Override
	public void serIndex(int index) {
		if (this.index == index) {
			return;
		}

		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		switch (index) {
		case DETAILS_FRAGMENT:
			transaction.show(detailsFragment).hide(statusFragment);
			break;
		case STATUS_FRAGMENT:
			transaction.show(statusFragment).hide(detailsFragment);
			break;
		}
		transaction.commit();
		this.index = index;
	}

}
