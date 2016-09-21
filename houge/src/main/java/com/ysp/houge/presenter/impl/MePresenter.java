package com.ysp.houge.presenter.impl;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.dbcontroller.UserContorller;
import com.ysp.houge.dbcontroller.UserInfoController;
import com.ysp.houge.model.IUserInfoModel;
import com.ysp.houge.model.entity.bean.MessageCountEntity;
import com.ysp.houge.model.entity.db.UserEntity;
import com.ysp.houge.model.entity.db.UserInfoEntity;
import com.ysp.houge.model.impl.UserInfoModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.IMePresenter;
import com.ysp.houge.ui.iview.IMePageView;
import com.ysp.houge.utility.LogUtil;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import android.text.TextUtils;

public class MePresenter extends BasePresenter<IMePageView> implements IMePresenter {
    private IUserInfoModel infoModel;

    public MePresenter(IMePageView view) {
        super(view);
    }

    @Override
    public void initModel() {
        infoModel = new UserInfoModelImpl();
    }

    @Override
    public void showChangeStatusPop() {
        // TODO 显示更换身份对话框
        mView.showChangeLoginStatusDialog();
    }

    @Override
    public void changeLoginStutus() {
        /** 切换身份 */
        int nowStatus = MyApplication.getInstance().getPreferenceUtils().getLoginStatus();
        MyApplication.getInstance().setLoginStaus((nowStatus == MyApplication.LOG_STATUS_BUYER)
                ? MyApplication.LOG_STATUS_SELLER : MyApplication.LOG_STATUS_BUYER);

        mView.changeLoginStatus();
    }

    @Override
    public void onSystemClick() {
        mView.jumpToSystem();
    }

    @Override
    public void getUserInfo() {
        //刷新显示
        mView.setMessageCount(null);
        // TODO 获取用户信息
        infoModel.meInfoRequest(true, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (data != null && data instanceof UserInfoEntity) {
                    UserInfoEntity userInfo = (UserInfoEntity) data;
                    userInfo.im_id = MyApplication.getInstance().getUserInfo().im_id;
                    userInfo.im_pass = MyApplication.getInstance().getUserInfo().im_pass;
                    // 更新内存中的个人信息数据
                    MyApplication.getInstance().setUserInfo(userInfo);
                    // 保存或更新个人信息到数据库
                    UserInfoController.createOrUpdate(userInfo);
                    //存储到基本信息表
                    UserEntity userEntity = new UserEntity(userInfo.id, userInfo.nick, userInfo.avatar, userInfo.sex);
                    UserContorller.createOrUpdate(userEntity);
                    mView.showUserInfo(userInfo);
                }else {
                    mView.showToast("获取信息异常");
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.showUserInfo(MyApplication.getInstance().getUserInfo());
            }
        });
    }

    @Override
    public void onUserInfoCliclk() {
        mView.jumpToAccountInfo();
    }

    @Override
    public void onBalanceClick() {
        mView.jumpToBalance();
    }

    @Override
    public void onLoveMeClick() {
        mView.jumpToLoveMe();
    }

    @Override
    public void onAllCommentClick() {
        mView.jumpToAllComment();
    }

    @Override
    public void onMyFootprintClick() {
        mView.jumpToMyFootprint();
    }

    @Override
    public void onAddressManagerClick() {
        mView.jumpToAddressManager();
    }

    @Override
    public void onMySkillClick() {
        mView.jumpToMeSkill();
    }

    @Override
    public void onTimeManagerClick() {
        mView.jumpToTimeManager();
    }

    @Override
    public void onResidentAddressClick() { mView.jumpToResidentAddress(); }

    @Override
    public void onMakeMoneyByShareClick() {
        mView.jumpToMakeMoneyByShare();
    }

    @Override
    public void onMyDemandClick() {
        mView.jumpToMeNeed();
    }

    @Override
    public void onServiceSafeguardClick() {
        String serviceSafeguardg = MyApplication.getInstance().getUserInfo().serviceSafeguardg;
        if (!TextUtils.isEmpty(serviceSafeguardg)) {
            double money = -1;
            try{money = Double.parseDouble(serviceSafeguardg);}catch (Exception e){}
            if (money > 0)
                mView.jumpToUnfreezeServiceSafeguard();
            else
                mView.jumpToApplyServiceSafeguard();
        }
    }

    @Override
    public void onAdvanceOfWagesClick() {
        mView.jumpToAdvanceSalary();
    }

    @Override
    public void onMeBuyerClick() {
        mView.jumpToMeBuy();

        MessageCountEntity messageCountEntity = MyApplication.getInstance().getMessageCountEntity();
        messageCountEntity.buy = "0";
        MyApplication.getInstance().setMessageCountEntity(messageCountEntity);
        EventBus.getDefault().post(new MessageCountEntity());
    }

    @Override
    public void onMeSellerClick() {
        mView.jumpToMeSeller();

        MessageCountEntity messageCountEntity = MyApplication.getInstance().getMessageCountEntity();
        messageCountEntity.sale = "0";
        MyApplication.getInstance().setMessageCountEntity(messageCountEntity);
        EventBus.getDefault().post(new MessageCountEntity());
    }
}
