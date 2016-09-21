/*   
 * Copyright (c) 2010-2020 Founder Ltd. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
package com.ysp.houge.app;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.easemob.EMConnectionListener;
import com.easemob.EMError;
import com.easemob.EMEventListener;
import com.easemob.EMNotifierEvent;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMMessage;
import com.easemob.easeui.controller.EaseUI;
import com.easemob.easeui.domain.EaseUser;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.BuildConfig;
import com.ysp.houge.dbcontroller.UserContorller;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.dbmanager.DatabaseHelper;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.bean.PhoneInfo;
import com.ysp.houge.model.entity.db.UserEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.entity.eventbus.LogoutEventBusEntity;
import com.ysp.houge.service.LocationService;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.PreferenceUtils;
import com.ysp.houge.utility.SystemUtils;
import com.ysp.houge.utility.Toaster;
import com.ysp.houge.utility.imageloader.ImageLoaderManager;
import com.ysp.houge.utility.volley.HttpFileRequest;
import com.ysp.houge.utility.volley.HttpRequest;
import com.ysp.houge.utility.volley.MultiPartStack;

import java.util.List;

/**
 * @author tyn
 * @version 1.0
 * @描述:自定义的应用程序全局变量
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * @date 2015年7月13日下午10:13:41
 */
public class MyApplication extends MultiDexApplication implements EMConnectionListener, EaseUI.EaseUserProfileProvider, BDLocationListener {
    /**
     * 买家
     */
    public static final int LOG_STATUS_BUYER = 1;
    /**
     * 卖家
     */
    public static final int LOG_STATUS_SELLER = 2;


    //手机信息对象
    private PhoneInfo phoneInfo;
    //当前位置对象
    private BDLocation bdLocation;
    //用于定时定位的Handler
    private AppHandler appHandler;
    //登录用户对象
    private UserInfoEntity selfUserInfo;
    //配置工具对象
    private PreferenceUtils preferenceUtils;
    //ImageLoader加载对象
    private ImageLoaderManager imageLoaderManager;
    //消息数量实体
    private MessageCountEntity messageCountEntity;
    //单利application对象
    private static MyApplication globalContext = null;
    //定位服务对象
    private static LocationService locationService = null;

    public static MyApplication getInstance() {
        return globalContext;
    }

    //内存分析插件
//    public static RefWatcher getRefWacher(Context context) {
//        MyApplication application = (MyApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }
//
//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        int pid = android.os.Process.myPid();
        String processAppName = SystemUtils.getAppName(this, pid);

        //防止在程序的服务中启动
        if (processAppName == null || !processAppName.equalsIgnoreCase("com.ysp.houge")) {
            return;
        }
        globalContext = this;
        // 初始化数据库(ORMLite框架)
        DatabaseHelper.init(this);
        // 初始化toast提示
        Toaster.init(globalContext);
        // 初始化百度地图配置
        SDKInitializer.initialize(globalContext);
        // 初始化网络请求库（Volley）
        HttpRequest.buildRequestQueue(globalContext);
        //实例化消息提醒数量的实体
        messageCountEntity = new MessageCountEntity();
        // 初始化图片加载库(Imageloader)
        imageLoaderManager = new ImageLoaderManager(globalContext);
        // 初始化配置
        preferenceUtils = PreferenceUtils.getInstance(globalContext);
        // 初始化图片上传网络请求库（Volley）
        HttpFileRequest.buildRequestQueue(globalContext, new MultiPartStack());

        //友盟统计日志加密
        //AnalyticsConfig.enableEncrypt(true);
        //取消友盟统计默认的页面统计方案
        MobclickAgent.openActivityDurationTrack(false);
        //设置页面统计方案的超时时间5分钟
        MobclickAgent.setSessionContinueMillis(5 * 60 * 1000);
        //友盟分享的平台配置 start
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105096616", "t3LCUnZwlIWQf0VH");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("1753568615", "7f35f1f4002c64630408559bb64c13b9");
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx0e42514e2de65d8d", "162aa70adc28a6f8c5392b17c1ffef59");
        //友盟分享的平台配置 end

        //环信SDK初始化
        initIM();
        //定位
        startLocal();
        //友盟LOG以及toast设置
        Log.LOG = BuildConfig.DEBUG;
        Config.IsToastTip = BuildConfig.DEBUG;

