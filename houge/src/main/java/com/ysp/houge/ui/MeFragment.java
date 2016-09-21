package com.ysp.houge.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dialog.YesOrNoDialog;
import com.ysp.houge.dialog.YesOrNoDialog.OnYesOrNoDialogClickListener;
import com.ysp.houge.dialog.YesOrNoDialog.YesOrNoType;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.bean.YesOrNoDialogEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.presenter.IMePresenter;
import com.ysp.houge.presenter.impl.MePresenter;
import com.ysp.houge.ui.base.BaseFragment;
import com.ysp.houge.ui.iview.IMePageView;
import com.ysp.houge.ui.login.LoginActivity;
import com.ysp.houge.ui.map.ResidentAddressActivity;
import com.ysp.houge.ui.me.AdvanceSalaryActivity;
import com.ysp.houge.ui.me.CommentListActivity;
import com.ysp.houge.ui.me.LoveMeListActivity;
import com.ysp.houge.ui.me.MakeMoneyByShareActivity;
import com.ysp.houge.ui.me.MyBuyActivity;
import com.ysp.houge.ui.me.MyNeedActivity;
import com.ysp.houge.ui.me.MySellActivity;
import com.ysp.houge.ui.me.MySkillActivity;
import com.ysp.houge.ui.me.TimeManagerActivity;
import com.ysp.houge.ui.me.address.AddressManagerActivity;
import com.ysp.houge.ui.me.balance.BalanceActivity;
import com.ysp.houge.ui.me.footprint.MyFootprintActivity;
import com.ysp.houge.ui.me.meinfo.MeInfoActivity;
import com.ysp.houge.ui.me.safeguard.ApplyServiceSafeguardActivity;
import com.ysp.houge.ui.me.safeguard.UnfreezeCashDeposit;
import com.ysp.houge.ui.me.setting.SettingActivity;
import com.ysp.houge.ui.photoview.PhotoViewActivity;
import com.ysp.houge.utility.ChangeUtils;
import com.ysp.houge.utility.imageloader.ImageLoaderManager.LoadImageType;
import com.ysp.houge.widget.MyActionBar;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hx
 * @version 1.0
 * @描述: 我的 买家页面View层
 * @Copyright Copyright (c) 2015
 * @Company 杭州新鲜人网络科技有限公司.
 * @date 2015年10月12日下午1:34:02
 */
public class MeFragment extends BaseFragment implements IMePageView, OnClickListener {
    private MyActionBar actionBar;

    private ImageView mAvater;// 头像
    private TextView mName;// 昵称
    private TextView mServiceSafeguard;// 服务保障
    private TextView mAuth;// 认证
    private TextView mRate; // 评分
    private RelativeLayout mUserInfo;// 用户基本信息
    private LinearLayout mLineBalance; // 余额
    private TextView mBalance; // 余额文本
    private TextView mAllComment; // 所有评论
    private TextView mLoveMe; // 喜欢我的

    private TextView mBuyCount;// 我的购买消息
    private TextView mSellCount;// 我的卖出消息
    private TextView mSafeguard;// 我的保证金额

    private List<String> list;
    private int loginStatus;

    private IMePresenter iMePagePresenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initActionbar(View view) {
        actionBar = new MyActionBar(getActivity());
        actionBar.setTitle(R.string.mine);
        actionBar.setLeftEnable(false);
        actionBar.setRightText(R.string.system);
        RelativeLayout actionbar = (RelativeLayout) view.findViewById(R.id.rl_actionbar);
        actionbar.addView(actionBar);
        actionBar.setLeftClickListenner(this);
        actionBar.setRightClickListenner(this);
    }

