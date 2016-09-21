package com.ysp.houge.ui.order;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.entity.bean.OrderDetailsEntity.OrderEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.eventbus.PaySuccessEventBusEntity;
import com.ysp.houge.presenter.IPayPresenter;
import com.ysp.houge.presenter.impl.PayPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IPayPagerView;
import com.ysp.houge.utility.AliPayUtils;
import com.ysp.houge.utility.AliPayUtils.AlipayResponeCallBack;
import com.ysp.houge.utility.GetUri;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.PayView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 描述： 支付页面
 *
 * @ClassName: PayActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月30日 上午9:34:46
 * 
 *        版本: 1.0
 */
public class PayActivity extends BaseFragmentActivity implements IPayPagerView, OnClickListener {
	public final static String ORDER_ID = "order_id";
	public final static String PRICE = "price";
	public final static String TITLE = "title";
	public final static String NAME = "name";
	public final static String PAY_TYPE = "pay_type";

	private TextView money, title, name, pay;
	private PayView pv;

	private AliPayUtils payUtils;
	private IPayPresenter iPayPresenter;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, PayActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_pay);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.pay));
		actionBar.setRightText("客服");
		actionBar.setRightClickListenner(this);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		money = (TextView) findViewById(R.id.tv_money);
		title = (TextView) findViewById(R.id.tv_title);
		name = (TextView) findViewById(R.id.tv_name);
		pay = (TextView) findViewById(R.id.tv_sure_pay);

		pv = (PayView) findViewById(R.id.pv_pay_view);
		pay.setOnClickListener(this);
        pv.hideFaceToFace();
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iPayPresenter = new PayPresenter(this);

		Bundle bundle = getIntent().getExtras();
		if (null != bundle && bundle.containsKey(ORDER_ID) && bundle.containsKey(PRICE) && bundle.containsKey(TITLE)
				&& bundle.containsKey(NAME) && bundle.containsKey(PAY_TYPE)) {
			iPayPresenter.setOrderId(bundle.getString(ORDER_ID));
			showPayInfo(bundle.getString(PRICE), bundle.getString(TITLE), bundle.getString(NAME));
			pv.setPayWay(bundle.getInt(PAY_TYPE));
		} else {
			showToast("支付信息异常");
			close();
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
	public void showEQSPop() {
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
						GetUri.openCallTelephonePage(PayActivity.this, phoneNum);
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
	public void showPayInfo(String money, String title, String name) {
		this.money.setText(money);
		this.title.setText(title);
		this.name.setText(name);
	}

	@Override
	public void aliPay(String sign) {
		payUtils = new AliPayUtils(new AlipayResponeCallBack() {

			@Override
			public void payWait() {
				showToast("支付结果确认中");
				close();
			}

			@Override
			public void paySuccess(String sign) {
				EventBus.getDefault().post(new PaySuccessEventBusEntity());
				showToast("支付成功");
				close();
			}

			@Override
			public void payError(int errorCode) {
				showToast("支付失败");
			}
		}, sign);
		payUtils.alipay(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 菜单 客服
		case R.id.menu_head_right:
			iPayPresenter.clickEQS();
			break;

		// 支付
		case R.id.tv_sure_pay:
			if (pv.getPayWay() == OrderEntity.PAY_TYPE_BALANCE) {
				double money = 0.0;
				try {
					money = Double.parseDouble(this.money.getText().toString());
				} catch (Exception e) {
				}

				if (MyApplication.getInstance().getUserInfo().balance < money) {
					showToast("余额不足！");
					return;
				}
			}

			iPayPresenter.pay(pv.getPayWay());
			break;
		default:
			break;
		}
	}

}