        String local = MyApplication.getInstance().getPreferenceUtils().getLocal();
        if (!TextUtils.isEmpty(local)) {
            bdLocation = new Gson().fromJson(local, BDLocation.class);
        }

//        leakCanary
//      refWatcher =  LeakCanary.install(this);
    }


    // 环信SDK部分
    private void initIM() {

        // 初始化环信SDK
        EMChat.getInstance().init(globalContext);
        /**
         * debugMode == true 时为打开，SDK会在log里输入调试信息
         * @param debugMode
         * 在做代码混淆的时候需要设置成false
         */
        EMChat.getInstance().setDebugMode(false);//在做打包混淆时，要关闭debug模式，避免消耗不必要的资源
        EMChat.getInstance().init(globalContext);
        EaseUI.getInstance().setUserProfileProvider(this);
        EaseUI.getInstance().init(globalContext);
        EMChatManager.getInstance().addConnectionListener(this);

        if (EMChat.getInstance().isLoggedIn()) {
            EMChatManager.getInstance().loadAllConversations();
            // 获取到EMChatOptions对象
            final EMChatOptions options = EMChatManager.getInstance().getChatOptions();
            EMChatManager.getInstance().registerEventListener(new EMEventListener() {
                                                                  @Override
                                                                  public void onEvent(EMNotifierEvent event) {
                                                                      EMMessage message = (EMMessage) event.getData();
                                                                      if (null != message) {
                                                                          EventBus.getDefault().post(new MessageCountEntity());
                                                                          //应用在后台，不需要刷新UI,通知栏提示新消息
                                                                          if (!isAppOnForeground(MyApplication.this)) {
                                                                              EaseUI.getInstance().getNotifier().onNewMsg(message);
                                                                          } else {
                                                                              EaseUI.getInstance().getNotifier().viberateAndPlayTone(message);
                                                                          }
                                                                      }
                                                                  }
                                                              }, new EMNotifierEvent.Event[]{EMNotifierEvent.Event.EventNewMessage, EMNotifierEvent.Event.EventOfflineMessage, EMNotifierEvent.Event.EventDeliveryAck, EMNotifierEvent.Event.EventReadAck}
            );
            //APP初始化完毕
            EMChat.getInstance().setAppInited();
        }
    }

    /**
     * 专门用来处理环信推送or提示是否在前台还是后台，他娘的，环信的判断是否在前台或者后台有问题。
     *
     * @param context
     * @return
     */
    private static boolean isAppOnForeground(Context context) {
        String packgeName = context.getPackageName();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
        if (runningTaskInfos.size() > 0) {
            if (packgeName.equals(runningTaskInfos.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }


    private void startLocal() {
        // 初始化定位服务
        if (null == locationService) {
            locationService = LocationService.getInstance(this);
            locationService.registerListener(this);
        }
        if (appHandler == null)
            appHandler = new AppHandler();

        //开启定位
        locationService.start();
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        //无论如何关闭定位资源
        locationService.stop();

        //检查对象(后期如果需要强制地址对象也在此验证)
        if (null == location) {
            LogUtil.setLogWithE(this.getClass().getSimpleName(), "onReceiveLocation location is null");
            return;
        }

        //每次接收到定位信息之后就发送一个3分钟的延时消息
        appHandler.sendEmptyMessageDelayed(0x0001, 3 * 60 * 1000);

        int localType = location.getLocType();

        if (
            //gps定位结果
                localType == BDLocation.TypeGpsLocation
                        //网络定位结果(最精确的定位结果)
                        || localType == BDLocation.TypeNetWorkLocation
                        //离线定位(可用的定位返回方案)
                        || localType == BDLocation.TypeOffLineLocation) {

            //需要以下结果（经纬度以及street信息,但是这里只管存储）
            if (TextUtils.isEmpty(getPreferenceUtils().getLocal()))
                EventBus.getDefault().post(location);
            //更新内存中的gps
            bdLocation = location;
            //存储暂时的gps,发送消息
            getPreferenceUtils().setLocal(new Gson().toJson(bdLocation));
        } else {
            LogUtil.setLogWithE(this.getClass().getSimpleName(), "onReceiveLocation 定位错误");
        }
    }

    @Override  //IM的挤掉线监听，借鉴来当做自己的监听
    public void onDisconnected(final int error) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                switch (error) {
                    case EMError.CONNECTION_CONFLICT:
                        EventBus.getDefault().post(new LogoutEventBusEntity());
                        break;
                    default:
                        break;
                }
            }
        }).start();
    }

    @Override  //IM用户提供者
    public EaseUser getUser(String username) {
        int id = -1;
        try {
            id = Integer.parseInt(username);
        } catch (Exception e) {
            return null;
        }

        UserEntity entity = UserContorller.get(id);
        if (null == entity)
            return null;
        EaseUser user = new EaseUser(entity.nick);
        user.setAvatar(entity.avatar);
        return user;
    }

    /**
     * 系统定位Handler
     */
    private class AppHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0001:
                    startLocal();
                    break;
                default:
                    break;
            }
        }
    }

    public UserInfoEntity getUserInfo() {
        int id = PreferenceUtils.getInstance(globalContext).getId();
        if (id != 0) {
            selfUserInfo = UserInfoController.get(id);
        }
        return selfUserInfo;
    }

    public void setLoginStaus(int loginStaus) {
        if (getPreferenceUtils() != null) {
            getPreferenceUtils().setLoginStatus(loginStaus);
        }
    }

    public int getCurrentUid() {
        return PreferenceUtils.getInstance(globalContext).getId();
    }

    public PreferenceUtils getPreferenceUtils() {
        return preferenceUtils;
    }

    public ImageLoaderManager getImageLoaderManager() {
        return imageLoaderManager;
    }

    public void setUserInfo(UserInfoEntity userInfo) {
        this.selfUserInfo = userInfo;
    }

    /*身份信息 需求方 技能发布方*/
    public int getLoginStaus() {
        android.util.Log.d("MyApplication", "getLoginStaus " + getPreferenceUtils().getLoginStatus());
        return getPreferenceUtils().getLoginStatus();
    }

    public PhoneInfo getPhoneInfo() {
        android.util.Log.d("MyApplication", "phoneInfo:" + phoneInfo);
        return phoneInfo;
    }

    public void setPhoneInfo(PhoneInfo phoneInfo) {
        this.phoneInfo = phoneInfo;
    }

    public BDLocation getBdLocation() {
        android.util.Log.d("MyApplication", "bdLocation:" + bdLocation);
        return bdLocation;
    }

    public MessageCountEntity getMessageCountEntity() {
        android.util.Log.d("MyApplication", "messageCountEntity:" + messageCountEntity);
        return messageCountEntity;
    }

    public void setMessageCountEntity(MessageCountEntity messageCountEntity) {
        this.messageCountEntity = messageCountEntity;
    }

    @Override
    public void onConnected() {
    }

}