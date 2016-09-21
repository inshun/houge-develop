package com.ysp.houge.ui.recommend;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.model.entity.eventbus.RecommendSettingFinishEantity;
import com.ysp.houge.model.entity.eventbus.WorkTypeSetFinishEventBusEntity;
import com.ysp.houge.presenter.IRecommendNeedSettingPresenter;
import com.ysp.houge.presenter.impl.recmmend.RecommendNeedSettingPresenter;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.iview.IRecommendNeedSettingPageView;
import com.ysp.houge.ui.map.ResidentAddressActivity;

import java.util.List;

/**
 * 描述： 关注需求设置
 *
 * @ClassName: RecommendNeedSettingActivity
 * @author: hx
 * @date: 2015年12月18日 上午10:30:44
 * <p/>
 * 版本: 1.0
 */
public class RecommendNeedSettingActivity extends BaseFragmentActivity
        implements IRecommendNeedSettingPageView, OnMapClickListener, BDLocationListener, OnClickListener {
    public final static int SERVICE_AREA_10 = 10;
    public final static int SERVICE_AREA_20 = 20;
    public final static int SERVICE_AREA_30 = 30;
    public final static int SERVICE_AREA_40 = 40;
    public final static int SERVICE_AREA_50 = 50;

    public final static String WORK_TYPE_KEY = "workType";
    // 定位相关
    public LocationClient mLocClient;
    private TextView tvAdd;// 点击添加的提示文本
    private TextView textView;// 显示需求列表的文本
    private MapView mMapView;// 地图
    private Drawable drawableDef;
    private Drawable drawableSel;

    private ImageView iTen, iTwenty, iThirty, iForty, iFifty;
    private TextView tTen, tTwenty, tThirty, tForty, tFifty;
    private View one, two, three, four;

    private IRecommendNeedSettingPresenter iRecommendNeedSettingPresenter;

    public static void jumpIn(Context context, Bundle bundle) {
        Intent intent = new Intent(context, RecommendNeedSettingActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_recommend_need_setting);
        check();
    }


    public void check(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);
        }else {
            //已经赋予过权限
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 允许
                Toast.makeText(this,"有权限",Toast.LENGTH_SHORT).show();
            } else {
                    //无权限
                    showToast("请打开对应的权限，否者会导致App无法正常运行！");
            }
        }
    }

    @Override
    protected void initActionbar() {
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        findViewById(R.id.ll_title_finish).setOnClickListener(this);
        findViewById(R.id.rela_work_type_list).setOnClickListener(this);

        textView = (TextView) findViewById(R.id.tv_recommend_list);
        tvAdd = (TextView) findViewById(R.id.tv_cliick_to_add);
        textView.setOnClickListener(this);
        tvAdd.setOnClickListener(this);

        mMapView = (MapView) findViewById(R.id.mv_my_address);
        mMapView.getMap().setOnMapClickListener(this);

        tTen = (TextView) findViewById(R.id.tv_ten);
        tTwenty = (TextView) findViewById(R.id.tv_twenty);
        tThirty = (TextView) findViewById(R.id.tv_thirty);
        tForty = (TextView) findViewById(R.id.tv_forty);
        tFifty = (TextView) findViewById(R.id.tv_fifty);

        tTen.setOnClickListener(this);
        tTwenty.setOnClickListener(this);
        tThirty.setOnClickListener(this);
        tForty.setOnClickListener(this);
        tFifty.setOnClickListener(this);

        iTen = (ImageView) findViewById(R.id.iv_ten_icon);
        iTwenty = (ImageView) findViewById(R.id.iv_twenty_icon);
        iThirty = (ImageView) findViewById(R.id.iv_thirty_icon);
        iForty = (ImageView) findViewById(R.id.iv_forty_icon);
        iFifty = (ImageView) findViewById(R.id.iv_fifty_icon);

        iTen.setOnClickListener(this);
        iTwenty.setOnClickListener(this);
        iThirty.setOnClickListener(this);
        iForty.setOnClickListener(this);
        iFifty.setOnClickListener(this);

        one = findViewById(R.id.v_one);
        two = findViewById(R.id.v_two);
        three = findViewById(R.id.v_three);
        four = findViewById(R.id.v_four);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        iRecommendNeedSettingPresenter = new RecommendNeedSettingPresenter(this);
        if (getIntent().getExtras().containsKey(WORK_TYPE_KEY)) {
            // 获得设置的关注点并显示在上面
            notifyListDate(getIntent().getExtras().getString(WORK_TYPE_KEY));
        }

        // 地图相关设置
        mMapView.showZoomControls(false);
        mMapView.showScaleControl(false);
        mMapView.getMap().getUiSettings().setCompassEnabled(false);
        mMapView.getMap().getUiSettings().setRotateGesturesEnabled(false);
        mMapView.getMap().getUiSettings().setOverlookingGesturesEnabled(false);
        mMapView.getMap().setMaxAndMinZoomLevel(10, 18);

        // 获取设置的位置或者定位
        iRecommendNeedSettingPresenter.getSetting();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                jumpToHome(false);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.getMap().setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void notifyListDate(String string) {
        if (TextUtils.isEmpty(string)) {
            tvAdd.setVisibility(View.VISIBLE);
        } else {
            tvAdd.setVisibility(View.GONE);
            textView.setText(string);
        }
    }

    @Override
    public void notifyListDate(List<WorkTypeEntity> entities) {
        if (entities == null || entities.size() < 1) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < entities.size(); i++) {
            if (TextUtils.equals(entities.get(i).getIs_watch(), "yes")) {
                sb.append(entities.get(i).getName());
                sb.append(" 丨 ");
            }
        }

        sb.delete(sb.length() - 3, sb.length());
        notifyListDate(sb.toString());
    }

    @Override
    public void setAddress(AddressEntity address) {
        if (address != null && address.latitude != null && address.latitude > 0 && address.longitude != null
                && address.longitude > 0) {
            // 有地理坐标加载
            mMapView.getMap().clear();
            // 定义Maker坐标点
            LatLng point = new LatLng(address.latitude, address.longitude);
            // 构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);
            // 构建MarkerOption，用于在地图上添加Marker
            OverlayOptions option = new MarkerOptions().position(point).icon(bitmap);
            // 在地图上添加Marker，并显示
            mMapView.getMap().addOverlay(option);

            MapStatus u = new MapStatus.Builder()
                    // 设置中心点
                    .target(point)
                            // 放大的倍数
                    .zoom(18).build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(u);
            mMapView.getMap().animateMapStatus(mapStatusUpdate);
        } else {
            // 定位到当前位置
            mMapView.getMap().setMyLocationEnabled(true);
            // 定位初始化
            mLocClient = new LocationClient(this);
            mLocClient.registerLocationListener(this);
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);// 打开gps
            option.setCoorType("bd09ll"); // 设置坐标类型
            option.setScanSpan(1000 * 5);// 定位5秒一次
            mLocClient.setLocOption(option);
            if (!mLocClient.isStarted()) {
                mLocClient.start();
            }
        }
    }

    @Override
    public void destroyLocationClient() {
        // 第二次定位返回后直接销毁，节约资源
        if (mLocClient != null && mLocClient.isStarted()) {
            mLocClient.stop();
        }
    }

    @Override
    public void setServiceDistance(int serviceArea) {
        int lineColorDef = getResources().getColor(R.color.color_e5e5e5);
        int textColorDef = getResources().getColor(R.color.color_999999);
        int textColorSel = getResources().getColor(R.color.color_app_theme_blue_bg);
        if (null == drawableSel) {
            drawableDef = getResources().getDrawable(R.drawable.shapa_service_area_def);
        }

        if (null == drawableSel) {
            drawableSel = getResources().getDrawable(R.drawable.shapa_service_area_sel);
        }

        switch (serviceArea) {
            case SERVICE_AREA_10:
                tTen.setTextColor(textColorSel);
                tTwenty.setTextColor(textColorDef);
                tThirty.setTextColor(textColorDef);
                tForty.setTextColor(textColorDef);
                tFifty.setTextColor(textColorDef);

                iTen.setImageDrawable(drawableSel);
                iTwenty.setImageDrawable(drawableDef);
                iThirty.setImageDrawable(drawableDef);
                iForty.setImageDrawable(drawableDef);
                iFifty.setImageDrawable(drawableDef);

                one.setBackgroundColor(lineColorDef);
                two.setBackgroundColor(lineColorDef);
                three.setBackgroundColor(lineColorDef);
                four.setBackgroundColor(lineColorDef);
                break;
            case SERVICE_AREA_20:
                tTen.setTextColor(textColorSel);
                tTwenty.setTextColor(textColorSel);
                tThirty.setTextColor(textColorDef);
                tForty.setTextColor(textColorDef);
                tFifty.setTextColor(textColorDef);

                iTen.setImageDrawable(drawableSel);
                iTwenty.setImageDrawable(drawableSel);
                iThirty.setImageDrawable(drawableDef);
                iForty.setImageDrawable(drawableDef);
                iFifty.setImageDrawable(drawableDef);

                one.setBackgroundColor(textColorSel);
                two.setBackgroundColor(lineColorDef);
                three.setBackgroundColor(lineColorDef);
                four.setBackgroundColor(lineColorDef);
                break;
            case SERVICE_AREA_30:
                tTen.setTextColor(textColorSel);
                tTwenty.setTextColor(textColorSel);
                tThirty.setTextColor(textColorSel);
                tForty.setTextColor(textColorDef);
                tFifty.setTextColor(textColorDef);

                iTen.setImageDrawable(drawableSel);
                iTwenty.setImageDrawable(drawableSel);
                iThirty.setImageDrawable(drawableSel);
                iForty.setImageDrawable(drawableDef);
                iFifty.setImageDrawable(drawableDef);

                one.setBackgroundColor(textColorSel);
                two.setBackgroundColor(textColorSel);
                three.setBackgroundColor(lineColorDef);
                four.setBackgroundColor(lineColorDef);
                break;
            case SERVICE_AREA_40:
                tTen.setTextColor(textColorSel);
                tTwenty.setTextColor(textColorSel);
                tThirty.setTextColor(textColorSel);
                tForty.setTextColor(textColorSel);
                tFifty.setTextColor(textColorDef);

                iTen.setImageDrawable(drawableSel);
                iTwenty.setImageDrawable(drawableSel);
                iThirty.setImageDrawable(drawableSel);
                iForty.setImageDrawable(drawableSel);
                iFifty.setImageDrawable(drawableDef);

                one.setBackgroundColor(textColorSel);
                two.setBackgroundColor(textColorSel);
                three.setBackgroundColor(textColorSel);
                four.setBackgroundColor(lineColorDef);
                break;
            case SERVICE_AREA_50:
                tTen.setTextColor(textColorSel);
                tTwenty.setTextColor(textColorSel);
                tThirty.setTextColor(textColorSel);
                tForty.setTextColor(textColorSel);
                tFifty.setTextColor(textColorSel);

                iTen.setImageDrawable(drawableSel);
                iTwenty.setImageDrawable(drawableSel);
                iThirty.setImageDrawable(drawableSel);
                iForty.setImageDrawable(drawableSel);
                iFifty.setImageDrawable(drawableSel);

                one.setBackgroundColor(textColorSel);
                two.setBackgroundColor(textColorSel);
                three.setBackgroundColor(textColorSel);
                four.setBackgroundColor(textColorSel);
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        iRecommendNeedSettingPresenter.onMapClick();
    }

    @Override
    public boolean onMapPoiClick(MapPoi arg0) {
        return false;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        // 定位回掉
        iRecommendNeedSettingPresenter.onReceiveLocation(location);
    }

    @Override
    public void jumpToSkillList() {
        // TODO 跳转到能力列表
        Bundle bundle = new Bundle();
        bundle.putString(WorkTypeListActivity.KEY, textView.getText().toString());
        WorkTypeListActivity.jumpIn(this, bundle);
    }

    @Override
    public void jumpToChooseMap(double latitude, double langtitude) {
        Bundle bundle = new Bundle();
        bundle.putInt(ResidentAddressActivity.KEY, ResidentAddressActivity.TYPE_RETURN_RESULT);
        ResidentAddressActivity.jumpIn(this, bundle);
    }

    @Override
    public void jumpToHome(boolean isRefresh) {
        // TODO 回到首页
        if (isRefresh) {
            // 发送消息通知首页刷新
            EventBus.getDefault().post(new RecommendSettingFinishEantity());
        }
        close();
        overridePendingTransition(R.anim.activity_stay, R.anim.slide_out_to_bottom);
    }

    /**
     * 接收工种列表页面的数据
     */
    public void onEventMainThread(WorkTypeSetFinishEventBusEntity busEntity) {
        iRecommendNeedSettingPresenter.finishAddSkillList(busEntity);
    }

    /**
     * 地图取点返回
     */
    public void onEventMainThread(LocationChooseEventBusEntity busEntity) {
        iRecommendNeedSettingPresenter.chooseMapFinish(busEntity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 完成设置
            case R.id.ll_title_finish:
                iRecommendNeedSettingPresenter.setFinish();
                break;

            // 工种列表
            case R.id.tv_recommend_list:
            case R.id.tv_cliick_to_add:
            case R.id.rela_work_type_list:
                iRecommendNeedSettingPresenter.addSkillList();
                break;

            // 范围10
            case R.id.tv_ten:
            case R.id.iv_ten_icon:
                iRecommendNeedSettingPresenter.setServiceArea(SERVICE_AREA_10);

                break;

            // 范围20
            case R.id.tv_twenty:
            case R.id.iv_twenty_icon:
                iRecommendNeedSettingPresenter.setServiceArea(SERVICE_AREA_20);
                break;

            // 范围30
            case R.id.tv_thirty:
            case R.id.iv_thirty_icon:
                iRecommendNeedSettingPresenter.setServiceArea(SERVICE_AREA_30);
                break;

            // 范围40
            case R.id.tv_forty:
            case R.id.iv_forty_icon:
                iRecommendNeedSettingPresenter.setServiceArea(SERVICE_AREA_40);
                break;

            // 范围50
            case R.id.tv_fifty:
            case R.id.iv_fifty_icon:
                iRecommendNeedSettingPresenter.setServiceArea(SERVICE_AREA_50);
                break;
        }
    }
}
