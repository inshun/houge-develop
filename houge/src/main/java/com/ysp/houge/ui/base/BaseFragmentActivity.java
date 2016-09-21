/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
/**
 *
 */
package com.ysp.houge.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.ysp.houge.app.AppManager;
import com.ysp.houge.dialog.HougeProgressDialog;
import com.ysp.houge.ui.iview.IBaseView;
import com.ysp.houge.utility.Toaster;

/**
 * This class is used for 页面基础类
 *
 * @author tyn
 * @version 1.0, 2014-9-8 上午10:44:54
 */

public abstract class BaseFragmentActivity extends FragmentActivity implements
        IBaseView {
    public static final String TITLE_LEFT_LABEL = "titleLeftLabel";
    protected String titleLeftLabel;

    protected boolean isActivityLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(TITLE_LEFT_LABEL)) {
                titleLeftLabel = getIntent().getExtras().getString(
                        TITLE_LEFT_LABEL);
            }
        }
        // Possible work around for market launches. See
        // http://code.google.com/p/android/issues/detail?id=2373
        // for more details. Essentially, the market launches the main activity
        // on top of other activities.
        // we never want this to happen. Instead, we check if we are the root
        // and if not, we finish.
        if (!isTaskRoot()) {
            final Intent intent = getIntent();
            final String intentAction = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && intentAction != null
                    && intentAction.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        // 将该Activity加入堆栈
        AppManager.getAppManager().addActivity(this);
        initializeState(savedInstanceState);
        setContentView();
        initActionbar();
        initializeViews(savedInstanceState);
        initializeData(savedInstanceState);
    }
    //    沉浸是页面处理
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_LEFT_LABEL, titleLeftLabel);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(TITLE_LEFT_LABEL)) {
            titleLeftLabel = savedInstanceState.getString(TITLE_LEFT_LABEL);
        }
    }

    protected void initializeState(Bundle savedInstanceState) {
    }

    ;

    protected abstract void setContentView();

    protected abstract void initializeViews(Bundle savedInstanceState);

    protected abstract void initializeData(Bundle savedInstanceState);

    protected abstract void initActionbar();

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
//        MyApplication.isApplicationLive = true;
        isActivityLive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MyApplication.isApplicationLive = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isActivityLive = false;
    }

    @Override
    protected void onDestroy() {
        // 将该Activity从堆栈移除
        AppManager.getAppManager().removeActivity(this);


//        // 注销掉监听
//        if (locationService.isStart()) {
//            locationService.stop(); // 停止定位服务
//        }
//        locationService.unregisterListener(mListener);
        super.onDestroy();
    }

    @Override
    public void close() {
        finish();
    }

    @Override
    public void showProgress(boolean flag, String message) {
        if (isActivityLive) {
            HougeProgressDialog.show(this);
        }
    }

    @Override
    public void showProgress(String message) {
        showProgress(true, message);
    }

    @Override
    public void showProgress() {
        showProgress(true);
    }

    @Override
    public void showProgress(boolean flag) {
        showProgress(flag, "");
    }

    @Override
    public void hideProgress() {
        HougeProgressDialog.hide();
    }

    @Override
    public void showToast(int resId) {
        showToast(getString(resId));
    }

    @Override
    public void showToast(String msg) {
        if (isActivityLive) {
            Toaster.getInstance().displayToast(msg, -1);
        }
    }

    @Override
    public void showNetError() {
        showToast("网络异常,请稍后重试");
    }

    @Override
    public void showParseError() {
        showToast("数据解析异常");
    }


}
