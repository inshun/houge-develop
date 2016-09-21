package com.ysp.houge.ui.iview;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by it_huangxin on 2016/2/1.
 */
public interface IResidentAddressPageView extends IBaseView {

    void startLocal();

    void moveToAddress(LatLng latLng,boolean needGeo);
}