    @Override
    protected void initializeViews(View view, Bundle savedInstanceState) {
        mAvater = (ImageView) view.findViewById(R.id.iv_avatar);
        mName = (TextView) view.findViewById(R.id.tv_nickname);
        mServiceSafeguard = (TextView) view.findViewById(R.id.tv_service_safeguard);
        mAuth = (TextView) view.findViewById(R.id.tv_student_auth);
        mRate = (TextView) view.findViewById(R.id.tv_rate);
        mUserInfo = (RelativeLayout) view.findViewById(R.id.rela_user_info);
        mLineBalance = (LinearLayout) view.findViewById(R.id.line_user_balance);
        mBalance = (TextView) view.findViewById(R.id.tv_balance);
        mLoveMe = (TextView) view.findViewById(R.id.tv_love_me);
        mAllComment = (TextView) view.findViewById(R.id.tv_all_comment);
        mUserInfo.setOnClickListener(this);
        mLineBalance.setOnClickListener(this);
        mLoveMe.setOnClickListener(this);
        mAllComment.setOnClickListener(this);

        mBuyCount = (TextView) view.findViewById(R.id.tv_me_buy_count);
        mSellCount = (TextView) view.findViewById(R.id.tv_me_sell_count);
        mSafeguard = (TextView) view.findViewById(R.id.tv_safeguard_money);

        mAvater.setOnClickListener(this);
        view.findViewById(R.id.rela_my_footprint).setOnClickListener(this);
        view.findViewById(R.id.rela_make_money_by_share).setOnClickListener(this);
        view.findViewById(R.id.rela_me_buy).setOnClickListener(this);
        view.findViewById(R.id.rela_my_need).setOnClickListener(this);
        view.findViewById(R.id.rela_add_manager).setOnClickListener(this);
        view.findViewById(R.id.rela_me_seller).setOnClickListener(this);
        view.findViewById(R.id.rela_me_skill).setOnClickListener(this);
        view.findViewById(R.id.rela_time_manager).setOnClickListener(this);
        view.findViewById(R.id.rela_address).setOnClickListener(this);
        view.findViewById(R.id.rela_advance_salary).setOnClickListener(this);
        view.findViewById(R.id.rela_service_safeguard).setOnClickListener(this);
    }

