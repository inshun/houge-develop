package com.ysp.houge.presenter.impl;

import android.util.Log;

import com.ysp.houge.model.ISplashModel;
import com.ysp.houge.model.entity.bean.VersionInfoEntity;
import com.ysp.houge.model.impl.SplashActivityModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ISplashActivytPresenter;
import com.ysp.houge.ui.iview.ISplashActivity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

/**
 * * @描述:
 *
 * @author Ocean
 * @version 1.01
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2016/8/3
 * Email: m13478943650@163.com
 */
public class SplashActivityPresenter extends BasePresenter<ISplashActivity> implements ISplashActivytPresenter {
    private VersionInfoEntity entity;
    private ISplashModel model;
    public SplashActivityPresenter(ISplashActivity view) {
        super(view);
    }

    @Override
    public void initModel() {
        model = new SplashActivityModelImpl();

    }

    @Override
    public void getLatestVertionInfo() {

        model.getVersionInfoFromNet(new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                if (data != null){
                    entity = new VersionInfoEntity();
                    entity = (VersionInfoEntity) data;
                    mView.checkVersionInfo(entity);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                Log.d("SplashActivityPresenter", message);
            }
        });

    }
}
