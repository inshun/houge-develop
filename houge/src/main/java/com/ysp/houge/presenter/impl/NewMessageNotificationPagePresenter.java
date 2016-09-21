package com.ysp.houge.presenter.impl;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import com.ysp.houge.R;
import com.ysp.houge.model.ISettingModel;
import com.ysp.houge.model.entity.bean.SettingEntity;
import com.ysp.houge.model.impl.SettingModelImpl;
import com.ysp.houge.presenter.BasePresenter;
import com.ysp.houge.presenter.INewMessageNotificationPagePresenter;
import com.ysp.houge.ui.iview.INewMessageNotificationPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;
import com.ysp.houge.utility.volley.ResponseCode;

public class NewMessageNotificationPagePresenter extends BasePresenter<INewMessageNotificationPageView>
        implements INewMessageNotificationPagePresenter {

    private boolean isNightNote;
    private boolean isVoiceNote;
    private boolean isShockNote;
    private String night, voice, shock;
    private List<Boolean> isNotes = new ArrayList<Boolean>();

    private ISettingModel iSettingModel;

    public NewMessageNotificationPagePresenter(INewMessageNotificationPageView view) {
        super(view);
    }

    @Override
    public void initModel() {
        iSettingModel = new SettingModelImpl();
    }

    @Override
    public void getNoteSetting() {
        mView.showProgress();
        isNotes.add(isNightNote);
        isNotes.add(isVoiceNote);
        isNotes.add(isShockNote);
        iSettingModel.getSetting(new OnNetResponseCallback() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                mView.hideProgress();
                if (null != data && data instanceof List<?>) {
                    List<SettingEntity> list = (List<SettingEntity>) data;
                    // 循环找到服务距离字段
                    for (int i = 0; i < list.size(); i++) {
                        if (TextUtils.equals(list.get(i).name, "night_notify_status")) {
                            isNightNote = TextUtils.equals(list.get(i).value, "yes");
                            isNotes.set(0, isNightNote);
                        } else if (TextUtils.equals(list.get(i).name, "sound_status")) {
                            isVoiceNote = TextUtils.equals(list.get(i).value, "yes");
                            isNotes.set(1,isVoiceNote);
                        } else if (TextUtils.equals(list.get(i).name, "shake_status")) {
                            isShockNote = TextUtils.equals(list.get(i).value, "yes");
                            isNotes.set(2, isShockNote);
                        }
                    }
                }

                for (int i = 0; i < isNotes.size(); i++) {
                    mView.serNoteType(i, isNotes.get(i));
                }
            }

            @Override
            public void onError(int errorCode, String message) {
                mView.hideProgress();
                mView.serNoteType(0, true);
                mView.serNoteType(1, false);
                mView.serNoteType(2, false);
            }

        });
    }

    @Override
    public void changeNote(int index) {
        boolean isNote = isNotes.get(index) == (Boolean) true ? false : true;
        isNotes.set(index, isNote);
        mView.serNoteType(index, isNotes.get(index));
    }

    @Override
    public void submit() {
        mView.showProgress();
        if (isChange()) {
            iSettingModel.setting(night, voice, shock, "", new OnNetResponseCallback() {

                @Override
                public void onSuccess(Object data) {
                    mView.hideProgress();
                    if (data != null && data instanceof String) {
                        mView.showToast("设置成功");
                        mView.close();
                    }
                }

                @Override
                public void onError(int errorCode, String message) {
                    mView.hideProgress();
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
        } else {
            mView.hideProgress();
            mView.close();
        }
    }

    private boolean isChange() {
        int changeCount = 0;
        if (isNightNote != isNotes.get(0)) {
            changeCount++;
        }
        if (isNotes.get(0)) {
            night = "yes";
        } else {
            night = "no";
        }

        if (isVoiceNote != isNotes.get(1)) {
            changeCount++;
        }
        if (isNotes.get(1)) {
            voice = "yes";
        } else {
            voice = "no";
        }

        if (isShockNote != isNotes.get(2)) {
            changeCount++;
        }
        if (isNotes.get(2)) {
            shock = "yes";
        } else {
            shock = "no";
        }

        if (changeCount > 0) {
            return true;
        } else {
            return false;
        }
    }
}
