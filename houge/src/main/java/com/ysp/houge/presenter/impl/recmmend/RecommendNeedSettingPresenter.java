package com.ysp.houge.presenter.impl.recmmend;

import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.IGetWorkTypeModel;
import com.ysp.houge.model.ISettingModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.SettingEntity;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;
import com.ysp.houge.model.impl.AddressModelImpl;
import com.ysp.houge.model.impl.GetWorkTypeModelImpl;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IRecommendNeedSettingPresenter;
import com.ysp.houge.ui.iview.IRecommendNeedSettingPageView;
import com.ysp.houge.ui.recommend.RecommendNeedSettingActivity;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.List;

/**
 * @author it_hu
 *         <p/>
 *         卖家关注设置presenter层
 */
public class RecommendNeedSettingPresenter extends BasePresenter<IRecommendNeedSettingPageView>
        implements IRecommendNeedSettingPresenter {
    private boolean isRecommendChange = false;
    private boolean isAddressChange = false;
    private boolean isServiceDistanceChange = false;
    private int localCount = 0;// 定位的标记

    private AddressEntity addressEntity;
    private WorkTypeSetFinishEventBusEntity busEntity;
    private int serviceArea = RecommendNeedSettingActivity.SERVICE_AREA_10;

    private IGetWorkTypeModel iGetWorkTypeModel;
    private IAddressModel iAddressModel;
    private ISettingModel iSettingModel;

    public RecommendNeedSettingPresenter(IRecommendNeedSettingPageView view) {
        super(view);
        addressEntity = new AddressEntity();
    }

    @Override
    public void initModel() {
        iGetWorkTypeModel = new GetWorkTypeModelImpl();
        iAddressModel = new AddressModelImpl();
        iSettingModel = new SettingModelImpl();
    }

    @Override
    public void getSetting() {
        /** 获得地址以及获得距离(这里关注点的数据已经从上一个页面传过来了,所以忽略) */
        getAddress();
    }

    @Override
    public void addSkillList() {
        mView.jumpToSkillList();
    }

    @Override
    public void finishAddSkillList(WorkTypeSetFinishEventBusEntity busEntity) {
        isRecommendChange = true;
        this.busEntity = busEntity;
        mView.notifyListDate(busEntity.entities);
    }

    @Override
    public void getAddress() {
        mView.showProgress();
        iAddressModel.getDefaultAddress(MyApplication.getInstance().getCurrentUid(), MyApplication.LOG_STATUS_SELLER, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                if (null != data && data instanceof AddressEntity) {
                    addressEntity = (AddressEntity) data;
                    mView.setAddress(addressEntity);
                } else {
                    mView.setAddress(null);
                }
                getServiceDistance();
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                mView.setAddress(null);
                getServiceDistance();
            }
        });
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        switch (localCount) {
            case 0:
                localCount++;
                addressEntity.latitude = location.getLatitude();
                addressEntity.longitude = location.getLongitude();

                // 两个都不大于0，代表定位失败
                if (!(addressEntity.latitude > 0 && addressEntity.longitude > 0)) {
                    localCount = 0;// 这里可以让他再次定位
                } else {
                    mView.setAddress(addressEntity);
                }
                break;

            default:
                // 已经有经纬度,销毁定位服务
                mView.destroyLocationClient();
                break;
        }
    }

    @Override
    public void onMapClick() {
        mView.jumpToChooseMap(addressEntity.latitude, addressEntity.longitude);
    }

    @Override
    public void chooseMapFinish(LocationChooseEventBusEntity busEntity) {
        if (busEntity.langtitude > 0 && busEntity.latitude > 0 && !TextUtils.isEmpty(busEntity.address)) {
            isAddressChange = true;
            addressEntity.latitude = busEntity.latitude;
            addressEntity.longitude = busEntity.langtitude;
            busEntity.address = busEntity.address;
            mView.setAddress(addressEntity);
        }
    }

    @Override
    public void getServiceDistance() {
        mView.showProgress();
        iSettingModel.getSetting(new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                if (null != data && data instanceof List<?>) {
                    List<SettingEntity> list = (List<SettingEntity>) data;
                    // 循环找到服务距离字段
                    for (int i = 0; i < list.size(); i++) {
                        if (TextUtils.equals(list.get(i).name, "service_distance")) {
                            try {
                                // 这里需要捕获异常，因为未设置的时候回返回false
                                serviceArea = Integer.parseInt(list.get(i).value);
                            } catch (Exception e) {
                            }
                            break;
                        }
                    }
                }
                mView.setServiceDistance(serviceArea);
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                // 设置默认的距离
                mView.setServiceDistance(serviceArea);
            }

        });
    }

    @Override
    public void setServiceArea(int serviceArea) {
        isServiceDistanceChange = true;
        this.serviceArea = serviceArea;
        mView.setServiceDistance(serviceArea);
    }

    @Override
    public String setFinish() {
        // 三个都未设置，直接退出
//        if (!isRecommendChange && !isAddressChange && !isServiceDistanceChange) {
//            mView.jumpToHome(false);
//        } else {
            // 按照页面顺序，那个开始了就从那个开始设置
            if (isRecommendChange) {
                requestRecommend();
                return null;
            }

            if (isAddressChange) {
                requestAddress();
                return null;
            }

            if (isServiceDistanceChange) {
                requestServiceDistance();
                return null;
            }
//        }
        return null;
    }

    /**
     * 关注点请求
     */
    private void requestRecommend() {
        mView.showProgress();
        iGetWorkTypeModel.recommendSetting(busEntity.delList, busEntity.list, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (isAddressChange) {
                    requestAddress();
                    return;
                }

                if (isServiceDistanceChange) {
                    requestServiceDistance();
                    return;
                }

                mView.showToast("设置完成");
                mView.jumpToHome(true);
            }

            @Override
            public void onError(int errorCode, String message) {
                //失败了不接着往下设置
                mView.hideProgress();
                switch (errorCode) {
                    case ResponseCode.TIP_ERROR:
                        mView.showToast(message);
                        break;
                    default:
                        mView.showToast(R.string.request_failed);
                        break;
                }
            }
        });
    }

    /**
     * 地址请求
     */
    private void requestAddress() {
        mView.showProgress();
        iAddressModel.addOrUpdateAddress(MyApplication.LOG_STATUS_SELLER, addressEntity.id,
                MyApplication.getInstance().getCurrentUid(), "", "", "", "", "", true, addressEntity.longitude,
                addressEntity.latitude, new OnNetResponseCallback() {

                    @Override
                    public void onSuccess(Object data) {
                        if (isServiceDistanceChange) {
                            requestServiceDistance();
                            return;
                        }
                        mView.showToast("设置完成");
                        mView.jumpToHome(true);
                    }

                    @Override
                    public void onError(int errorCode, String message) {
                        mView.hideProgress();
                        switch (errorCode) {
                            case ResponseCode.TIP_ERROR:
                                mView.showToast(message);
                                break;
                            default:
                                mView.showToast(R.string.request_failed);
                                break;
                        }
                    }
                });
    }

    /**
     * 服务范围请求
     */
    private void requestServiceDistance() {
        iSettingModel.setting("", "", "", String.valueOf(serviceArea), new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                mView.showToast("设置完成");
                mView.jumpToHome(true);
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                switch (errorCode) {
                    case ResponseCode.TIP_ERROR:
                        mView.showToast(message);
                        break;
                    default:
                        mView.showToast(R.string.request_failed);
                        break;
                }
            }
        });
    }
}
