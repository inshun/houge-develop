package com.ysp.houge.widget.navigationbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.entity.bean.TabViewDescriptor;
import com.ysp.houge.utility.LogUtil;

/**
 * @author tyn
 * @version 1.0
 * @描述:切换栏子项
 * @Copyright Copyright (c) 2015
 * @Company 杭州网络科技有限公司.
 * @date 2015年8月5日下午5:51:29
 */
public class
HomeTabView extends RelativeLayout implements OnClickListener {
    private int currentTab;
    private TabView mTab1;
    private TabView mTab2;
    private TabView mTab3;
    private TabView mTab4;
    private ImageView mSummer;
    private TabViewDescriptor tabViewDescriptor1;
    private TabViewDescriptor tabViewDescriptor2;
    private TabViewDescriptor tabViewDescriptor3;
    private TabViewDescriptor tabViewDescriptor4;
    private SwicthViewListener swicthViewListener;
    private int loginStatus;

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     * @描述
     */
    @SuppressLint("NewApi")
    public HomeTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @描述
     */
    public HomeTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     * @描述
     */
    public HomeTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SwicthViewListener getSwicthViewListener() {
        return swicthViewListener;
    }

    /**
     * @param swicthViewListener the swicthViewListener to set
     */
    public void setSwicthViewListener(SwicthViewListener swicthViewListener) {
        this.swicthViewListener = swicthViewListener;
    }

    /**
     * 更换身份之后刷新
     */
    public void notifyView(int tab) {
        // 获取新的身份
        loginStatus = MyApplication.getInstance().getLoginStaus();
        switchTab(tab, loginStatus);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_tabs, this);
        mTab1 = (TabView) findViewById(R.id.mTab1);
        mTab2 = (TabView) findViewById(R.id.mTab2);
        mSummer = (ImageView) findViewById(R.id.iv_summon);
        mTab3 = (TabView) findViewById(R.id.mTab3);
        mTab4 = (TabView) findViewById(R.id.mTab4);

        mTab1.setOnClickListener(this);
        mTab2.setOnClickListener(this);
        mSummer.setOnClickListener(this);
        mTab3.setOnClickListener(this);
        mTab4.setOnClickListener(this);
        /*获取登陆身份*/
        loginStatus = MyApplication.getInstance().getLoginStaus();

        initData();
        switchTab(0, loginStatus);
    }

    public void initData() {
        tabViewDescriptor1 = new TabViewDescriptor("关注", R.drawable.tab_icon_recommend_default, 0, false);
        tabViewDescriptor2 = new TabViewDescriptor("附近", R.drawable.tab_icon_recommend_default, 0, false);
        tabViewDescriptor3 = new TabViewDescriptor("消息", R.drawable.tab_icon_recommend_default, MyApplication
                .getInstance().getPreferenceUtils().getChatUnreadCount(MyApplication.getInstance().getCurrentUid()),
                false);
        tabViewDescriptor4 = new TabViewDescriptor("我的", R.drawable.tab_icon_recommend_default, 0, false);

        mTab1.initData(tabViewDescriptor1);
        mTab2.initData(tabViewDescriptor2);
        mTab3.initData(tabViewDescriptor3);
        mTab4.initData(tabViewDescriptor4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mTab1:
                if (currentTab != 0 && swicthViewListener != null) {
                    boolean isChoose = swicthViewListener.clickOne();
                    if (isChoose) {
                        switchTab(0, loginStatus);
                    }
                }
                break;
            case R.id.mTab2:
                if (currentTab != 1 && swicthViewListener != null) {
                    boolean isChoose = swicthViewListener.clickTwo();
                    if (isChoose) {
                        switchTab(1, loginStatus);
                    }
                }
                break;
            case R.id.iv_summon:
                boolean choose = swicthViewListener.onPulish();
                if (choose) {
                    switchTab(2, loginStatus);
                }
                break;
            case R.id.mTab3:
                if (currentTab != 3 && swicthViewListener != null) {
                    boolean isChoose = swicthViewListener.clickThree();
                    if (isChoose) {
                        switchTab(3, loginStatus);
                    }
                }
                break;
            case R.id.mTab4:
                if (currentTab != 4 && swicthViewListener != null) {
                    boolean isChoose = swicthViewListener.clickFour();
                    if (isChoose) {
                        switchTab(4, loginStatus);
                    }
                }
            default:
                LogUtil.setLogWithE("HomeTabView", "Error View ID");
                break;
        }
    }

    /**
     * 消息数量更新
     */
    public void updateMessageCount(int count) {
        tabViewDescriptor3.unreadCount = count;
        mTab3.initData(tabViewDescriptor3);
    }


    /**
     * 我的数量更新
     */
    public void updateMeCount(int count) {
        tabViewDescriptor4.unreadCount = count;
        mTab4.initData(tabViewDescriptor4);
    }

    public void switchTab(int tab, int status) {
        currentTab = tab;
        switch (tab) {
            case 0:
                tabViewDescriptor2.resTabImgId = R.drawable.tab_icon_nearby_default;
                tabViewDescriptor3.resTabImgId = R.drawable.tab_icon_message_default;
                tabViewDescriptor4.resTabImgId = R.drawable.tab_icon_me_default;

                tabViewDescriptor2.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor3.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor4.tabNameColor = R.color.home_tab_text_color_selector;

                switch (status) {
                    case MyApplication.LOG_STATUS_BUYER:
                        tabViewDescriptor1.tabNameColor = R.color.color_app_theme_orange_bg;
                        tabViewDescriptor1.resTabImgId = R.drawable.tab_icon_recommend_selected_b;
                        break;
                    case MyApplication.LOG_STATUS_SELLER:
                        tabViewDescriptor1.tabNameColor = R.color.color_app_theme_blue_bg;
                        tabViewDescriptor1.resTabImgId = R.drawable.tab_icon_recommend_selected_s;
                        break;
                }
                break;
            case 1:
                tabViewDescriptor1.resTabImgId = R.drawable.tab_icon_recommend_default;
                tabViewDescriptor3.resTabImgId = R.drawable.tab_icon_message_default;
                tabViewDescriptor4.resTabImgId = R.drawable.tab_icon_me_default;

                tabViewDescriptor1.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor2.tabNameColor = R.color.color_actionbar_bg;
                tabViewDescriptor3.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor4.tabNameColor = R.color.home_tab_text_color_selector;

                switch (status) {
                    case MyApplication.LOG_STATUS_BUYER:
                        tabViewDescriptor2.tabNameColor = R.color.color_app_theme_orange_bg;
                        tabViewDescriptor2.resTabImgId = R.drawable.tab_icon_nearby_selected_b;
                        break;
                    case MyApplication.LOG_STATUS_SELLER:
                        tabViewDescriptor2.tabNameColor = R.color.color_app_theme_blue_bg;
                        tabViewDescriptor2.resTabImgId = R.drawable.tab_icon_nearby_selected_s;
                        break;
                }
                break;

            case 3:
                tabViewDescriptor1.resTabImgId = R.drawable.tab_icon_recommend_default;
                tabViewDescriptor2.resTabImgId = R.drawable.tab_icon_nearby_default;
                tabViewDescriptor4.resTabImgId = R.drawable.tab_icon_me_default;

                tabViewDescriptor1.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor2.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor3.tabNameColor = R.color.color_actionbar_bg;
                tabViewDescriptor4.tabNameColor = R.color.home_tab_text_color_selector;

                switch (status) {
                    case MyApplication.LOG_STATUS_BUYER:
                        tabViewDescriptor3.tabNameColor = R.color.color_app_theme_orange_bg;
                        tabViewDescriptor3.resTabImgId = R.drawable.tab_icon_message_selected_b;
                        break;
                    case MyApplication.LOG_STATUS_SELLER:
                        tabViewDescriptor3.tabNameColor = R.color.color_app_theme_blue_bg;
                        tabViewDescriptor3.resTabImgId = R.drawable.tab_icon_message_selected_s;
                        break;
                }
                break;
            case 4:
                tabViewDescriptor1.resTabImgId = R.drawable.tab_icon_recommend_default;
                tabViewDescriptor2.resTabImgId = R.drawable.tab_icon_nearby_default;
                tabViewDescriptor3.resTabImgId = R.drawable.tab_icon_message_default;

                tabViewDescriptor1.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor2.tabNameColor = R.color.home_tab_text_color_selector;
                tabViewDescriptor3.tabNameColor = R.color.home_tab_text_color_selector;

                switch (status) {
                    case MyApplication.LOG_STATUS_BUYER:
                        tabViewDescriptor4.resTabImgId = R.drawable.tab_icon_me_selected_b;
                        tabViewDescriptor4.tabNameColor = R.color.color_app_theme_orange_bg;
                        break;
                    case MyApplication.LOG_STATUS_SELLER:
                        tabViewDescriptor4.resTabImgId = R.drawable.tab_icon_me_selected_s;
                        tabViewDescriptor4.tabNameColor = R.color.color_app_theme_blue_bg;
                        break;
                }
                break;
            default:
                break;
        }
        mTab1.initData(tabViewDescriptor1);
        mTab2.initData(tabViewDescriptor2);
        mTab3.initData(tabViewDescriptor3);
        mTab4.initData(tabViewDescriptor4);
        initPulish(status);
    }

    private void initPulish(int status) {
        switch (status) {
            case MyApplication.LOG_STATUS_BUYER:
                mSummer.setBackgroundResource(R.drawable.tab_icon_pulish_b);
                break;
            case MyApplication.LOG_STATUS_SELLER:
                mSummer.setBackgroundResource(R.drawable.tab_icon_pulish_s);
                break;
        }
    }

    public interface SwicthViewListener {
        boolean clickOne();

        boolean clickTwo();

        boolean onPulish();

        boolean clickThree();

        boolean clickFour();
    }
}
