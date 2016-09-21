package com.ysp.houge.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.AppManager;
import com.ysp.houge.app.Constants;
import com.ysp.houge.app.CreateDialogUtil;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.dialog.PulishDialog;
import com.ysp.houge.dialog.YesDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.IIMModel;
import com.ysp.houge.model.entity.bean.AppInitEntity;
import com.ysp.houge.model.entity.bean.IMEntity;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.bean.WebEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.entity.eventbus.ChangeLoginStatusEventBusEntity;
import com.ysp.houge.model.entity.eventbus.IMLoginEventBusEntity;
import com.ysp.houge.model.entity.eventbus.LoginEventBusEntity;
import com.ysp.houge.model.entity.eventbus.LogoutEventBusEntity;
import com.ysp.houge.model.entity.eventbus.PushInfoEventBusEntity;
import com.ysp.houge.model.impl.IMModelImpl;
import com.ysp.houge.model.impl.PushInfoModelImpl;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.receiver.PushMessageManager;
import com.ysp.houge.ui.base.BaseFragmentActivity;
import com.ysp.houge.ui.details.NeedDetailsActivity;
import com.ysp.houge.ui.details.SkillDetailsActivity;
import com.ysp.houge.ui.login.LoginActivity;
import com.ysp.houge.ui.me.WebViewActivity;
import com.ysp.houge.ui.me.balance.BalanceActivity;
import com.ysp.houge.ui.order.OrderDetailsActivity;
import com.ysp.houge.utility.ChangeUtils;
import com.ysp.houge.utility.SizeUtils;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;
import com.ysp.houge.widget.navigationbar.HomeTabView;
import com.ysp.houge.widget.navigationbar.HomeTabView.SwicthViewListener;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 描述： App主页面(存放导航栏的页面)
 *
 * @ClassName: HomeActivity
 * @author: hx
 * @date: 2015年12月3日 下午5:23:50
 * <p/>
 * 版本: 1.0
 */
public class HomeActivity extends BaseFragmentActivity implements SwicthViewListener {
    private static final String TAG = "HomeActivity";

    private RecommendFragment recommendFragment; // 关注
    private NearbyFragment nearbyListFragment; // 附近
    private MessageFragment messageFragment; // 消息
    private MeFragment meFragment; // 我的

    public int pageSize;//当前选中的下标
    private HomeTabView mHomeTabView;

    /**
     * 所有显示的Fragment的集合
     */
    private Fragment[] fragments;
    /**
     * 退出登录的弹窗
     */
    private YesDialog yesDialog;

    // 当前登录身份
    private int loginStatus;
    //IM的Molde层
    private IIMModel iimModel;
    //主页Hnadler
    private HomeHandler handler;

    /*版本升级*/
//	private boolean vertion = false;
    private String upDataInfo = null;

    private static boolean isPushSkillOrNeed = true;


