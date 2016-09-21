package com.ysp.houge.presenter;

import com.baidu.mapapi.model.LatLng;

/**
 * Created by it_huangxin on 2016/2/1.
 */
public interface IResidentAddressPresenter{

    /**获取设置的常驻地*/
    void getResidentAddress();

    /** 修改常驻地 */
    void addOrUpdate(LatLng latLng);

}
