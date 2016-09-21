package com.ysp.houge.presenter.impl;

import com.ysp.houge.app.MyApplication;
import com.ysp.houge.lisenter.OnItemClickListener;
import com.ysp.houge.model.INearbyModel;
import com.ysp.houge.model.entity.bean.ChatPageEntity;
import com.ysp.houge.model.entity.bean.GoodsDetailEntity;
import com.ysp.houge.model.entity.bean.WorkTypeEntity;
import com.ysp.houge.model.impl.NearbyModelImpl;
import com.ysp.houge.presenter.INewJoinSkillPresenter;
import com.ysp.houge.ui.iview.INewJoinSkillPageView;
import com.ysp.houge.utility.volley.OnNetResponseCallback;

import java.util.ArrayList;
import java.util.List;

public class NewJoinSkillPresenter implements INewJoinSkillPresenter<List<GoodsDetailEntity>> {
    private int page = 1;
    private boolean hasDate = true;

    private int type = -1;

    @SuppressWarnings("unused")
    private WorkTypeEntity entity;
    private INearbyModel iNearbyModel;
    private INewJoinSkillPageView iNewJoinSkillPageView;

    private List<GoodsDetailEntity> list = new ArrayList<GoodsDetailEntity>();

    public NewJoinSkillPresenter(INewJoinSkillPageView iNewJoinSkillPageView) {
        super();
        iNearbyModel = new NearbyModelImpl();
        this.iNewJoinSkillPageView = iNewJoinSkillPageView;

        switch (MyApplication.getInstance().getLoginStaus()) {
            case MyApplication.LOG_STATUS_SELLER:
                type = 2;
                break;
            case MyApplication.LOG_STATUS_BUYER:
                type = 1;
                break;
        }
    }

    @Override
    public void refresh() {
        page = 1;
        hasDate = true;
        list.clear();

        iNearbyModel.getNewJoinSkillList(page, type, new OnNetResponseCallback() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                if (data != null && data instanceof List<?>) {
                    list = (List<GoodsDetailEntity>) data;
                    if (list.isEmpty()) {
                        hasDate = false;
                    } else {
                        hasDate = true;
                    }
                }
                iNewJoinSkillPageView.refreshComplete(list);
            }

            @Override
            public void onError(int errorCode, String message) {
                iNewJoinSkillPageView.refreshComplete(list);
            }
        });
    }

    @Override
    public void loadMore() {
        page++;
        list.clear();

        iNearbyModel.getNewJoinSkillList(page, type, new OnNetResponseCallback() {

            @SuppressWarnings("unchecked")
            @Override
            public void onSuccess(Object data) {
                if (data != null && data instanceof List<?>) {
                    list = (List<GoodsDetailEntity>) data;
                    if (list.isEmpty()) {
                        hasDate = false;
                        page--;
                    } else {
                        hasDate = true;
                    }
                }
                iNewJoinSkillPageView.loadMoreComplete(list);
            }

            @Override
            public void onError(int errorCode, String message) {
                iNewJoinSkillPageView.loadMoreComplete(list);
            }
        });
    }

    @Override
    public boolean hasData() {
        return hasDate;
    }

    @Override
    public void setWorkType(WorkTypeEntity entity) {
        this.entity = entity;
    }

    @Override
    public void OnClick(int position, int operation) {
        // 列表的点击操作
        switch (operation) {
            // 用户详情
            case OnItemClickListener.CLICK_OPERATION_USER_DETAIL:
                iNewJoinSkillPageView.jumpToUserInfo(list.get(position).user_id);
                break;
            // 技能详情
            case OnItemClickListener.CLICK_OPERATION_SKILL_DETAIL:
                iNewJoinSkillPageView.jumpToSkillDetails(list.get(position).id);
                break;
            // 需求详情
            case OnItemClickListener.CLICK_OPERATION_NEED_DETAIL:
                iNewJoinSkillPageView.jumpToNeedDetails(list.get(position).id);
                break;
            // 聊一聊
            case OnItemClickListener.CLICK_OPERATION_HAVE_A_TALK:
                int goodType = -1;
                switch (MyApplication.getInstance().getLoginStaus()) {
                    case MyApplication.LOG_STATUS_SELLER:
                        goodType = ChatPageEntity.GOOD_TYPE_NEED;
                        break;
                    case MyApplication.LOG_STATUS_BUYER:
                        goodType = ChatPageEntity.GOOD_TYPE_SKILL;


                        break;
                }
                ChatPageEntity chatPageEntity = new ChatPageEntity(list.get(position).user_id, list.get(position), goodType);
                iNewJoinSkillPageView.jumpToHaveATalk(chatPageEntity);
                break;
            // 分享
            case OnItemClickListener.CLICK_OPERATION_SHARE:
                iNewJoinSkillPageView.share(list.get(position));
                break;
        }
    }

}