    public static void jumpIn(Context context, Bundle bundle) {
        ((Activity) context).finish();
        Intent intent = new Intent(context, HomeActivity.class);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void initActionbar() {
    }

    @Override
    protected void initializeViews(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        mHomeTabView = (HomeTabView) findViewById(R.id.mHomeTabView);

        /**第一次进来发布技能或者需求*/
        pushSkillOrNeed();
        /**获取版本信息*/
        getCrrentVersion();

    }

    /*版本辨别*/
    private void getCrrentVersion() {

        if (null != this.getIntent().getExtras()) {

            Bundle b = this.getIntent().getExtras();
            Boolean isShowDialog = b.getBoolean(Constants.KEY_IS_SHOW_UP_DATA_DIALOG);
            int mustUpData = b.getInt(Constants.KEY_MUST_UP_DATA);
            upDataInfo = b.getString("updatainfo");
            if (isShowDialog) {
                showUpDataDialog(mustUpData);
            }
        }

    }

    /*升级提示*/
    private void showUpDataDialog(int mustUpData) {
        if (mustUpData == 0) {
            new AlertDialog.Builder(this)
                    .setTitle("升级提醒")
                    .setMessage(upDataInfo)
                    .setCancelable(false)// 非点击按钮不可取消dialog
                    .setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        /*打开手机自带应用商店*/
                            String str = "market://details?id=" + getPackageName();
                            Intent localIntent = new Intent("android.intent.action.VIEW");
                            localIntent.setData(Uri.parse(str));
                            startActivity(localIntent);
                        }
                    })
                    .setNegativeButton("稍后升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "稍后升级");
                        }
                    })
                    .create().show();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("升级提醒")
                    .setMessage(upDataInfo)
                    .setCancelable(false) // 非点击按钮不可取消dialog
                    .setPositiveButton("立即升级", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        /*打开手机自带应用商店*/
                            String str = "market://details?id=" + getPackageName();
                            Intent localIntent = new Intent("android.intent.action.VIEW");
                            localIntent.setData(Uri.parse(str));
                            startActivity(localIntent);
                        }
                    })
                    .create().show();
        }


    }

    /*发布需求*/
    public void pushSkillOrNeed() {
        if (!isPushSkillOrNeed) {
            PulishDialog dialog = new PulishDialog(HomeActivity.this, HomeActivity.this);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = SizeUtils.getScreenWidth(HomeActivity.this);
            dialog.getWindow().setAttributes(lp);
            dialog.show();
            isPushSkillOrNeed = true;
        }

    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            //从缓存中读取每一个Fragment
            recommendFragment = (RecommendFragment) getFragmentFromCacheManager(0);
            nearbyListFragment = (NearbyFragment) getFragmentFromCacheManager(1);
            messageFragment = (MessageFragment) getFragmentFromCacheManager(2);
            meFragment = (MeFragment) getFragmentFromCacheManager(3);

            //判断每个Frament
            //      如果为空则新建
            //      如果不为空，则隐藏
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (recommendFragment == null) {
                recommendFragment = new RecommendFragment();
                transaction.add(R.id.fragmentContainer, recommendFragment, TAG + 0)
                        .show(recommendFragment);
            } else {
                transaction.show(messageFragment);
            }
            if (nearbyListFragment == null) {
                nearbyListFragment = new NearbyFragment();
            } else {
                transaction.hide(nearbyListFragment);
            }
            if (messageFragment == null) {
                messageFragment = new MessageFragment();
            } else {
                transaction.hide(messageFragment);
            }
            if (meFragment == null) {
                meFragment = new com.ysp.houge.ui.MeFragment();
            } else {
                transaction.hide(meFragment);
            }

            transaction.commitAllowingStateLoss();
        } else {
            //初次进来，所有的Fragment实例都New出来
            recommendFragment = new RecommendFragment();
            nearbyListFragment = new NearbyFragment();
            messageFragment = new MessageFragment();
            meFragment = new MeFragment();

            // 显示第一个fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentContainer, recommendFragment, TAG + 0)
                    .commitAllowingStateLoss();
        }

        //设置导航栏按钮的监听事件
        mHomeTabView.setSwicthViewListener(this);
        //获取当前登录的身份
        loginStatus = MyApplication.getInstance().getLoginStaus();
        //推送绑定用户ID
        if (null != MyApplication.getInstance().getUserInfo())
            PushMessageManager.bindAlias(this, MyApplication.getInstance().getCurrentUid());
        //实例化Fragment数组对象
        fragments = new Fragment[]{recommendFragment, nearbyListFragment, messageFragment, meFragment};

        //实例化主页Handler
        handler = new HomeHandler();
        iimModel = new IMModelImpl();

        appInit();//获取app初始化的参数
        loadEMChat();//IM登录
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (null != intent && null != intent.getExtras() && intent.getExtras().containsKey("TAG")) {
            loadEMChat();//登录IM
            getAllCount();//消息计数刷新
            //发送登录消息，让两个页面的整体数据刷新
            EventBus.getDefault().post(new LoginEventBusEntity());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;
//		EventBus.getDefault().unregister(this);
    }

    /**
     * 选择页面
     */
    private void switchPage(int switchPage) {
        if (fragments[pageSize] != null) {
            if (switchPage != pageSize) {
                FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                trx.hide(fragments[pageSize]);
                if (!fragments[switchPage].isAdded()) {
                    trx.add(R.id.fragmentContainer, fragments[switchPage], TAG + switchPage);
                }
                trx.show(fragments[switchPage]).commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    /**
     * 从缓存中读取Fragment
     */
    private Fragment getFragmentFromCacheManager(int position) {
        if (getSupportFragmentManager().findFragmentByTag(TAG + position) != null) {
            return getSupportFragmentManager().findFragmentByTag(TAG + position);

        }
        return null;
    }

    /**
     * 登陆信息失效 token失效
     */
    public void onEventMainThread(LogoutEventBusEntity logoutEventBusEntity) {
        createLoginAtOtherDialog();
    }


    /**
     * 身份改变后刷新导航栏
     */
    public void onEventMainThread(ChangeLoginStatusEventBusEntity entity) {
        // 判断一下，如果状态重复，则不做处理
        if (loginStatus == MyApplication.getInstance().getLoginStaus()) {
            return;
        }
        loginStatus = MyApplication.getInstance().getLoginStaus();
        mHomeTabView.notifyView(entity.getTab());
    }


    /**
     * 消息数量的信息
     */
    public void onEventMainThread(MessageCountEntity messageCountEntity) {
        MessageCountEntity mMessageCountEntity = MyApplication.getInstance().getMessageCountEntity();
        int messageCount = 0;
        int mineCount = 0;
        int count = ChangeUtils.toInt(mMessageCountEntity.count, 0);
        int buy = ChangeUtils.toInt(mMessageCountEntity.buy, 0);
        int sale = ChangeUtils.toInt(mMessageCountEntity.sale, 0);

        messageCount = count + EMChatManager.getInstance().getUnreadMsgsCount();
        mineCount = buy + sale;

        if (messageCount > -1) {
            mHomeTabView.updateMessageCount(messageCount);
        }

        if (mineCount > -1) {
            mHomeTabView.updateMeCount(mineCount);
        }
        refreshMsgList();
    }

    //完全是为了优化消息列表刷新。 写的这么烂，以后优化
    private int unraedmsg;

    public void refreshMsgList() {
        MessageCountEntity msgcountentity = new MessageCountEntity();
        int countt = ChangeUtils.toInt(msgcountentity.count, 0);
        int msgcount = countt + EMChatManager.getInstance().getUnreadMsgsCount();
        if (unraedmsg != msgcount) {
            unraedmsg = msgcount;
            messageFragment.refreshMessage();
        }


    }

    /**
     * 推送,页面跳转
     */
    public void onEventMainThread(PushInfoEventBusEntity busEntity) throws UnsupportedEncodingException {
        if (null != busEntity) {
            switch (busEntity.page_id) {
                case PushInfoEventBusEntity.PAGE_SKILL:
                    if (busEntity.id != -1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(SkillDetailsActivity.KEY, busEntity.id);
                        SkillDetailsActivity.jumpIn(this, bundle);
                    }
                    break;

                case PushInfoEventBusEntity.PAGE_NEED:
                    if (busEntity.id != -1) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(NeedDetailsActivity.KEY, busEntity.id);
                        NeedDetailsActivity.jumpIn(this, bundle);
                    }
                    break;

                case PushInfoEventBusEntity.PAGE_ORDER:
                    if (!TextUtils.isEmpty(busEntity.url)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(OrderDetailsActivity.KEY, busEntity.url);
                        //bundle.putString("TAG","SELL");
                        //bundle.putBoolean("isFuncation", false);
                        OrderDetailsActivity.jumpIn(this, bundle);
                    }
                    break;

                case PushInfoEventBusEntity.PAGE_WEB:
                    if (!TextUtils.isEmpty(busEntity.url)) {
                        Bundle bundle = new Bundle();
                        WebEntity entity = new WebEntity(URLDecoder.decode(busEntity.url, "utf-8"), "详情");
                        bundle.putSerializable(WebViewActivity.KEY, entity);
                        WebViewActivity.jumpIn(this, bundle);
                    }
                    break;

                case PushInfoEventBusEntity.PAGE_MESSAGE:
//				HomeActivity.jumpIn(this, null);
                    clickThree();
                    break;
                case PushInfoEventBusEntity.PAGE_USER_CHANGELOG:
                    BalanceActivity.jumpIn(this);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 创建提示账号在别处登录的对话框
     */
    private void createLoginAtOtherDialog() {
        YesOrNoDialogEntity yesOrNoDialogEntity = new YesOrNoDialogEntity("对不起，您的账号长时间未登录或者在别处登录", "", "", "确定");
        if (yesDialog == null) {
            yesDialog = new YesDialog(HomeActivity.this, yesOrNoDialogEntity, new OnYesOrNoDialogClickListener() {

                @Override
                public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
                    switch (yesOrNoType) {
                        case BtnOk:
                            // 解绑推送
                            PushMessageManager.unBindAlias(HomeActivity.this, MyApplication.getInstance().getCurrentUid());
                            MyApplication.getInstance().getPreferenceUtils().cleanLoginPreference();
                            MyApplication.getInstance().setUserInfo(null);
                            AppManager.getAppManager().finishAllActivity();
                            // 推送信息收集接口状态下线
                            requestPushInfo();

                            if (EMChat.getInstance().isLoggedIn())// 下线聊天
                                EMChatManager.getInstance().logout();// 此方法为同步方法
                            mHomeTabView.updateMessageCount(0);
                            mHomeTabView.updateMeCount(0);
                            break;
                        default:
                            break;
                    }
                }
            });
            yesDialog.setCanceledOnTouchOutside(false);
            yesDialog.setOnKeyListener(CreateDialogUtil.keylistener);
        }
        if (!yesDialog.isShowing()) {
            yesDialog.show();
        }
    }


    /**
     * 请求推送信息收集接口
     */
    private void requestPushInfo() {
        new PushInfoModelImpl().setPushInfo(1, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                LoginActivity.jumpIn(HomeActivity.this);
                close();
            }

            @Override
            public void onError(int errorCode, String message) {
                LoginActivity.jumpIn(HomeActivity.this);
                close();
            }
        });
    }

    public void createLoginDialog() {
        YesOrNoDialogEntity yesOrNoDialogEntity = new YesOrNoDialogEntity("您还未登陆,请先登录", "", getString(R.string.cancel),
                getString(R.string.sure));
        if (yesDialog == null) {
            yesDialog = new YesDialog(HomeActivity.this, yesOrNoDialogEntity, new OnYesOrNoDialogClickListener() {

                @Override
                public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
                    switch (yesOrNoType) {
                        case BtnOk:
                            LoginActivity.jumpIn(HomeActivity.this);
                            break;
                        default:
                            yesDialog.cancel();
                            break;
                    }
                }
            });
        }
        if (!yesDialog.isShowing()) {
            yesDialog.show();
        }
    }

    /**
     * app初始化接口
     */
    private void appInit() {
        new SettingModelImpl().appInit(new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof AppInitEntity) {
                    AppInitEntity initEntity = (AppInitEntity) data;
                    MyApplication.getInstance().getPreferenceUtils().setAppInitInfo(new Gson().toJson(initEntity));
                    if (null != MyApplication.getInstance().getUserInfo())
                        getAllCount();
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                if (errorCode == ResponseCode.TIP_ERROR && TextUtils.equals(message, "sign_check_fail"))
                    return;
                // 延时调用初始化接口，直至成功
                if (null != handler) {
                    handler.sendEmptyMessageDelayed(0x0001, 3 * 1000);
                }
            }
        });
    }

    /**
     * 获取消息数量
     */
    private void getAllCount() {
        String json = MyApplication.getInstance().getPreferenceUtils().getAppInitInfo();
        if (json == "") {
            appInit();
        } else {
            int m = new Gson().fromJson(json, AppInitEntity.class).notice_new_time;
            if (null != handler) {
                handler.sendEmptyMessageDelayed(0x0003, m * 60 * 1000);
            }
            new SettingModelImpl().getAllCount();
        }
    }

    /**
     * 环信登录
     */
    private void loadEMChat() {
        // TODO 登录环信
        UserInfoEntity infoEntity = MyApplication.getInstance().getUserInfo();
        if (null != infoEntity && !EMChat.getInstance().isLoggedIn()) {
            iimModel.login(infoEntity.im_id, infoEntity.im_pass, new OnNetResponseCallback() {

                @Override
                public void onSuccess(Object data) {
                    // 成功了,发送消息给列表或者聊天页面
                    EventBus.getDefault().post(new IMLoginEventBusEntity());
                }

                @Override
                public void onError(int errorCode, String message) {
                    switch (errorCode) {

                        // 网络原因的登录失败递归调用
                        case EMCallBack.ERROR_EXCEPTION_CONNECT_TIMER_OUT:// 超时
                        case EMCallBack.ERROR_EXCEPTION_NONETWORK_ERROR:// 网络不可用
                        case EMCallBack.ERROR_EXCEPTION_UNABLE_CONNECT_TO_SERVER:// 无法连接到服务器
//                        showToast("网络不可用，请检查网络连接");
                            // 延时调用
                            if (null != handler) {
                                handler.sendEmptyMessageDelayed(0x0002, 2 * 1000);
                            }
                            break;

                        // 账户密码问题先去注册
                        case EMCallBack.ERROR_EXCEPTION_INVALID_PASSWORD_USERNAME:
                            registEMChat();
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    /**
     * 环信注册
     */
    private void registEMChat() {
        iimModel.register(new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (null != handler && null != data && data instanceof IMEntity && !TextUtils.isEmpty(((IMEntity) data).password)) {
                    UserInfoEntity infoEntity = MyApplication.getInstance().getUserInfo();
                    //替换用户IM密码
                    infoEntity.im_pass = ((IMEntity) data).password;
                    //更新内存用户信息
                    MyApplication.getInstance().setUserInfo(infoEntity);
                    //更新数据库用户信息
                    UserInfoController.createOrUpdate(infoEntity);
                    // 延时调用登录
                    handler.sendEmptyMessageDelayed(0x0002, 2 * 1000);
                } else {
                    //重置密码
                    reSetEMChatPass();
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                //重置密码
                reSetEMChatPass();
            }
        });
    }

    /**
     * 环信重置密码
     */
    private void reSetEMChatPass() {
        iimModel.reSetPassword(new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (null != handler && null != data && data instanceof IMEntity && !TextUtils.isEmpty(((IMEntity) data).password)) {
                    UserInfoEntity infoEntity = MyApplication.getInstance().getUserInfo();
                    //替换用户IM密码
                    infoEntity.im_pass = ((IMEntity) data).password;
                    //更新内存用户信息
                    MyApplication.getInstance().setUserInfo(infoEntity);
                    //更新数据库用户信息
                    UserInfoController.createOrUpdate(infoEntity);
                    // 延时调用
                    handler.sendEmptyMessageDelayed(0x0002, 2 * 1000);
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                showToast("网络错误");
            }
        });
    }

    @Override
    public boolean onPulish() {
        // 登陆跳转发布，不登陆跳转登陆
        if (MyApplication.getInstance().getUserInfo() == null) {
            createLoginDialog();
            return false;
        } else {
            PulishDialog dialog = new PulishDialog(HomeActivity.this, HomeActivity.this);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = SizeUtils.getScreenWidth(HomeActivity.this);
            dialog.getWindow().setAttributes(lp);
            dialog.show();
            return true;
        }
    }

    @Override
    public boolean clickOne() {
        switchPage(0);
        pageSize = 0;
        mHomeTabView.switchTab(0, loginStatus);
        return true;
    }

    @Override
    public boolean clickTwo() {
        switchPage(1);
        pageSize = 1;
        mHomeTabView.switchTab(1, loginStatus);
        return true;
    }

    @Override
    public boolean clickThree() {
        if (MyApplication.getInstance().getUserInfo() == null) {
            createLoginDialog();
            return false;
        } else {
            switchPage(2);
            pageSize = 2;
            mHomeTabView.switchTab(3, loginStatus);
            return true;
        }
    }

    @Override
    public boolean clickFour() {
        if (MyApplication.getInstance().getUserInfo() == null) {
            createLoginDialog();
            return false;
        } else {
            switchPage(3);
            pageSize = 3;
            mHomeTabView.switchTab(4, loginStatus);
            return true;
        }
    }

    /**
     * 主页Handler
     */
    private class HomeHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0001:
                    appInit();
                    break;
                case 0x0002:
                    loadEMChat();
                    break;
                case 0x0003:
                    getAllCount();
                    break;
                default:
                    break;
            }
        }
    }
}
