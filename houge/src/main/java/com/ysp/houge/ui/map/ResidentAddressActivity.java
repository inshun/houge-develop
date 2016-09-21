package com.ysp.houge.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.presenter.IResidentAddressPresenter;
import com.ysp.houge.presenter.impl.ResidentAddressPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IResidentAddressPageView;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.widget.MyActionBar;
/**
 * Created by it_huangxin on 2016/2/1.
 */
public class ResidentAddressActivity extends BaseFragmentActivity implements IResidentAddressPageView,BDLocationListener,OnGetGeoCoderResultListener {
    //进入标记，用于区分进入页面是的需求
    public static final String KEY = "enter_key";
    /** 直接保存 */
    public static final int TYPE_SAVE_RESULT = 0;
    /** 返回地图页面结果 */
    public static final int TYPE_RETURN_RESULT = 1;
    LocationChooseEventBusEntity localEntity;
    private Button sure;
    private MapView mapView;
    private int type;
    private LatLng latLng;
    private GeoCoder geoCoder;
    private LocationClient locationClient;
    private IResidentAddressPresenter iResidentAddressPresenter;
    private Bundle bundle;
    private MyActionBar actionBar;

    public static void jumpIn(Context context,Bundle bundle){
        Intent intent = new Intent(context,ResidentAddressActivity.class);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_resident_address);
        check();
    }

    public void check(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL
            // _STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }else {
            //已赋予权限
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 允许
            } else {
                //无权限
                showToast("请打开对应的权限，否者会导致App无法正常运行！");
            }
        }
    }

    @Override
    protected void initActionbar() {
        actionBar = new MyActionBar(this);
        bundle = getIntent().getExtras();
        if(bundle.getInt("TAG") ==  2) {
            actionBar.setRightText("下一步");
            actionBar.setRightClickListenner(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != iResidentAddressPresenter) {
                        switch (type) {
                            case TYPE_SAVE_RESULT:
                                iResidentAddressPresenter.addOrUpdate(latLng);
                                break;
                            case TYPE_RETURN_RESULT:
                                if (null == localEntity)
                                    showToast("位置问题");
                                else
                                    EventBus.getDefault().post(localEntity);
                                close();
                                break;
                        }
                    }
                }
            });
        }
       actionBar.setTitle("设置接活地址");
        RelativeLayout actionbar = (RelativeLayout) findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
    }
    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        mapView = (MapView) findViewById(R.id.mMapView);
        sure = (Button) findViewById(R.id.mSureBtn);
        if(bundle.getInt("TAG") ==  2){
            sure.setVisibility(View.GONE);
        }
        mapView.showZoomControls(false);
        mapView.showScaleControl(false);
        mapView.getMap().getUiSettings().setCompassEnabled(false);
        mapView.getMap().getUiSettings().setRotateGesturesEnabled(false);
        mapView.getMap().getUiSettings().setOverlookingGesturesEnabled(false);
        mapView.getMap().setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(17).build()));
        mapView.getMap().setMaxAndMinZoomLevel(3, 18);
        mapView.getMap().setOnMapTouchListener(new BaiduMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_UP:
                        if (null == mapView)
                            return;
                        Point point = new Point(mapView.getMap().getMapStatus().targetScreen.x,
                                mapView.getMap().getMapStatus().targetScreen.y);
                        LatLng location = mapView.getMap().getProjection().fromScreenLocation(point);
                        geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(location));
                        break;
                    default:
                        break;
                }
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != iResidentAddressPresenter) {
                    switch (type) {
                        case TYPE_SAVE_RESULT:
                            iResidentAddressPresenter.addOrUpdate(latLng);
                            break;
                        case TYPE_RETURN_RESULT:
                            if (null == localEntity)
                                showToast("位置问题");
                            else
                                EventBus.getDefault().post(localEntity);
                            close();
                            break;
                    }
                }
            }
        });
    }
    @Override
    protected void initializeData(Bundle savedInstanceState) {
        if (null != getIntent() && null != getIntent().getExtras() && getIntent().getExtras().containsKey(KEY)){
            type = getIntent().getExtras().getInt(KEY);
        }else {
            close();
        }
        geoCoder = GeoCoder.newInstance();
        geoCoder.setOnGetGeoCodeResultListener(this);
        iResidentAddressPresenter = new ResidentAddressPresenter(this);
        iResidentAddressPresenter.getResidentAddress();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationClient != null) {
            // 退出时销毁定位
            locationClient.stop();
        }
        // 关闭定位图层
        mapView.getMap().setMyLocationEnabled(false);
        mapView.onDestroy();
        geoCoder.destroy();
        mapView = null;
    }
    @Override
    public void startLocal() {
        mapView.getMap().setMyLocationEnabled(true);
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        locationClient.setLocOption(option);
        locationClient.start();
    }
    @Override
    public void moveToAddress(LatLng latLng,boolean needGeo) {
        if (needGeo){
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(latLng));
            return;
        }
        MapStatus mapStatus = new MapStatus.Builder().target(latLng).build();
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);
        mapView.getMap().animateMapStatus(mapStatusUpdate);
        this.latLng = latLng;
    }
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (null != bdLocation){
            locationClient.stop();
            geoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude())));
        }
    }
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        if (geoCodeResult == null || geoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有检索到结果
            return;
        }
    }
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (null == mapView){
            return;
        }
        mapView.getMap().clear();
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            // 没有找到检索结果
            showToast("没有检测到地址信息");
            return;
        }
        // 为InfoWindow创建View
        View view = LayoutInflater.from(ResidentAddressActivity.this).inflate(R.layout.view_map_resident_address,null);
        TextView textView = (TextView) view.findViewById(R.id.tv_address);
        textView.setText(result.getAddress());
        // 创建InfoWindow , 传入 view， 地理坐标， y 轴偏移量
        InfoWindow mInfoWindow = new InfoWindow(view, result.getLocation(),
                (int) (SizeUtils.dip2px(ResidentAddressActivity.this, -50)));
        // 显示InfoWindow
        mapView.getMap().showInfoWindow(mInfoWindow);
        moveToAddress(result.getLocation(),false);
        if (null == localEntity) localEntity = new LocationChooseEventBusEntity();
        localEntity.address = result.getAddress();
        localEntity.province = result.getAddressDetail().province;
        localEntity.city = result.getAddressDetail().city;
        localEntity.latitude = result.getLocation().latitude;
        localEntity.langtitude = result.getLocation().longitude;
        localEntity.getResult = true;
    }

}