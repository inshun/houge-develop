package com.ysp.houge.ui.me.balance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IBalanceModel;
import com.ysp.houge.model.impl.BalanceModelImpl;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.me.WithdrawalWebview;
import com.ysp.houge.utility.DoubleClickUtil;
import com.ysp.houge.utility.MD5Utils;
import com.ysp.houge.utility.RsaUtils;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.ClearEditText;
import com.ysp.houge.widget.MyActionBar;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 提现页面
 *
 * @ClassName: WithdrawDepositActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月22日 上午11:02:03
 * 
 *        版本: 1.0
 */
@SuppressLint("InflateParams")
public class WithdrawDepositActivity extends BaseFragmentActivity implements OnClickListener {
	private RelativeLayout actionbar;
	private ClearEditText cetMoney, cetName, cetAccount, mPwd;
	private Button submit, sure, cancle;
	private TextView deal;
	private ImageView dealChoose;
	private StringBuilder sb;
	private boolean isChoose = true;

	private PopupWindow pop;

	private IBalanceModel iBalanceModel;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, WithdrawDepositActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_withdraw_deposit);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.withdraw_deposit));
		actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		cetMoney = (ClearEditText) findViewById(R.id.cet_money);
		cetName = (ClearEditText) findViewById(R.id.cet_name);
		cetAccount = (ClearEditText) findViewById(R.id.cet_account);

		submit = (Button) findViewById(R.id.btn_submit);

		deal = (TextView) findViewById(R.id.tv_withdraw_deposit_deal);
		dealChoose = (ImageView) findViewById(R.id.iv_withdraw_deposit_deal_choose);

		submit.setOnClickListener(this);
		deal.setOnClickListener(this);
		dealChoose.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		iBalanceModel = new BalanceModelImpl();
		initDeal(isChoose);
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

	private void initDeal(boolean isChoose) {
		if (null == sb) {
			sb = new StringBuilder();
			sb.append("阅读并同意《");
			switch (MyApplication.getInstance().getLoginStaus()) {
				case MyApplication.LOG_STATUS_BUYER:
					sb.append("<font color='#ff6724'>");
					break;

				case MyApplication.LOG_STATUS_SELLER:
					sb.append("<font color='#30ccda'>");
					break;
			}
			sb.append("提现协议");
			sb.append("</font>");
			sb.append("》");
			deal.setText(Html.fromHtml(sb.toString()));

		}

		if (isChoose) {
			switch (MyApplication.getInstance().getLoginStaus()) {
				case MyApplication.LOG_STATUS_BUYER:
					dealChoose.setImageResource(R.drawable.icon_sel_orange);
					break;

				case MyApplication.LOG_STATUS_SELLER:
					dealChoose.setImageResource(R.drawable.icon_sel_blue);
					break;
			}
		} else {
			dealChoose.setImageResource(R.drawable.icon_def);
		}
	}

	private void checkInput(String money, String name, String account) {
		if (TextUtils.isEmpty(money)) {
			showToast(R.string.please_input_withdraw_deposit_money);
			return;
		}

		if (TextUtils.isEmpty(name)) {
			showToast(R.string.please_input_person_name);
			return;
		}

		if (TextUtils.isEmpty(account)) {
			showToast(R.string.please_intpt_account);
			return;
		}

		// 检查完毕之后弹出对话框
		createPwdPop();
	}

	@SuppressWarnings("deprecation")
	private void createPwdPop() {
		View view = LayoutInflater.from(this).inflate(R.layout.popup_password, null);
		mPwd = (ClearEditText) view.findViewById(R.id.cet_pwd);
		sure = (Button) view.findViewById(R.id.btn_sure);
		cancle = (Button) view.findViewById(R.id.btn_cancle);
		sure.setOnClickListener(this);
		cancle.setOnClickListener(this);

		pop = new PopupWindow(view, SizeUtils.dip2px(this, 300), LayoutParams.WRAP_CONTENT, true) {

			@Override
			public void dismiss() {
				super.dismiss();
				// pop消失的时候还原窗体透明度
				WindowManager.LayoutParams lp = getWindow().getAttributes();
				lp.alpha = 1.0f;
				getWindow().setAttributes(lp);
			}
		};
		pop.setAnimationStyle(R.style.popwin_anim_top);
		pop.setFocusable(true);// 获得焦点
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
		pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

		// 透明度
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = 0.3f;
		getWindow().setAttributes(lp);
		// 设置显示的地方
		pop.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.CENTER_HORIZONTAL, 0, 0);
	}

	// 验证密码操作(这里做加密操作)
	private void requestCheckPassword(String password) {
		requesWithdrawDeposit(cetMoney.getText().toString().trim(), cetName.getText().toString().trim(),
				cetAccount.getText().toString().trim(), RsaUtils.encryptByPublic(password));
	}

	private void requesWithdrawDeposit(String money, String name, String account, String password) {
		showProgress();
		iBalanceModel.withdrawDeposit(Double.parseDouble(money), account, name, password, new OnNetResponseCallback() {

			@Override
			public void onSuccess(Object data) {
				hideProgress();
				showToast("申请成功,请等待处理");
				close();
			}

			@Override
			public void onError(int errorCode, String message) {
				hideProgress();
				switch (errorCode) {
					case ResponseCode.TIP_ERROR:
						showToast(message);
						close();
						break;
					default:
						showToast(R.string.request_failed);
						break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			// 提交
			case R.id.btn_submit:
				if (DoubleClickUtil.isFastClick()) {
					return;
				}

				if (!isChoose) {
					showToast("请先同意提现协议");
					return;


				}

				checkInput(cetMoney.getText().toString().trim(), cetName.getText().toString().trim(),
						cetAccount.getText().toString().trim());
				break;

			// 协议
			case R.id.tv_withdraw_deposit_deal:
				WithdrawalWebview.jumpIn(this);
				break;
			// 协议选中状态
			case R.id.iv_withdraw_deposit_deal_choose:
				isChoose = isChoose ? false : true;
				initDeal(isChoose);
				break;

			// pop确定
			case R.id.btn_sure:
				// 检查用户密码
				if (TextUtils.isEmpty(mPwd.getText().toString().trim())) {
					showToast("密码不能为空");
					return;
				}

				if (mPwd.getText().toString().trim().length() < 8) {
					showToast("密码长度为8~16位");
					return;
				}

				// 先让窗体消失
				pop.dismiss();
				showProgress();
				// 请求密码确认接口
				requestCheckPassword(mPwd.getText().toString().trim());
				break;

			// pop取消
			case R.id.btn_cancle:
				if (pop != null && pop.isShowing()) {
					pop.dismiss();
				}
				break;
			default:
				break;
		}
	}

	private String createUrl(){
		StringBuilder url = new StringBuilder(HttpApi.getAbsPathUrl(HttpApi.SITE_WITHDRAW));
		url.append("?");

		//一些全局参数
		Map<String, String> params = new HashMap<String, String>();
		params.put(HttpRequest.PARAME_NAME_PT,HttpRequest.PARAME_VALUE_PT);
		params.put(HttpRequest.PARAME_NAME_VR,HttpRequest.PARAME_VALUE_VR);
		params.put(HttpRequest.PARAME_NAME_APT,String.valueOf(MyApplication.LOG_STATUS_SELLER));
		String token = MyApplication.getInstance().getPreferenceUtils().getToken();
		if (!TextUtils.isEmpty(token)){
			params.put(HttpRequest.PARAME_NAME_TOKEN, token);
		}
		String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
		if (!TextUtils.isEmpty(local)){
			BDLocation location = new Gson().fromJson(local,BDLocation.class);
			params.put(HttpRequest.PARAME_NAME_GPS, location.getLatitude() + "," + location.getLongitude());
		}
		params.put(HttpRequest.PARAME_NAME_SIGN, MD5Utils.MD5(MD5Utils.CreateLinkString(params) + MD5Utils.SING));

		//拼接上全局参数
		for (Map.Entry<String,String> entry : params.entrySet()){
			url.append(entry.getKey());
			url.append("=");
			url.append(entry.getValue());
			url.append("&");
		}

		//删除最后一个&符号
		url.deleteCharAt(url.length() - 1);
		return url.toString();
	}
}
