package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import com.ypy.eventbus.EventBus;
import com.ysp.houge.R;
import com.ysp.houge.app.MyApplication;
import com.ysp.houge.model.IAddressModel;
import com.ysp.houge.model.IFileUploadModel;
import com.ysp.houge.model.ISellerOrderModel;
import com.ysp.houge.model.ISkillMoneyUnitModel;
import com.ysp.houge.model.ITimeModel;
import com.ysp.houge.model.entity.bean.AddressEntity;
import com.ysp.houge.model.entity.bean.SkillEntity;
import com.ysp.houge.model.entity.bean.SkillMoneyUnitEntity;
import com.ysp.houge.model.entity.eventbus.LocationChooseEventBusEntity;
import com.ysp.houge.model.impl.AddressModelImpl;
import com.ysp.houge.model.impl.FileUploadModelImpl;
import com.ysp.houge.model.impl.SellerOrderModelImpl;
import com.ysp.houge.model.impl.SkillMoneyUnitModelImpl;
import com.ysp.houge.model.impl.TimeModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.ISkillPulishPresenter;
import com.ysp.houge.ui.iview.ISkillPulishPagerView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

import java.util.ArrayList;
import java.util.List;

public class SkillPulishPresenter extends BasePresenter<ISkillPulishPagerView> implements ISkillPulishPresenter {
    private List<String> picList;// 图片数组
    private boolean isSettingTime = false;// 是否设置了时间
    private boolean isSettingAddress = false;// 是否设置了地址
    private boolean isFree = true;// 免薪实习标记
    private boolean isWorkLunch = false;// 工作餐标记
    private boolean isStay = false;// 提供住宿标记

    private SkillEntity entity;

    private ITimeModel iTimeModel;
    private IAddressModel iAddressModel;
    private IFileUploadModel iFileUploadModel;
    private ISellerOrderModel iSellerOrderModel;


    /*技能金额单位*/
    private SkillMoneyUnitEntity moneyUnitEntity;
    private ISkillMoneyUnitModel iSkillMoneyUnitModel;
    private List<SkillMoneyUnitEntity> units = new ArrayList<SkillMoneyUnitEntity>();

    public SkillPulishPresenter(ISkillPulishPagerView view) {
        super(view);
        picList = new ArrayList<String>();
    }

    @Override
    public void initModel() {

        iTimeModel = new TimeModelImpl();
        iAddressModel = new AddressModelImpl();
        iFileUploadModel = new FileUploadModelImpl();
        iSellerOrderModel = new SellerOrderModelImpl();
        iSkillMoneyUnitModel = new SkillMoneyUnitModelImpl();
    }

    public int getPicListSize() {
        return picList.size();
    }

    @Override
    public void delImage(int position) {

        // 先删除最后一个空的
        if (picList.size() > 0 && TextUtils.isEmpty(picList.get(picList.size() - 1))) {
            picList.remove(picList.size() - 1);
        }

        if (picList.size() > position) {
            picList.remove(position);
            picList.add("");
        }
        mView.loadImg(picList);
    }

    @Override
    public void choosePicBack(String picPath) {

        // TODO 选择图片后
        if (picList.size() > 0 && TextUtils.isEmpty(picList.get(picList.size() - 1))) {
            picList.remove(picList.size() - 1);
        }
        if (!TextUtils.isEmpty(picPath) && !TextUtils.equals(picPath, "233")) {
            picList.add(picList.size(), picPath);
            if (picList.size() < 9) {
                picList.add("");
            }
        } else if (TextUtils.equals(picPath, "233")) {
            picList.add("");
        }
        mView.loadImg(picList);
    }

    @Override
    public void changeFreeStatus() {
        // TODO 改变免薪状态
        isFree = isFree ? false : true;
        mView.changeFreeStatus(isFree);

        // 如果免薪实习为false，那么另外两个也要为false
        // 后期如果要还逻辑请将该字段放到提交位置处理
        if (!isFree) {
            isWorkLunch = true;
            isStay = true;
            changeWorkLunchStatus();
            changeStayStatus();
        }
    }

    @Override
    public void changeWorkLunchStatus() {
        // TODO Auto-generated method stub
        isWorkLunch = isWorkLunch ? false : true;
        mView.changeWorkLunchStatus(isWorkLunch);
    }

    @Override
    public void changeStayStatus() {
        // TODO Auto-generated method stub
        isStay = isStay ? false : true;
        mView.changeStayStatus(isStay);
    }

    @Override
    public void setTimeFinish() {
        isSettingTime = true;
    }

    @Override
    public void addAddress(LocationChooseEventBusEntity locationEntity) {
        mView.showProgress();
        iAddressModel.addOrUpdateAddress(MyApplication.LOG_STATUS_SELLER, 0,
                MyApplication.getInstance().getCurrentUid(), "", "", "", "", "",
                true, locationEntity.langtitude, locationEntity.latitude,
                new OnNetResponseCallback() {
                    @Override
                    public void onSuccess(Object data) {
//                mView.hideProgress();
                        isSettingAddress = true;

                        //发送设置成功的消息，实际是时间设置消息去响应这个请求
                        EventBus.getDefault().post("");
                    }

                    @Override
                    public void onError(int errorCode, String message) {
//                mView.hideProgress();
                        isSettingAddress = false;
                        mView.showToast("接活地址设置错误");
                    }
                });
    }

