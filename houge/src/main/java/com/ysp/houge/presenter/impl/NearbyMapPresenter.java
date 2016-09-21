package com.ysp.houge.presenter.impl;

import com.ysp.houge.model.INearbyModel;
import com.ysp.houge.model.entity.bean.NearbyMapResultEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.NearbyModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.INearbyMapPresenter;
import com.ysp.houge.ui.iview.INearbyMapPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.List;

/**
 * 描述： 附近页面Presenter层
 *
 * @ClassName: NearbyMapPresenter
 * @author: hx
 * @date: 2015年12月9日 下午1:39:52
 * <p/>
 * 版本: 1.0
 */
public class NearbyMapPresenter extends BasePresenter<INearbyMapPageView> implements INearbyMapPresenter {
    private WorkTypeEntity workTypeEntity;
    private INearbyModel iNearbyModel;
    private List<UserInfoEntity> list;

    public NearbyMapPresenter(INearbyMapPageView view) {
        super(view);
    }

    @Override
    public void initModel() {
        iNearbyModel = new NearbyModelImpl();
    }

    @Override
    public void setWorkType(WorkTypeEntity entity) {
        this.workTypeEntity = entity;
    }

    @Override
    public void getNearBySkillList(double latitude, double langtitude) {
        StringBuilder gps = new StringBuilder();
        gps.append(latitude);
        gps.append(",");
        gps.append(langtitude);
        if (mView != null && workTypeEntity != null) {
            iNearbyModel.getMapNearbySkillList(workTypeEntity.getId(), gps.toString(), new OnNetResponseCallback() {
                @Override
                public void onSuccess(Object data) {
                    if (null != data && data instanceof NearbyMapResultEntity) {
                        mView.showMapMarkerList(((NearbyMapResultEntity) data).getUserList());
                    }
                }


                @Override
                public void onError(int errorCode, String message) {
                    mView.showToast("网络错误");
                }
            });
        }
    }

    @Override
    public void getNearByNeedList(double latitude, double langtitude) {
        StringBuilder gps = new StringBuilder();
        gps.append(latitude);
        gps.append(",");
        gps.append(langtitude);
        if (mView != null && workTypeEntity != null) {
            iNearbyModel.getMapNearbyNeedList(workTypeEntity.getId(), gps.toString(), new OnNetResponseCallback() {
                @Override
                public void onSuccess(Object data) {
                    if (null != data && data instanceof NearbyMapResultEntity) {
                        mView.showMapMarkerList(((NearbyMapResultEntity) data).getUserList());
                    }
                }

                @Override
                public void onError(int errorCode, String message) {
                    mView.showToast("网络错误");
                }
            });
        }
    }
}