    @Override
    protected void initializeData(Bundle savedInstanceState) {
        if (iMePagePresenter == null) {
            iMePagePresenter = new MePresenter(this);
        }
        loginStatus = MyApplication.getInstance().getLoginStaus();
        // 更加身份初始化标题栏以及下方布局
        changeLoginStatus();
        iMePagePresenter.getUserInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // 重新显示的时候判断登陆身份是否一样，不一样就加载新数据
        if (hidden)
            return;
        if (null == iMePagePresenter)
            iMePagePresenter = new MePresenter(this);
        iMePagePresenter.getUserInfo();

        if (loginStatus != MyApplication.getInstance().getLoginStaus()) {
            // 获取新的登陆身份
            loginStatus = MyApplication.getInstance().getLoginStaus();
            changeLoginStatus();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setMessageCount(null);
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void setMessageCount(MessageCountEntity entity) {
        MessageCountEntity messageCountEntity = MyApplication.getInstance().getMessageCountEntity();
        if (null == messageCountEntity)
            return;
        int buyCount = ChangeUtils.toInt(messageCountEntity.buy, 0);
        int saleCount = ChangeUtils.toInt(messageCountEntity.sale, 0);
        if (buyCount > 0) {
            if (buyCount > 99) {
                mBuyCount.setText("99+");
            } else {
                mBuyCount.setText(String.valueOf(buyCount));
            }
            mBuyCount.setVisibility(View.VISIBLE);
        } else {
            mBuyCount.setText("0");
            mBuyCount.setVisibility(View.GONE);
        }

        if (saleCount > 0) {
            if (buyCount > 99) {
                mSellCount.setText("99+");
            } else {
                mSellCount.setText(String.valueOf(saleCount));
            }
            mSellCount.setVisibility(View.VISIBLE);
        } else {
            mSellCount.setText("0");
            mSellCount.setVisibility(View.GONE);
        }
    }

    @Override
    public void showChangeLoginStatusDialog() {
        // TODO 更换身份弹窗
        loginStatus = MyApplication.getInstance().getLoginStaus();
        StringBuilder sb = new StringBuilder("您当前身份为");
        switch (loginStatus) {
            case MyApplication.LOG_STATUS_BUYER:
                sb.append("买家，是否切换成卖家?");
                break;
            case MyApplication.LOG_STATUS_SELLER:
                sb.append("卖家，是否切换成买家?");
                break;
        }

        YesOrNoDialogEntity dialogEntity = new YesOrNoDialogEntity("是否切换身份?", sb.toString(), "取消", "确定");
        YesOrNoDialog dialog = new YesOrNoDialog(getActivity(), dialogEntity, new OnYesOrNoDialogClickListener() {

            @Override
            public void onYesOrNoDialogClick(YesOrNoType yesOrNoType) {
                if (yesOrNoType == YesOrNoType.BtnOk) {
                    iMePagePresenter.changeLoginStutus();
                }
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void changeLoginStatus() {
        loginStatus = MyApplication.getInstance().getLoginStaus();
        switch (loginStatus) {
            case MyApplication.LOG_STATUS_BUYER:
                initBuy();
                break;
            case MyApplication.LOG_STATUS_SELLER:
                initSell();
                break;
        }
        actionBar.initDefult();
//        EventBus.getDefault().post(new ChangeLoginStatusEventBusEntity(4));
    }

    private void initBuy() {
        // TODO 初始化卖家状态
        // 标题栏部分
        mBalance.setTextColor(getResources().getColor(R.color.color_app_theme_orange_bg));
    }

    private void initSell() {
        // TODO 初始化买家状态
        mBalance.setTextColor(getResources().getColor(R.color.color_app_theme_blue_bg));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void showUserInfo(UserInfoEntity info) {
        if (null == info)
            return;
        // 处理头像背景边框
        if (info.sex == 1) {
            mAvater.setBackground(getResources().getDrawable(R.drawable.shapa_sex_mal));
        } else if (info.sex == 2) {
            mAvater.setBackground(getResources().getDrawable(R.drawable.shapa_sex_femal));
        } else {
            mAvater.setBackground(getResources().getDrawable(R.drawable.shapa_sex_def));
        }

        // 服务保障
        if (!TextUtils.isEmpty(info.serviceSafeguardg)) {
            double serviceSafeguardg = -1;
            try {
                serviceSafeguardg = Double.parseDouble(info.serviceSafeguardg);
            } catch (Exception e) {
            }
            if (serviceSafeguardg > 0) {
                mServiceSafeguard.setVisibility(View.VISIBLE);
                // 这里需要设置冻结金额
                mSafeguard.setText(info.serviceSafeguardg + "元");
                //如果审核中则需要显示审核中
                if (!TextUtils.isEmpty(info.protect_status)) {
                    mSafeguard.setText("解冻审核中");
                }
                mSafeguard.setVisibility(View.VISIBLE);
            } else {
                mServiceSafeguard.setVisibility(View.GONE);
                mSafeguard.setVisibility(View.GONE);
            }
        }

        // 认证
        if (TextUtils.isEmpty(info.verfied)) {
            mAuth.setVisibility(View.GONE);
        } else {
            mAuth.setVisibility(View.VISIBLE);
            if (TextUtils.equals("1", info.verfied)) {
                mAuth.setText("学生认证");
            } else if (TextUtils.equals("2", info.verfied)) {
                mAuth.setText("个人认证");
            } else if (TextUtils.equals("3", info.verfied)) {
                mAuth.setText("企业认证");
            } else {
                mAuth.setVisibility(View.GONE);
            }
        }

        if (null == list) {
            list = new ArrayList<String>();
        }
        list.clear();
        list.add(info.avatar);

        // 加载头像
        MyApplication.getInstance().getImageLoaderManager().loadNormalImage(mAvater, info.avatar,
                LoadImageType.RoundAvatar);
        // 昵称
        mName.setText(info.nick);
        // 星级
        mRate.setText(String.valueOf(info.star));
        // 这里显示可用余额
        mBalance.setText(String.valueOf(info.balance));
        // 评论数量
        mAllComment.setText(String.valueOf(info.comment_count));
        // 喜欢数量
        mLoveMe.setText(String.valueOf(info.view_count));
    }

    @Override
    public void jumpToSystem() {
        SettingActivity.jumpIn(getActivity(), null);
    }

    @Override
    public void jumpToAccountInfo() {
        MeInfoActivity.jumpIn(getActivity(), null);
    }

    @Override
    public void jumpToBalance() {
        BalanceActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToLoveMe() {
        LoveMeListActivity.jumpIn(getActivity(), null);
    }

    @Override
    public void jumpToAllComment() {
        CommentListActivity.jumpIn(getActivity(), null);
    }

    @Override
    public void jumpToMyFootprint() {
        MyFootprintActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToAddressManager() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddressManagerActivity.ENTER_PAGE_KEY, AddressManagerActivity.ENTER_PAGE_ME);
        AddressManagerActivity.jumpIn(getActivity(), bundle);
    }

    @Override
    public void jumpToMeSkill() {
        MySkillActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToTimeManager() {
        TimeManagerActivity.jumpIn(getContext(), null);
    }

    @Override
    public void jumpToResidentAddress() {
        Bundle bundle = new Bundle();
        bundle.putInt(ResidentAddressActivity.KEY, ResidentAddressActivity.TYPE_SAVE_RESULT);
        ResidentAddressActivity.jumpIn(getActivity(), bundle);
    }

    @Override
    public void jumpToMakeMoneyByShare() {
        if (null == MyApplication.getInstance().getUserInfo()) {
            LoginActivity.jumpIn(getActivity());
            return;
        }
        MakeMoneyByShareActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToMeBuy() {
        MyBuyActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToMeNeed() {
        MyNeedActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToMeSeller() {
        MySellActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToAdvanceSalary() {
        AdvanceSalaryActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToApplyServiceSafeguard() {
        ApplyServiceSafeguardActivity.jumpIn(getActivity());
    }

    @Override
    public void jumpToUnfreezeServiceSafeguard() {
        UnfreezeCashDeposit.jumpIn(getActivity());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 头像大图查看
            case R.id.iv_avatar:
                Bundle bundle = new Bundle();
                bundle.putSerializable(PhotoViewActivity.KEY, new Gson().toJson(list));
                PhotoViewActivity.jumpIn(getActivity(), bundle);
                break;

            // 系统
            case R.id.menu_head_right:
                iMePagePresenter.onSystemClick();
                break;

            // 个人信息
            case R.id.rela_user_info:
                iMePagePresenter.onUserInfoCliclk();
                break;

            // 账户余额
            case R.id.line_user_balance:
                iMePagePresenter.onBalanceClick();
                break;

            // 喜欢我的
            case R.id.tv_love_me:
                iMePagePresenter.onLoveMeClick();
                break;

            // 所有留言
            case R.id.tv_all_comment:
                iMePagePresenter.onAllCommentClick();
                break;

            // 我的足迹
            case R.id.rela_my_footprint:
                iMePagePresenter.onMyFootprintClick();
                break;

            // 分享赚钱
            case R.id.rela_make_money_by_share:
                iMePagePresenter.onMakeMoneyByShareClick();
                break;

            // 我购买的
            case R.id.rela_me_buy:
                iMePagePresenter.onMeBuyerClick();
                break;

            // 我的需求
            case R.id.rela_my_need:
                iMePagePresenter.onMyDemandClick();
                break;

            // 地址管理
            case R.id.rela_add_manager:
                iMePagePresenter.onAddressManagerClick();
                break;

            // 我卖出的
            case R.id.rela_me_seller:
                iMePagePresenter.onMeSellerClick();
                break;

            // 我的技能
            case R.id.rela_me_skill:
                iMePagePresenter.onMySkillClick();
                break;

            // 时间管理
            case R.id.rela_time_manager:
                iMePagePresenter.onTimeManagerClick();
                break;

            // 常驻地址
            case R.id.rela_address:
                iMePagePresenter.onResidentAddressClick();
                break;

            // 预支工资
            case R.id.rela_advance_salary:
                iMePagePresenter.onAdvanceOfWagesClick();
                break;

            // 服务保障
            case R.id.rela_service_safeguard:
                iMePagePresenter.onServiceSafeguardClick();
                break;
        }
    }
}
