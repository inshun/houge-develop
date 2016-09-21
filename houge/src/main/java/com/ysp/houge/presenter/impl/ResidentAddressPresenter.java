package com.ysp.houge.presenter.impl;
import com.baidu.mapapi.model.LatLng;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.impl.AddressModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IResidentAddressPresenter;
import com.ysp.houge.ui.iview.IResidentAddressPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
/**
 * Created by it_huangxin on 2016/2/1.
 */
public class ResidentAddressPresenter extends BasePresenter<IResidentAddressPageView> implements IResidentAddressPresenter {
    private IAddressModel iAddressModel;
    private AddressEntity addressEntity;
    public ResidentAddressPresenter(IResidentAddressPageView view) {
        super(view);
    }
    @Override
    public void initModel() {
        iAddressModel = new AddressModelImpl();
    }
    @Override
    public void getResidentAddress() {
//        mView.showProgress();
        iAddressModel.getDefaultAddress(MyApplication.getInstance().getCurrentUid(),MyApplication.LOG_STATUS_SELLER, new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
//                mView.hideProgress();
                if (null != data && data instanceof AddressEntity) {
                    addressEntity = (AddressEntity) data;
                    mView.moveToAddress(new LatLng(addressEntity.latitude,addressEntity.longitude),true);
                }else {
                    mView.startLocal();
                }
            }
            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                mView.startLocal();
            }
        });
    }
    @Override
    public void addOrUpdate(LatLng latLng) {
        if (null == latLng){
            mView.showToast("错误的信息");
            return;
        }else {
            if (null == addressEntity) {
                addressEntity = new AddressEntity();
                addressEntity.id = 0;
            }
            addressEntity.latitude = latLng.latitude;
            addressEntity.longitude =latLng.longitude;
            mView.showProgress();
            iAddressModel.addOrUpdateAddress(MyApplication.LOG_STATUS_SELLER, addressEntity.id,
                    MyApplication.getInstance().getCurrentUid(), "", "", "", "", "", true, addressEntity.longitude,
                    addressEntity.latitude, new OnNetResponseCallback() {
                        @Override
                        public void onSuccess(Object data) {
                            mView.hideProgress();
                            mView.showToast("设置完成");
                            mView.close();
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
}

