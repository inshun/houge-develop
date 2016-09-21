package com.ysp.houge.ui.me.safeguard;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.AppManager;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.AliPayUtils;
import com.ysp.houge.utility.AliPayUtils.AlipayResponeCallBack;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.MyActionBar;

/**
 * @author it_huang
 *
 *         提交保证金
 */
public class SubmitCashDeposit extends BaseFragmentActivity implements AlipayResponeCallBack {
	private TextView submit, money;
    private TextView zhifubao;

	private AliPayUtils aliPayUtils;
	private IUserInfoModel infoModel;
	private TextView bond;


	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, SubmitCashDeposit.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_submit_cash_deposit);
		infoModel = new UserInfoModelImpl();
		baozhangjin();
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.submit_cash_deposit));
		actionBar.setBackgroundColor(getResources().getColor(R.color.white));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
        zhifubao = (TextView) findViewById(R.id.tv_zhifubao);
		bond= (TextView) findViewById(R.id.tv_bond);

		money = (TextView) findViewById(R.id.tv_safeguard_money);
//		StringBuilder sb = new StringBuilder("保障金     ");
        Drawable drawableLeft = getResources().getDrawable(R.drawable.icon_zhifubao);
        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_SELLER:
//                sb.append("<font color='#30ccda'>");
                // 这一步必须要做,否则不会显示.
                Drawable drawableRightBlue = getResources().getDrawable(R.drawable.icon_sel_blue);
                drawableRightBlue.setBounds(0, 0, drawableRightBlue.getMinimumWidth(), drawableRightBlue.getMinimumHeight());
                zhifubao.setCompoundDrawables(drawableLeft, null, drawableRightBlue, null);
                break;
            case MyApplication.LOG_STATUS_BUYER:
//		        sb.append("<font color='#fbb217'>");
                // 这一步必须要做,否则不会显示.
                Drawable drawableRightOrange = getResources().getDrawable(R.drawable.icon_sel_orange);
                drawableRightOrange.setBounds(0, 0, drawableRightOrange.getMinimumWidth(), drawableRightOrange.getMinimumHeight());
                zhifubao.setCompoundDrawables(drawableLeft, null, drawableRightOrange, null);
                break;
        }
//		if(bond == null){
//			baozhangjin();
//		}else{
//			sb.append(bond);
//			showToast(bond.toString());
//		}
//		sb.append("</font>");
//		money.setText(Html.fromHtml(sb.toString()));

		submit = (TextView) findViewById(R.id.tv_submit);
		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
		// 请求支付保证金的接口(获得签名)
		submitCashDeposit();
			}
		});
	}

	private void baozhangjin() {
		infoModel.getProtectedfee(new OnNetResponseCallback() {

			@Override
				public void onSuccess(Object data) {
				bond.setText(data.toString());
			}

			@Override
			public void onError(int errorCode, String message) {
				showToast(R.string.request_failed);
			}
		});
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		aliPayUtils = new AliPayUtils();
		aliPayUtils.setAlipayResponeCallBack(this);


	}

	private void submitCashDeposit() {
		infoModel.submitCashDeposit(new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof String) {
                    aliPayUtils.setSign((String) data);
                    aliPayUtils.alipay(SubmitCashDeposit.this);
                } else {
                    showToast(R.string.request_failed);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
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
	public void payWait() {
		showToast("支付结果确认中");
        //结束申请服务保障页面
        AppManager.getAppManager().finishActivity(ApplyServiceSafeguardActivity.class);
        close();
	}

	@Override
	public void paySuccess(String sign) {
		showToast("提交成功");
        //结束申请服务保障页面
        AppManager.getAppManager().finishActivity(ApplyServiceSafeguardActivity.class);
		close();
	}

	@Override
	public void payError(int errorCode) {
		showToast("支付失败");
	}
}