    @Override
    public void submit(String skillTitle, String skillDesc, String price, String unit) {


        if (!(picList.size() > 0)) {
            mView.showToast("请添加技能相关图片");
            return;
        }

        if (TextUtils.isEmpty(skillTitle)) {
            mView.showToast("请输入技能名称");
            return;
        }

        if (TextUtils.isEmpty(skillDesc)) {
            mView.showToast("请输入技能描述");
            return;
        }

        //如果不是免薪实习
        if (!isFree) {
            if (TextUtils.isEmpty(price)) {
                mView.showToast("请输入价格");
                return;
            }

            if (TextUtils.isEmpty(unit)) {
                mView.showToast("请输入单位");
                return;
            }
        }

        getIsSettingDefault(skillTitle, skillDesc, price, unit);
    }

    @Override
    public void pickUp() {
        // TODO 收起
        mView.pickUp();
    }

    private void getIsSettingDefault(String skillTitle, String skillDesc, String price, String unit) {
//        if (isSettingTime && isSettingAddress) {
        if (isSettingAddress) {
            // 发布 先创建发布订单对象
            entity = new SkillEntity();
            entity.title = skillTitle;
            entity.desc = skillDesc;
            if (isFree) {
                entity.price = Double.parseDouble("0");
                entity.unit = "";
            } else {
                entity.price = Double.parseDouble(price);
                entity.unit = unit;
            }

            if (isFree) {
                entity.is_no_salary = "1";
            } else {
                entity.is_no_salary = "0";
            }

            if (isStay) {
                entity.is_room = "1";
            } else {
                entity.is_room = "0";
            }

            if (isWorkLunch) {
                entity.is_good = "1";
            } else {
                entity.is_good = "0";
            }

            uploadImg();
        } else {
//            mView.hideProgress();
            //先设置时间
//            if (!isSettingTime) {
//                mView.showToast("请先设置您的接活时间");
//                mView.jumToTimeManager();
//                return;
//            }

            //再设置地址(地址设置完成之后会通过时间设置完成的事件来重新发布)
            if (!isSettingAddress) {
                mView.jumpToMap();
                return;
            }
        }
    }

    private void uploadImg() {
//        mView.showProgress();

        // 上传之前删除最后一个空的
        if (picList.size() > 0 && TextUtils.isEmpty(picList.get(picList.size() - 1))) {
            picList.remove(picList.size() - 1);
        }

        if (picList.isEmpty()) {
            mView.showToast("请添加技能相关图片");
//            mView.hideProgress();
            return;
        }

        iFileUploadModel.uploadMultiFile(picList, "path", "goods", new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
//                mView.hideProgress();
                if (data != null && data instanceof List<?>) {
                    // 接收图片数组
                    List<String> list = (List<String>) data;
                    // 空判断并转成后台规定的格式
                    if (list != null && list.size() > 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < list.size(); i++) {
                            stringBuilder.append(list.get(i));
                            stringBuilder.append(",");
                        }

                        // 去掉最后一个逗号
                        if (stringBuilder.lastIndexOf(",") == stringBuilder.length() - 1) {
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        }
                        entity.image = stringBuilder.toString();
                        // 这里 请求卖家发布接口
                        requesPulish();
                    } else {
                        entity = new SkillEntity();
//                        mView.hideProgress();
                        mView.showToast("图片上传失败");
                    }
                } else {
                    entity = new SkillEntity();
//                    mView.hideProgress();
                    mView.showToast("图片上传失败");
                }
            }

            @Override
            public void onError(int errorCode, String message) {
//                mView.hideProgress();
                entity = new SkillEntity();
//                mView.hideProgress();
                mView.showToast("图片上传失败");
            }
        });
    }

    private void requesPulish() {
        mView.progress();
//        mView.showProgress();
        iSellerOrderModel.releaseOrderRequest(entity, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                mView.progresshide();
//                mView.hideProgress();
                if (data != null && data instanceof String) {
                    mView.showToast((String) data);
                    mView.jumpToHomeActivity();
                    pickUp();
                }
            }

            @Override
            public void onError(int errorCode, String message) {
//                mView.hideProgress();
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

    @Override
    public void getSetTime() {
        iTimeModel.getTime(MyApplication.LOG_STATUS_SELLER, new OnNetResponseCallback() {

            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof List<?>) {
                    isSettingTime = true;
                } else {
                    isSettingTime = false;
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                isSettingTime = false;
            }
        });
    }

    @Override
    public void getSetAddress() {
        iAddressModel.getDefaultAddress(MyApplication.getInstance().getCurrentUid(), MyApplication.LOG_STATUS_SELLER, new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                if (null != data && data instanceof AddressEntity)
                    isSettingAddress = true;
                else
                    isSettingAddress = false;
            }

            @Override
            public void onError(int errorCode, String message) {
                isSettingAddress = false;
            }
        });
    }


    /*获取技能金额单位*/
    @Override
    public void getSkillMoneyUnit() {

        /*如果有数据的话清空*/
        if (units.size()>0){
            units.clear();
        }

        iSkillMoneyUnitModel.getMoneyUnit(new OnNetResponseCallback() {
            @Override
            public void onSuccess(Object data) {
                if (data != null && data instanceof List<?>){
                    units.addAll((List<SkillMoneyUnitEntity>)data);
                }

                mView.getMoneyUnit(units);

            }

            @Override
            public void onError(int errorCode, String message) {
            }
        });
    }

}
