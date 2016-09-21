package com.ysp.houge.ui.me.safeguard;

import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.HttpApi;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.eventbus.SubmitCashDepositSuccessEventBusEntity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.MD5Utils;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.widget.MyActionBar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述： 申请服务保障
 *
 * @ClassName: ApplyServiceSafeguardActivity
 * 
 * @author: hx
 * 
 * @date: 2015年12月12日 下午4:48:24
 * 
 *        版本: 1.0
 */
public class ApplyServiceSafeguardActivity extends BaseFragmentActivity implements OnClickListener {
	private WebView webView;
	private TextView nextStep, serviceDeal;

	private boolean agreeDeal = true;
	private Drawable Y, N;

	public static void jumpIn(Context context) {
		context.startActivity(new Intent(context, ApplyServiceSafeguardActivity.class));
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_safeguard);
	}

	@Override
	protected void initActionbar() {
		MyActionBar actionBar = new MyActionBar(this);
		actionBar.setTitle(getString(R.string.apply_service_safeguard));
		actionBar.setBackgroundColor(getResources().getColor(R.color.white));
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		EventBus.getDefault().register(this);

		webView = (WebView) findViewById(R.id.wv_service_deal);
		nextStep = (TextView) findViewById(R.id.tv_next_step);
		serviceDeal = (TextView) findViewById(R.id.tv_service_deal);

		webView.getSettings().setJavaScriptEnabled(true);

		nextStep.setOnClickListener(this);
		serviceDeal.setOnClickListener(this);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		webView.loadUrl(createUrl());
		changeCheckStatus(agreeDeal);
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

    private String createUrl(){
        StringBuilder url = new StringBuilder(HttpApi.getAbsPathUrl(HttpApi.SERVICE_SAFEGUARD_DESC));
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

    private void changeCheckStatus(boolean isCheck) {
		if (isCheck) {
			if (null == Y) {
				switch (MyApplication.getInstance().getLoginStaus()){
                    case MyApplication.LOG_STATUS_BUYER:
                        Y = getResources().getDrawable(R.drawable.icon_sel_orange);
                        break;
                    case MyApplication.LOG_STATUS_SELLER:
                        Y = getResources().getDrawable(R.drawable.icon_sel_blue);
                        break;
                }
				Y.setBounds(0, 0, Y.getMinimumWidth(), Y.getMinimumHeight());
			}
			serviceDeal.setCompoundDrawables(Y, null, null, null);
		} else {
			if (null == N) {
				N = getResources().getDrawable(R.drawable.icon_def);
				N.setBounds(0, 0, N.getMinimumWidth(), N.getMinimumHeight());
			}
			serviceDeal.setCompoundDrawables(N, null, null, null);
		}
	}

	/** 接收提交保证金成功请求的 消息 */
	public void onEventMainThread(SubmitCashDepositSuccessEventBusEntity busEntity) {
		close();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_next_step:
			if (agreeDeal) {
				SubmitCashDeposit.jumpIn(this);
				close();
			} else {
				showToast("请先同意服务协议");
			}
			break;

		// 服务协议点击
		case R.id.tv_service_deal:
			agreeDeal = agreeDeal ? false : true;
			changeCheckStatus(agreeDeal);
			break;
		}
	}
}
