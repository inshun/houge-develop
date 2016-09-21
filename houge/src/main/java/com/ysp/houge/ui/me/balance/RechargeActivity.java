package com.ysp.houge.ui.me.balance;

import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IBalanceModel;
import com.ysp.houge.model.entity.eventbus.RechargeSuccessEventBusEntity;
import com.ysp.houge.model.impl.BalanceModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.AliPayUtils;
import com.ysp.houge.utility.AliPayUtils.AlipayResponeCallBack;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;


/**
 * 描述： 充值页面
 *
 * @ClassName: RechargeActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月17日 上午12:55:44
 * 
 *        版本: 1.0
 */
public class RechargeActivity extends BaseFragmentActivity {
	private ClearEditText money;
	private Button sure;
    private ImageView iv;

	private IBalanceModel iBalanceModel;
	private AliPayUtils aliPayUtils;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, RechargeActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_recharge);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.top_up));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		money = (ClearEditText) findViewById(R.id.cet_recharge_money);
        iv = (ImageView) findViewById(R.id.iv_pay_way_alipay);
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_BUYER:
                iv.setImageResource(R.drawable.icon_sel_orange);
                break;

            case MyApplication.LOG_STATUS_SELLER:
                iv.setImageResource(R.drawable.icon_sel_blue);
                break;
        }

		sure = (Button) findViewById(R.id.btn_sure);
		sure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String m = money.getText().toString().trim();
				if (TextUtils.isEmpty(m)) {
					showToast(R.string.please_input_recharge_money);
				} else {
					try {
						Double.parseDouble(m);
						requesrRecharge(m);
					} catch (Exception e) {
						LogUtil.setLogWithE("requesrRecharge", e.getMessage());
					}
				}
			}

		});
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iBalanceModel = new BalanceModelImpl();
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

    private void requesrRecharge(String price) {
		showProgress();
		iBalanceModel.recharge(BalanceModelImpl.PAY_TYPE_ALIPAY, Double.parseDouble(price),
				new OnNetResponseCallback() {

					@Override
					public void onSuccess(Object data) {
						hideProgress();
						if (data != null && data instanceof String) {
							alipay((String) data);
						} else {
							showToast(R.string.request_failed);
						}
					}

					@Override
					public void onError(int errorCode, String message) {
						hideProgress();
						switch (errorCode) {
						case ResponseCode.TIP_ERROR:
							showToast(message);
							break;
						default:
							showToast(R.string.request_failed);
							break;
						}
					}
				});
	}

	private void alipay(String sign) {
		aliPayUtils = new AliPayUtils(new AlipayResponeCallBack() {

			@Override
			public void payWait() {
				showToast("支付结果确认中");
                EventBus.getDefault().post(new RechargeSuccessEventBusEntity());
			}

			@Override
			public void paySuccess(String sign) {
				showToast("充值成功");
                EventBus.getDefault().post(new RechargeSuccessEventBusEntity());
				close();
			}

			@Override
			public void payError(int errorCode) {
				showToast("充值失败");
			}
		}, sign);
		aliPayUtils.alipay(this);
	}
}
