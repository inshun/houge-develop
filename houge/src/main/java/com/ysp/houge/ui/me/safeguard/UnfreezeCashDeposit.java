package com.ysp.houge.ui.me.safeguard;

import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author it_huang
 *
 *         解冻服务保证金
 */
public class UnfreezeCashDeposit extends BaseFragmentActivity implements OnClickListener {
    private TextView money;
	private Button unfreeze;

	private IUserInfoModel iUserInfoModel;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, UnfreezeCashDeposit.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_unfreeze_cash_deposit);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle("保证金解冻");
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		unfreeze = (Button) findViewById(R.id.btn_unfreeze);
		unfreeze.setOnClickListener(this);

        money = (TextView)findViewById(R.id.tv_money);
        money.setText(MyApplication.getInstance().getUserInfo().serviceSafeguardg + "元");
        switch (MyApplication.getInstance().getLoginStaus()){
            case MyApplication.LOG_STATUS_SELLER:
                money.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
                break;
            case MyApplication.LOG_STATUS_BUYER:
                money.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
                break;
        }
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iUserInfoModel = new UserInfoModelImpl();
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_unfreeze:
			unfreeze();
			close();
			break;

		default:
			break;
		}
	}

	private void unfreeze(){
		iUserInfoModel.unfreezeCashDeposit(new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				showToast("申请成功");
				close();
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
}
