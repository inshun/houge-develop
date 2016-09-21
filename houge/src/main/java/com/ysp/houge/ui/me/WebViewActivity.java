package com.ysp.houge.ui.me;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.WebEntity;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.widget.MyActionBar;
import com.ysp.houge.widget.x5sdk.DemoWebView;
import com.ysp.houge.widget.x5sdk.UrlUtils;

/**
 * @描述:网页页面
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * 
 * @author tyn
 * @date 2015年8月18日下午2:22:54
 * @version 1.0
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends BaseFragmentActivity {
	public static final String KEY = "WebViewActivity";
	protected FrameLayout frameLayout;
	protected FrameLayout mFullscreenContainer;
	Handler handler = new Handler(Looper.getMainLooper());
	private MyActionBar actionBar;
	private DemoWebView webView;
	private ProgressBar pvProgress;
	private String url;
	private String title;
	private WebEntity entity;
	private View mCustomView;
	private CustomViewCallback mCustomViewCallback;

	public static void jumpIn(Context context, Bundle bundle) {
		Intent intent = new Intent(context, WebViewActivity.class);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	@Override
	protected void initActionbar() {
		actionBar = new MyActionBar(this);
		RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
		actionbar.addView(actionBar);
	}

	@Override
	protected void setContentView() {
		setContentView(R.layout.activity_web_view);
	}

	@Override
	protected void initializeViews(Bundle savedInstanceState) {
		pvProgress = (ProgressBar) findViewById(R.id.pb_webprogress);
		frameLayout = (FrameLayout) findViewById(R.id.framelayout);
	}

	@Override
	protected void initializeData(Bundle savedInstanceState) {
		if (null != getIntent() && getIntent().getExtras().containsKey(KEY)) {
			entity = (WebEntity) getIntent().getExtras().getSerializable(KEY);
		} else {
			LogUtil.setLogWithE(KEY, "未检测到网页实体");
			close();
		}

		this.getWindow().setFormat(PixelFormat.RGBA_8888);
		LogUtil.setLogWithE(KEY, "QQBrowserSDK core version is " + WebView.getQQBrowserCoreVersion(this));

		title = entity.getTitle();
		url = entity.getUrl();
		actionBar.setTitle(title);
		// 满足条件的时候开启硬件加速(其实这里不用判断，开发最小支持版本16)
		if (android.os.Build.VERSION.SDK_INT >= 11) {
			getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
					android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
		}
		initWebView();
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
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(KEY, entity);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState.containsKey(KEY)) {
			entity = (WebEntity) savedInstanceState.getSerializable(KEY);
		}
	}

	private void initWebView() {
		// ==================================================================================================
		// 创建WebView
		webView = new DemoWebView(this);
		frameLayout.addView(webView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));

		// 设置Client
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public boolean shouldOverrideKeyEvent(WebView arg0, KeyEvent arg1) {
				return super.shouldOverrideKeyEvent(arg0, arg1);
			}

			@Override
			public void onPageStarted(WebView arg0, String arg1, Bitmap arg2) {
				super.onPageStarted(arg0, arg1, arg2);
				pvProgress.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView arg0, String arg1) {
				super.onPageFinished(arg0, arg1);
				pvProgress.setVisibility(View.GONE);
			}
		});


		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				WebViewActivity.this.title = title;
			}

			@Override
			public void onShowCustomView(View view, CustomViewCallback callback) {
				if (mCustomView != null) {
					callback.onCustomViewHidden();
					return;
				}
				mCustomViewCallback = callback;

				FrameLayout decor = (FrameLayout) WebViewActivity.this.getWindow().getDecorView();
				mFullscreenContainer = new FullscreenHolder(WebViewActivity.this);
				FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT);
				mFullscreenContainer.addView(view, lp);
				decor.addView(mFullscreenContainer, lp);
				mCustomView = view;
				WebViewActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				WebViewActivity.this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
				Intent intent = getIntent();
				intent.addFlags(Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY | Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				WebViewActivity.this.getApplicationContext().startActivity(intent);
			}

			@Override
			public void onHideCustomView() {
				if (mCustomView == null) {
					return;
				}

				FrameLayout decor = (FrameLayout) WebViewActivity.this.getWindow().getDecorView();
				decor.removeView(mFullscreenContainer);
				mFullscreenContainer.removeAllViews();
				mFullscreenContainer = null;
				WebViewActivity.this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				mCustomView = null;
				WebViewActivity.this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
				mCustomViewCallback.onCustomViewHidden();
			}
		});

		webView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String arg0, String arg1, String arg2, String arg3, long arg4) {
				LogUtil.setLogWithE(KEY, arg0);
				new AlertDialog.Builder(WebViewActivity.this).setTitle("是否下载")
						.setPositiveButton(getString(R.string.sure), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						showToast("正在下载");
					}
				}).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				}).setOnCancelListener(new DialogInterface.OnCancelListener() {

					@Override
					public void onCancel(DialogInterface dialog) {
						showToast("取消下载");
					}
				}).show();
			}
		});
		// 各种设置
		if (webView.getX5WebViewExtension() != null) {
			LogUtil.setLogWithE(KEY,
					"robins	CoreVersion_FromSDK:" + webView.getX5WebViewExtension().getQQBrowserVersion());
			webView.getX5WebViewExtension().setWebViewClientExtension(new ProxyWebViewClientExtension() {
				@Override
				public Object onMiscCallBack(String method, Bundle bundle) {
					if (method == "onSecurityLevelGot") {
					}
					return null;
				}
			});
		} else {
			LogUtil.setLogWithE(KEY, "robins	CoreVersion_FromSDK: null");
		}
		WebSettings webSetting = webView.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(false);
		webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
		webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
		webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());

		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		long time = System.currentTimeMillis();
		if (!TextUtils.isEmpty(url)) {
			if (url != null && !"".equals(url)) {
				url = UrlUtils.resolvValidUrl(url);
				if (url != null)
					webView.loadUrl(url);
			}
		}
		LogUtil.setLogWithE(KEY, "time-cost	cost time: " + (System.currentTimeMillis() - time));
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().sync();
		// ==================================================================================================
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			close();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (webView != null) {
			frameLayout.removeView(webView);
			webView.removeAllViews();
			webView.destroy();
		}
	}

	private class FullscreenHolder extends FrameLayout {
		public FullscreenHolder(Context context) {
			super(context);
			setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_light));
		}
	}
}
